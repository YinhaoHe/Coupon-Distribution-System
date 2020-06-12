package com.coupon.merchants.service.impl;

import com.alibaba.fastjson.JSON;
import com.coupon.merchants.constant.Constants;
import com.coupon.merchants.constant.ErrorCode;
import com.coupon.merchants.dao.MerchantsDao;
import com.coupon.merchants.entity.Merchants;
import com.coupon.merchants.service.IMerchantsServ;
import com.coupon.merchants.vo.CreateMerchantsRequest;
import com.coupon.merchants.vo.CreateMerchantsResponse;
import com.coupon.merchants.vo.Response;
import com.coupon.merchants.vo.couponTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>Merchant Service Creation Implementation</h1>
 */
@Slf4j
@Service
public class MerchantsServImpl implements IMerchantsServ {

    /** Merchants DB Interface */
    private MerchantsDao merchantsDao;
    /** kafka client */
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MerchantsServImpl(MerchantsDao merchantsDao,
                             KafkaTemplate<String, String> kafkaTemplate) {
        this.merchantsDao = merchantsDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public Response createMerchants(CreateMerchantsRequest request) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();

        ErrorCode errorCode = request.validate(merchantsDao);
        if (errorCode != ErrorCode.SUCCESS) {
            merchantsResponse.setId(-1);
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        } else {
            merchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
        }

        response.setData(merchantsResponse);

        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {

        Response response = new Response();

        Merchants merchants = merchantsDao.findById(id);
        if (null == merchants) {
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXIST.getCode());
            response.setErrorMsg(ErrorCode.MERCHANTS_NOT_EXIST.getDesc());
        }

        response.setData(merchants);

        return response;
    }

    @Override
    public Response distributeCouponTemplate(couponTemplate template) {

        Response response = new Response();
        /**verify this merchant*/
        ErrorCode errorCode = template.validate(merchantsDao);

        if (errorCode != ErrorCode.SUCCESS) {
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        } else {
            String cTemplate = JSON.toJSONString(template);
            kafkaTemplate.send(
                    Constants.TEMPLATE_TOPIC,
                    Constants.TEMPLATE_TOPIC,
                    cTemplate
            );
            log.info("DistributeCouponTemplates: {}", cTemplate);
        }

        return response;
    }
}
