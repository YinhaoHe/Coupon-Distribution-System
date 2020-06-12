package com.coupon.customers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>User get coupon request</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCouponTemplateRequest {

    /** User id */
    private Long userId;

    /** CouponTemplate Object */
    private CouponTemplate couponTemplate;
}