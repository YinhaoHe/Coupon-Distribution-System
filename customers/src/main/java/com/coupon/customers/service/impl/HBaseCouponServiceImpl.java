package com.coupon.customers.service.impl;

import com.coupon.customers.constant.Constants;
import com.coupon.customers.service.IHBaseCouponService;
import com.coupon.customers.utils.RowKeyGenUtil;
import com.coupon.customers.vo.CouponTemplate;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h1>Coupon HBase Service</h1>
 */
@Slf4j
@Service
public class HBaseCouponServiceImpl implements IHBaseCouponService {

    /** HBase client */
    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public HBaseCouponServiceImpl(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public boolean distributeCouponTemplateToHBase(CouponTemplate couponTemplate) {

        if (null == couponTemplate) {
            return false;
        }

        String rowKey = RowKeyGenUtil.genCouponTemplateRowKey(couponTemplate);

        try {
            if (hbaseTemplate.getConnection().getTable(TableName.valueOf(Constants.CouponTemplateTable.TABLE_NAME))
                    .exists(new Get(Bytes.toBytes(rowKey)))) {
                log.warn("RowKey {} is already exist!", rowKey);
                return false;
            }
        } catch (Exception ex) {
            log.error("DropCouponTemplateToHBase Error: {}", ex.getMessage());
            return false;
        }

        Put put = new Put(Bytes.toBytes(rowKey));

        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.CouponTemplateTable.ID),
                Bytes.toBytes(couponTemplate.getId())
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.CouponTemplateTable.TITLE),
                Bytes.toBytes(couponTemplate.getTitle())
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.CouponTemplateTable.SUMMARY),
                Bytes.toBytes(couponTemplate.getSummary())
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.CouponTemplateTable.DESC),
                Bytes.toBytes(couponTemplate.getDesc())
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.CouponTemplateTable.HAS_TOKEN),
                Bytes.toBytes(couponTemplate.getHasToken())
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.CouponTemplateTable.BACKGROUND),
                Bytes.toBytes(couponTemplate.getBackground())
        );

        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.CouponTemplateTable.LIMIT),
                Bytes.toBytes(couponTemplate.getLimit())
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.CouponTemplateTable.START),
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(couponTemplate.getStart()))
        );
        put.addColumn(
                Bytes.toBytes(Constants.CouponTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.CouponTemplateTable.END),
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(couponTemplate.getEnd()))
        );

        hbaseTemplate.saveOrUpdate(Constants.CouponTemplateTable.TABLE_NAME, put);

        return true;
    }
}
