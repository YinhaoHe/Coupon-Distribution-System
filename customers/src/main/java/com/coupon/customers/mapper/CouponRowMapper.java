package com.coupon.customers.mapper;

import com.coupon.customers.constant.Constants;
import com.coupon.customers.vo.Coupon;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * <h1>HBase Coupon Row To Coupon Object</h1>
 */
public class CouponRowMapper implements RowMapper<Coupon> {

    private static byte[] FAMILY_I = Constants.CouponTable.FAMILY_I.getBytes();
    private static byte[] USER_ID = Constants.CouponTable.USER_ID.getBytes();
    private static byte[] TEMPLATE_ID = Constants.CouponTable.TEMPLATE_ID.getBytes();
    private static byte[] TOKEN = Constants.CouponTable.TOKEN.getBytes();
    private static byte[] ASSIGNED_DATE = Constants.CouponTable.ASSIGNED_DATE.getBytes();
    private static byte[] CON_DATE = Constants.CouponTable.CON_DATE.getBytes();

    @Override
    public Coupon mapRow(Result result, int rowNum) throws Exception {

        Coupon coupon = new Coupon();

        coupon.setUserId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)));
        coupon.setTemplateId(Bytes.toString(result.getValue(FAMILY_I, TEMPLATE_ID)));
        coupon.setToken(Bytes.toString(result.getValue(FAMILY_I, TOKEN)));

        String[] patterns = new String[] {"yyyy-DD-dd"};
        coupon.setAssignedDate(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_I, ASSIGNED_DATE)), patterns));

        String conDateStr = Bytes.toString(result.getValue(FAMILY_I, CON_DATE));
        if (conDateStr.equals("-1")) {
            coupon.setConDate(null);
        } else {
            coupon.setConDate(DateUtils.parseDate(conDateStr, patterns));
        }

        coupon.setRowKey(Bytes.toString(result.getRow()));

        return coupon;
    }
}
