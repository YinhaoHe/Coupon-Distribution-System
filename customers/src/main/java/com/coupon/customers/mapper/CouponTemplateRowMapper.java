package com.coupon.customers.mapper;

import com.coupon.customers.constant.Constants;
import com.coupon.customers.vo.CouponTemplate;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * <h1>HBase CouponTemplate Row To CouponTemplate Object</h1>
 */
public class CouponTemplateRowMapper implements RowMapper<CouponTemplate> {

    private static byte[] FAMILY_B = Constants.CouponTemplateTable.FAMILY_B.getBytes();
    private static byte[] ID = Constants.CouponTemplateTable.ID.getBytes();
    private static byte[] TITLE = Constants.CouponTemplateTable.TITLE.getBytes();
    private static byte[] SUMMARY = Constants.CouponTemplateTable.SUMMARY.getBytes();
    private static byte[] DESC = Constants.CouponTemplateTable.DESC.getBytes();
    private static byte[] HAS_TOKEN = Constants.CouponTemplateTable.HAS_TOKEN.getBytes();
    private static byte[] BACKGROUND = Constants.CouponTemplateTable.BACKGROUND.getBytes();

    private static byte[] FAMILY_C = Constants.CouponTemplateTable.FAMILY_C.getBytes();
    private static byte[] LIMIT = Constants.CouponTemplateTable.LIMIT.getBytes();
    private static byte[] START = Constants.CouponTemplateTable.START.getBytes();
    private static byte[] END = Constants.CouponTemplateTable.END.getBytes();

    @Override
    public CouponTemplate mapRow(Result result, int rowNum) throws Exception {

        CouponTemplate CouponTemplate = new CouponTemplate();

        CouponTemplate.setId(Bytes.toInt(result.getValue(FAMILY_B, ID)));
        CouponTemplate.setTitle(Bytes.toString(result.getValue(FAMILY_B, TITLE)));
        CouponTemplate.setSummary(Bytes.toString(result.getValue(FAMILY_B, SUMMARY)));
        CouponTemplate.setDesc(Bytes.toString(result.getValue(FAMILY_B, DESC)));
        CouponTemplate.setHasToken(Bytes.toBoolean(result.getValue(FAMILY_B, HAS_TOKEN)));
        CouponTemplate.setBackground(Bytes.toInt(result.getValue(FAMILY_B, BACKGROUND)));

        String[] patterns = new String[] {"yyyy-MM-dd"};

        CouponTemplate.setLimit(Bytes.toLong(result.getValue(FAMILY_C, LIMIT)));
        CouponTemplate.setStart(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, START)), patterns));
        CouponTemplate.setEnd(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, END)), patterns));

        return CouponTemplate;
    }
}