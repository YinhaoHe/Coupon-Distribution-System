package com.coupon.customers.service;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>Create User Service Test</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testCreateUser() throws Exception {

        User user = new User();
        user.setBaseInfo(
                new User.BaseInfo("JesseX", 20, "m")
        );
        user.setOtherInfo(
                new User.OtherInfo("123456", "Inner Ring Rd, Irvine, CA 92697")
        );

        // {"data":{"baseInfo":{"age":20,"gender":"m","name":"JesseX"},
        // "id":119175,
        // "otherInfo":{"address":"Inner Ring Rd, Irvine, CA 92697","phone":"123456"}},
        // "errorCode":0,"errorMsg":""}
        System.out.println(JSON.toJSONString(userService.createUser(user)));
    }
}