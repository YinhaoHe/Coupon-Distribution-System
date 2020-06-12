package com.coupon.customers.service;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.constant.Constants;
import com.coupon.customers.utils.RowKeyGenUtil;
import com.coupon.customers.vo.Coupon;
import com.coupon.customers.vo.CouponTemplate;
import com.coupon.customers.vo.GetCouponTemplateRequest;
import com.coupon.customers.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>consume CouponTemplate in kafka</h1>
 */
@Slf4j
@Component
public class CustomerListener {

    private final User customer;
    private final long userId;
    private final List<CouponTemplate> coupons = new ArrayList<CouponTemplate>();

    @Autowired
    public CustomerListener(User customer) {
        this.customer = customer;
        this.userId = customer.getId();
    }

    @KafkaListener(topics = {Constants.TEMPLATE_TOPIC})
    public void receive(@Payload String couponTemplate,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("Customer Receive CouponTemplate: {}", couponTemplate);

        CouponTemplate ct;

        try {
            ct = JSON.parseObject(couponTemplate, CouponTemplate.class);
        } catch (Exception ex) {
            log.error("Parse CouponTemplate Error: {}", ex.getMessage());
            return;
        }

        if(this.userId == ct.getId()) {
            this.coupons.add(ct);
        }
    }

    private boolean useCoupon(GetCouponTemplateRequest request,
                                     Integer merchantsId, String couponTemplateId) throws Exception {

        byte[] FAMILY_I = Constants.CouponTable.FAMILY_I.getBytes();
        byte[] TOKEN = Constants.CouponTable.TOKEN.getBytes();

        // token
        if (request.getCouponTemplate().getHasToken()) {
            String token = redisTemplate.opsForSet().pop(couponTemplateId);
            if (null == token) {
                log.error("Token not exist: {}", couponTemplateId);
                return false;
            }
            recordTokenToFile(merchantsId, couponTemplateId, token);
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes(token));
        }
        this.coupons.remove(request.getCouponTemplate());
        return true;
    }
}