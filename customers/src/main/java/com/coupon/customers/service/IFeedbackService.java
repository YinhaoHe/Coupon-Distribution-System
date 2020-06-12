package com.coupon.customers.service;

import com.coupon.customers.vo.Feedback;
import com.coupon.customers.vo.Response;

/**
 * <h1>Feedback function</h1>
 */
public interface IFeedbackService {

    /**
     * <h2>Create Feedback</h2>
     * @param feedback {@link Feedback}
     * @return {@link Response}
     * */
    Response createFeedback(Feedback feedback);

    /**
     * <h2>get Feedback</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * */
    Response getFeedback(Long userId);
}