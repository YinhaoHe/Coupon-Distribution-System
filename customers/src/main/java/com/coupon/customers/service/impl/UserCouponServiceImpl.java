package com.coupon.customers.service.impl;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.constant.Constants;
import com.coupon.customers.constant.CouponStatus;
import com.coupon.customers.dao.MerchantsDao;
import com.coupon.customers.entity.Merchants;
import com.coupon.customers.mapper.CouponRowMapper;
import com.coupon.customers.service.IUserCouponService;
import com.coupon.customers.vo.Coupon;
import com.coupon.customers.vo.CouponInfo;
import com.coupon.customers.vo.CouponTemplate;
import com.coupon.customers.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>User Coupon related service implement</h1>
 */
@Slf4j
@Service
public class UserCouponServiceImpl implements IUserCouponService {
    /** Hbase  */
    private final HbaseTemplate hbaseTemplate;

    /** Merchants Dao */
    private final MerchantsDao merchantsDao;

    @Autowired
    public UserCouponServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }

    @Override
    public Response getUserCouponInfo(Long userId) throws Exception {
        return getCouponInfoByStatus(userId, CouponStatus.UNUSED);
    }

    @Override
    public Response getUserUsedCouponInfo(Long userId) throws Exception {
        return getCouponInfoByStatus(userId, CouponStatus.USED);
    }

    @Override
    public Response getUserAllCouponInfo(Long userId) throws Exception {
        return getCouponInfoByStatus(userId, CouponStatus.ALL);
    }

    @Override
    public Response userUseCoupon(Coupon coupon) {
        // use three filters to find the coupon
        // get prefix by userId
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(
                String.valueOf(coupon.getUserId())).reverse().toString());
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        filters.add(new PrefixFilter(rowPrefix));
        filters.add(new SingleColumnValueFilter(
                Constants.CouponTable.FAMILY_I.getBytes(),
                Constants.CouponTable.TEMPLATE_ID.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(coupon.getTemplateId())
        ));
        filters.add(new SingleColumnValueFilter(
                Constants.CouponTable.FAMILY_I.getBytes(),
                Constants.CouponTable.CON_DATE.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));

        scan.setFilter(new FilterList(filters));

        List<Coupon> coupons = hbaseTemplate.find(Constants.CouponTable.TABLE_NAME,
                scan, new CouponRowMapper());

        if (null == coupons || coupons.size() != 1) {
            log.error("UserUseCoupon Error: {}", JSON.toJSONString(coupon));
            return Response.failure("UserUseCoupon Error");
        }
        // update info
        byte[] FAMILY_I = Constants.CouponTable.FAMILY_I.getBytes();
        byte[] CON_DATE = Constants.CouponTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(coupons.get(0).getRowKey().getBytes());
        put.addColumn(FAMILY_I, CON_DATE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()))
        );
        datas.add(put);

        hbaseTemplate.saveOrUpdates(Constants.CouponTable.TABLE_NAME, datas);

        return Response.success();
    }

    /**
     * <h2>Get Coupon Info by status</h2>
     * @param userId user id
     * @param status {@link CouponStatus}
     * @return {@link Response}
     * */
    private Response getCouponInfoByStatus(Long userId, CouponStatus status) throws Exception {

        // get prefix by userId
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());

        CompareFilter.CompareOp compareOp =
                status == CouponStatus.UNUSED ?
                        CompareFilter.CompareOp.EQUAL : CompareFilter.CompareOp.NOT_EQUAL;

        Scan scan = new Scan();

        List<Filter> filters = new ArrayList<>();

        // 1. Row key prefix filter to find coupons for specific users
        filters.add(new PrefixFilter(rowPrefix));
        // 2. Filter based on column cell value, find unused coupons
        if (status != CouponStatus.ALL) {
            filters.add(
                    new SingleColumnValueFilter(
                            Constants.CouponTable.FAMILY_I.getBytes(),
                            Constants.CouponTable.CON_DATE.getBytes(), compareOp,
                            Bytes.toBytes("-1"))
            );
        }

        scan.setFilter(new FilterList(filters));

        List<Coupon> coupons = hbaseTemplate.find(Constants.CouponTable.TABLE_NAME, scan, new CouponRowMapper());
        Map<String, CouponTemplate> couponTemplateMap = buildCouponTemplateMap(coupons);
        Map<Integer, Merchants> merchantsMap = buildMerchantsMap(
                new ArrayList<>(couponTemplateMap.values()));

        List<CouponInfo> result = new ArrayList<>();

        for (Coupon coupon : coupons) {
            CouponTemplate couponTemplate = couponTemplateMap.getOrDefault(
                    coupon.getTemplateId(), null);
            if (null == couponTemplate) {
                log.error("CouponTemplate Null : {}", coupon.getTemplateId());
                continue;
            }

            Merchants merchants = merchantsMap.getOrDefault(couponTemplate.getId(), null);
            if (null == merchants) {
                log.error("Merchants Null : {}", couponTemplate.getId());
                continue;
            }

            result.add(new CouponInfo(coupon, couponTemplate, merchants));
        }

        return new Response(result);
    }

    /**
     * <h2>Construct a Map from Coupon object</h2>
     * @param coupons {@link Coupon}
     * @return Map {@link CouponTemplate}
     * */
    private Map<String, CouponTemplate> buildCouponTemplateMap(List<Coupon> coupons) throws Exception {

        String[] patterns = new String[] {"yyyy-MM-dd"};

        byte[] FAMILY_B = Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B);
        byte[] ID = Bytes.toBytes(Constants.CouponTemplateTable.ID);
        byte[] TITLE = Bytes.toBytes(Constants.CouponTemplateTable.TITLE);
        byte[] SUMMARY = Bytes.toBytes(Constants.CouponTemplateTable.SUMMARY);
        byte[] DESC = Bytes.toBytes(Constants.CouponTemplateTable.DESC);
        byte[] HAS_TOKEN = Bytes.toBytes(Constants.CouponTemplateTable.HAS_TOKEN);
        byte[] BACKGROUND = Bytes.toBytes(Constants.CouponTemplateTable.BACKGROUND);

        byte[] FAMILY_C = Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_C);
        byte[] LIMIT = Bytes.toBytes(Constants.CouponTemplateTable.LIMIT);
        byte[] START = Bytes.toBytes(Constants.CouponTemplateTable.START);
        byte[] END = Bytes.toBytes(Constants.CouponTemplateTable.END);

        List<String> templateIds = coupons.stream().map(
                Coupon::getTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGets = new ArrayList<>(templateIds.size());
        templateIds.forEach(t -> templateGets.add(new Get(Bytes.toBytes(t))));
        //get results from HBase
        Result[] templateResults = hbaseTemplate.getConnection()
                .getTable(TableName.valueOf(Constants.CouponTemplateTable.TABLE_NAME))
                .get(templateGets);

        // build Map<CouponTemplateId, CouponTemplate Object> -> to build CouponInfo
        Map<String, CouponTemplate> templateId2Object = new HashMap<>();
        for (Result item : templateResults) {
            CouponTemplate couponTemplate = new CouponTemplate();

            couponTemplate.setId(Bytes.toInt(item.getValue(FAMILY_B, ID)));
            couponTemplate.setTitle(Bytes.toString(item.getValue(FAMILY_B, TITLE)));
            couponTemplate.setSummary(Bytes.toString(item.getValue(FAMILY_B, SUMMARY)));
            couponTemplate.setDesc(Bytes.toString(item.getValue(FAMILY_B, DESC)));
            couponTemplate.setHasToken(Bytes.toBoolean(item.getValue(FAMILY_B, HAS_TOKEN)));
            couponTemplate.setBackground(Bytes.toInt(item.getValue(FAMILY_B, BACKGROUND)));

            couponTemplate.setLimit(Bytes.toLong(item.getValue(FAMILY_C, LIMIT)));
            couponTemplate.setStart(DateUtils.parseDate(
                    Bytes.toString(item.getValue(FAMILY_C, START)), patterns));
            couponTemplate.setEnd(DateUtils.parseDate(
                    Bytes.toString(item.getValue(FAMILY_C, END)), patterns
            ));

            templateId2Object.put(Bytes.toString(item.getRow()), couponTemplate);
        }

        return templateId2Object;
    }

    /**
     * <h2>Use CouponTemplate to find corresponding Merchants</h2>
     * @param couponTemplates {@link CouponTemplate}
     * @return {@link Merchants}
     * */
    private
    Map<Integer, Merchants> buildMerchantsMap(List<CouponTemplate> couponTemplates) {

        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = couponTemplates.stream().map(
                CouponTemplate::getId
        ).collect(Collectors.toList());
        List<Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);

        merchants.forEach(m -> merchantsMap.put(m.getId(), m));

        return merchantsMap;
    }
    
}
