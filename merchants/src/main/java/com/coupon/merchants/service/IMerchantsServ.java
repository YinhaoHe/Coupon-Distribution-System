package com.coupon.merchants.service;

import com.coupon.merchants.vo.CreateMerchantsRequest;
import com.coupon.merchants.vo.Response;
import com.coupon.merchants.vo.couponTemplate;

/**
 * <h1>Definition of merchant service interface</h1>
 */
public interface IMerchantsServ {

    /**
     * <h2>Create Merchant Service </h2>
     * @param request {@link CreateMerchantsRequest} Create business request
     * @return {@link Response}
     * */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     * <h2>Build merchant information based on id</h2>
     * @param id Merchant id
     * @return {@link Response}
     * */
    Response buildMerchantsInfoById(Integer id);

    /**
     * <h2>Distribute Coupon</h2>
     * @param template {@link couponTemplate} Coupon Obj
     * @return {@link Response}
     * */
    Response distributeCouponTemplate(couponTemplate template);
}
