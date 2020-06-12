package com.coupon.customers.vo;

import com.coupon.customers.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Coupon information received by households</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponInfo {

    /** Coupon */
    private Coupon coupon;

    /** Coupon Template */
    private CouponTemplate couponTemplate;

    /** Merchant of Coupon */
    private Merchants merchants;
}
