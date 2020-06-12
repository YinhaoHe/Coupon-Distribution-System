package com.coupon.customers.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>Abstract Service test class/h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractServiceTest {
    Long userId;

    @Before
    public void init() {

        userId = 119175L;
    }
}
