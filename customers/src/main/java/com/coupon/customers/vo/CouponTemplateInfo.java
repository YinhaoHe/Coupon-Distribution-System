package com.coupon.customers.vo;

import com.coupon.customers.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Coupont Template information</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateInfo {
    /** Coupon Template*/
    private CouponTemplate couponTemplate;

    /** Merchants of Coupon */
    private Merchants merchants;
}
