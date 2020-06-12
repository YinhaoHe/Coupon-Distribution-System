package com.coupon.customers.service;

import com.coupon.customers.vo.CouponTemplate;

/**
 * <h1>Coupon Hbase Service</h1>
 */
public interface IHBaseCouponService {

    /**
     * <h2>将 CouponTemplate 写入 HBase</h2>
     * @param couponTemplate {@link CouponTemplate}
     * @return true/false
     * */
    boolean distributeCouponTemplateToHBase(CouponTemplate couponTemplate);
}