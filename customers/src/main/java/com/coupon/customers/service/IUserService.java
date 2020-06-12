package com.coupon.customers.service;

import com.coupon.customers.vo.Response;
import com.coupon.customers.vo.User;

/**
 * <h1>User Service: Create User Service</h1>
 */
public interface IUserService {

    /**
     * <h2>Create user</h2>
     * @param user {@link User}
     * @return {@link Response}
     * */
    Response createUser(User user) throws Exception;
}