package com.coupon.customers.service;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.vo.CouponTemplate;
import com.coupon.customers.vo.GetCouponTemplateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>Get Coupon Service Test/h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetCouponTemplateServiceTest extends AbstractServiceTest{

    @Autowired
    private IGetCouponTemplateService getCouponTemplateService;

    @Test
    public void testGainPassTemplate() throws Exception {

        CouponTemplate target = new CouponTemplate();
        target.setId(3);
        target.setTitle("Middleware discount-2");
        target.setHasToken(true);

        System.out.println(JSON.toJSONString(
                getCouponTemplateService.getCouponTemplate(
                        new GetCouponTemplateRequest(userId, target)
                )
        ));
    }
}
