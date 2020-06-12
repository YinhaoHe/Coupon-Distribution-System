package com.coupon.customers.service;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.constant.Constants;
import com.coupon.customers.vo.CouponTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * <h1>consume CouponTemplate in kafka</h1>
 */
@Slf4j
@Component
public class ConsumeCouponTemplate {

    /** coupon related HBase services */
    private final IHBaseCouponService couponService;

    @Autowired
    public ConsumeCouponTemplate(IHBaseCouponService couponService) {
        this.couponService = couponService;
    }

    @KafkaListener(topics = {Constants.TEMPLATE_TOPIC})
    public void receive(@Payload String couponTemplate,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("Consumer Receive CouponTemplate: {}", couponTemplate);

        CouponTemplate ct;

        try {
            ct = JSON.parseObject(couponTemplate, CouponTemplate.class);
        } catch (Exception ex) {
            log.error("Parse CouponTemplate Error: {}", ex.getMessage());
            return;
        }

        log.info("DistributeCouponTemplateToHBase: {}", couponService.distributeCouponTemplateToHBase(ct));
    }
}