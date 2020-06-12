package com.coupon.customers.service;

import com.coupon.customers.vo.Coupon;
import com.coupon.customers.vo.Response;

/**
 * <h1>Get user coupon information</h1>
 */
public interface IUserCouponService {
    /**
     * <h2>Get user's current coupons</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * */
    Response getUserCouponInfo(Long userId) throws Exception;

    /**
     * <h2>Get user's used coupons</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * */
    Response getUserUsedCouponInfo(Long userId) throws Exception;

    /**
     * <h2>get user's all coupons</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * */
    Response getUserAllCouponInfo(Long userId) throws Exception;

    /**
     * <h2>Use coupon</h2>
     * @param coupon {@link Coupon}
     * @return {@link Response}
     * */
    Response userUseCoupon(Coupon coupon);
}
