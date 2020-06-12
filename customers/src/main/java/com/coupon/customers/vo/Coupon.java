package com.coupon.customers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h2>Coupons received by users</h2>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    /** User id */
    private Long userId;

    /** Coupon RowKey in HBase */
    private String rowKey;

    /** CouponTemplate RowKey in HBase */
    private String templateId;

    /** Coupon token, if null, then -1 */
    private String token;

    /** When the coupon was assigned */
    private Date assignedDate;

    /** Date of consumption, not null means that it has been consumed */
    private Date conDate;
}
