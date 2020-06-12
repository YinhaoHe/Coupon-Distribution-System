package com.coupon.customers.service;

import com.coupon.customers.vo.Response;

/**
 * <h1>Get inventory information: Only return the ones that the user has not received, that is, the coupon inventory function realizes the interface definition</h1>
 */
public interface IInventoryService {

    /**
     * <h2>Get Inventory Info</h2>
     * @param userId User id
     * @return {@link Response}
     * */
    Response getInventoryInfo(Long userId) throws Exception;
}