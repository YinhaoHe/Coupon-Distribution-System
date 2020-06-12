package com.coupon.customers.service;

import com.coupon.customers.vo.GetCouponTemplateRequest;
import com.coupon.customers.vo.Response;

/**
 * <h1>User Get Coupon Service</h1>
 */
public interface IGetCouponTemplateService {
    /**
     * <h2>Uer get Coupon</h2>
     * @param request {@link GetCouponTemplateRequest}
     * @return {@link Response}
     * */
    Response getCouponTemplate(GetCouponTemplateRequest request) throws Exception;
}
