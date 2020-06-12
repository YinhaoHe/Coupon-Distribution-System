package com.coupon.customers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>Definition of coupon objects</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplate {

    /** Merchant id */
    private Integer id;

    /** Coupon Title */
    private String title;

    /** Coupon Summary */
    private String summary;

    /** Coupon Description */
    private String desc;

    /** Maximum Coupon */
    private Long limit;

    /** Whether the coupon has Token for merchant verification */
    private Boolean hasToken; // token stored in the Redis Set, obtained from Redis

    /** Coupon background color */
    private Integer background;

    /** Coupon start time */
    private Date start;

    /** Coupon end time */
    private Date end;

}
