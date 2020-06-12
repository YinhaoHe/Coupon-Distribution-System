package com.coupon.merchants.controller;

import com.alibaba.fastjson.JSON;
import com.coupon.merchants.service.IMerchantsServ;
import com.coupon.merchants.vo.CreateMerchantsRequest;
import com.coupon.merchants.vo.Response;
import com.coupon.merchants.vo.couponTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>Merchants Services Controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/merchants") //set URI prefix
public class MerchantsCtl {
    /** Merchants Services Interface */
    private final IMerchantsServ merchantsServ;

    @Autowired
    public MerchantsCtl(IMerchantsServ merchantsServ) {
        this.merchantsServ = merchantsServ;
    }

    @ResponseBody
    @PostMapping("/create")
    public Response createMerchants(@RequestBody CreateMerchantsRequest request) {

        log.info("CreateMerchants: {}", JSON.toJSONString(request));
        return merchantsServ.createMerchants(request);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Response buildMerchantsInfo(@PathVariable Integer id) {

        log.info("BuildMerchantsInfo: {}", id);
        return merchantsServ.buildMerchantsInfoById(id);
    }

    @ResponseBody
    @PostMapping("/distribute")
    public Response distributeCouponTemplate(@RequestBody couponTemplate template) {

        log.info("DistributeCouponTemplate: {}", template);
        return merchantsServ.distributeCouponTemplate(template);
    }
}
