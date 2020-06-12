package com.coupon.customers.service.impl;

import com.coupon.customers.constant.Constants;
import com.coupon.customers.dao.MerchantsDao;
import com.coupon.customers.entity.Merchants;
import com.coupon.customers.mapper.CouponTemplateRowMapper;
import com.coupon.customers.service.IInventoryService;
import com.coupon.customers.service.IUserCouponService;
import com.coupon.customers.utils.RowKeyGenUtil;
import com.coupon.customers.vo.*;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>Get inventory information, only return what the user did not receive</h1>
 * Created by Qinyi.
 */
@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

    /** Hbase */
    private final HbaseTemplate hbaseTemplate;

    /** Merchants Dao interface */
    private final MerchantsDao merchantsDao;

    private final IUserCouponService userCouponService;

    @Autowired
    public InventoryServiceImpl(HbaseTemplate hbaseTemplate,
                                MerchantsDao merchantsDao,
                                IUserCouponService userCouponService) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
        this.userCouponService = userCouponService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response getInventoryInfo(Long userId) throws Exception {

        Response allUserCoupon = userCouponService.getUserAllCouponInfo(userId);
        List<CouponInfo> couponInfos = (List<CouponInfo>) allUserCoupon.getData();

        List<CouponTemplate> excludeObject = couponInfos.stream().map(CouponInfo::getCouponTemplate)
                .collect(Collectors.toList());
        List<String> excludeIds = new ArrayList<>();

        excludeObject.forEach(e -> excludeIds.add(
                RowKeyGenUtil.genCouponTemplateRowKey(e)));

        return new Response(new InventoryResponse(userId,
                buildCouponTemplateInfo(getAvailableCouponTemplate(excludeIds))));
    }

    /**
     * <h2>Get coupons available in the system</h2>
     * @param excludeIds already received coupons -- ids
     * @return {@link CouponTemplate}
     * */
    private List<CouponTemplate> getAvailableCouponTemplate(List<String> excludeIds) {
        // two filters (limit > 0 || limit == -1)
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);

        filterList.addFilter(
                new SingleColumnValueFilter(
                        Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_C),
                        Bytes.toBytes(Constants.CouponTemplateTable.LIMIT),
                        CompareFilter.CompareOp.GREATER,
                        new LongComparator(0L)
                )
        );
        filterList.addFilter(
                new SingleColumnValueFilter(
                        Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_C),
                        Bytes.toBytes(Constants.CouponTemplateTable.LIMIT),
                        CompareFilter.CompareOp.EQUAL,
                        Bytes.toBytes("-1")
                )
        );

        Scan scan = new Scan();
        scan.setFilter(filterList);

        List<CouponTemplate> validTemplates = hbaseTemplate.find(
                Constants.CouponTemplateTable.TABLE_NAME, scan, new CouponTemplateRowMapper());
        List<CouponTemplate> availableCouponTemplates = new ArrayList<>();
        // exclude expired coupons
        Date cur = new Date();

        for (CouponTemplate validTemplate : validTemplates) {

            if (excludeIds.contains(RowKeyGenUtil.genCouponTemplateRowKey(validTemplate))) {
                continue;
            }

            if (cur.getTime() >= validTemplate.getStart().getTime()
                    && cur.getTime() <= validTemplate.getEnd().getTime()) {
                availableCouponTemplates.add(validTemplate);
            }
        }

        return availableCouponTemplates;
    }

    /**
     * <h2>Build Coupon Info (add merchant info)</h2>
     * @param couponTemplates {@link CouponTemplate}
     * @return {@link CouponTemplateInfo}
     * */
    private
    List<CouponTemplateInfo> buildCouponTemplateInfo(List<CouponTemplate> couponTemplates) {

        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = couponTemplates.stream().map(
                CouponTemplate::getId
        ).collect(Collectors.toList());
        List<Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);
        merchants.forEach(m -> merchantsMap.put(m.getId(), m));

        List<CouponTemplateInfo> result = new ArrayList<>(couponTemplates.size());

        for (CouponTemplate couponTemplate : couponTemplates) {

            Merchants mc = merchantsMap.getOrDefault(couponTemplate.getId(),
                    null);
            if (null == mc) {
                log.error("Merchants Error: {}", couponTemplate.getId());
                continue;
            }

            result.add(new CouponTemplateInfo(couponTemplate, mc));
        }

        return result;
    }
}
