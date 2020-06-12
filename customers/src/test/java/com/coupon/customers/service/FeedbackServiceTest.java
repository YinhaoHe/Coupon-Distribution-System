package com.coupon.customers.service;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.constant.FeedbackType;
import com.coupon.customers.vo.Feedback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Feedback Service Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceTest extends AbstractServiceTest{

    @Autowired
    private IFeedbackService feedbackService;

    @Test
    public void testCreateFeedback() {

        Feedback appFeedback = new Feedback();
        appFeedback.setUserId(userId);
        appFeedback.setType(FeedbackType.APP.getCode());
        appFeedback.setTemplateId("-1");
        appFeedback.setComment("CS237 Middleware DistributeCouponSystem!");

        System.out.println(JSON.toJSONString(
                feedbackService.createFeedback(appFeedback))
        );

        Feedback passFeedback = new Feedback();
        passFeedback.setUserId(userId);
        passFeedback.setType(FeedbackType.COUPON.getCode());
        passFeedback.setTemplateId("fb475cdf36b3c4a26b98ec016e2720f8");
        passFeedback.setComment("Coupon Comment!!");

        System.out.println(JSON.toJSONString(
                feedbackService.createFeedback(passFeedback)
        ));
    }

    @Test
    public void testGetFeedback() {

        System.out.println(JSON.toJSONString(
                feedbackService.getFeedback(userId))
        );
    }
}
