package com.coupon.merchants.service;

import com.alibaba.fastjson.JSON;
import com.coupon.merchants.vo.CreateMerchantsRequest;
import com.coupon.merchants.vo.couponTemplate;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Merchant Service Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServTest {

    @Autowired
    private IMerchantsServ merchantsServ;

    /**
     * {"data":{"id":3},"errorCode":0,"errorMsg":""}
     * */
    @Test
    @Transactional
    public void testCreateMerchantServ() {

        CreateMerchantsRequest request = new CreateMerchantsRequest();
        request.setName("Middleware Shop");
        request.setLogoUrl("https://unsplash.com/photos/Bd7gNnWJBkU");
        request.setBusinessLicenseUrl("https://www.ics.uci.edu/~cs237/");
        request.setPhone("1234567890");
        request.setAddress("Donald Bren Hall, 6210, Irvine, CA 92697");

        System.out.println(JSON.toJSONString(merchantsServ.createMerchants(request)));
    }

    /**
     * {"data":{"address":"Donald Bren Hall, 6210, Irvine, CA 92697","businessLicenseUrl":"https://www.ics.uci.edu/~cs237/",
     * "id":3,"isAudit":false,"logoUrl":"https://unsplash.com/photos/Bd7gNnWJBkU",
     * "name":"Middleware Shop","phone":"1234567890"},"errorCode":0,"errorMsg":""}
     */
    @Test
    public void testBuildMerchantsInfoById() {
        System.out.println(JSON.toJSONString(merchantsServ.buildMerchantsInfoById(3)));
    }

    /**
     * {"background":2,"desc":"Description: Middleware","end":1589238046939,"hasToken":false,"id":3,"limit":10000,
     * "start":1587510046938,"summary":"Summary: Middleware course","title":"Middleware discount"}
     */
    @Test
    public void testDistributeCouponTemplate() {

        couponTemplate coupon = new couponTemplate();
        coupon.setId(5);
        coupon.setTitle("Middleware discount-4");
        coupon.setSummary("Summary: Middleware course");
        coupon.setDesc("Description: Middleware");
        coupon.setLimit(10000L);
        coupon.setHasToken(false);
        coupon.setBackground(2);
        coupon.setStart(DateUtils.addDays(new Date(), -10));
        coupon.setEnd(DateUtils.addDays(new Date(), 10));

        System.out.println(JSON.toJSONString(
                merchantsServ.distributeCouponTemplate(coupon)
        ));
    }
}
