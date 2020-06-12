package com.coupon.customers.service;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.vo.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * User Coupon Info Service Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCouponServiceTest extends AbstractServiceTest {
    @Autowired
    private IUserCouponService userCouponService;

    @Test
    public void testGetUserCouponInfo() throws Exception {

        System.out.println(JSON.toJSONString(
                userCouponService.getUserCouponInfo(userId))
        );
    }

    @Test
    public void testGetUserUsedCouponInfo() throws Exception {

        System.out.println(JSON.toJSONString(
                userCouponService.getUserUsedCouponInfo(userId)
        ));
    }

    @Test
    public void testGetUserAllCouponInfo() throws Exception {

        System.out.println(JSON.toJSONString(
                userCouponService.getUserAllCouponInfo(userId)
        ));
    }

    @Test
    public void testUserUseCoupon() {

        Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        coupon.setTemplateId("fb475cdf36b3c4a26b98ec016e2720f8");

        System.out.println(JSON.toJSONString(
                userCouponService.userUseCoupon(coupon)
        ));
    }
}
