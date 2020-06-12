package com.coupon.customers.service.impl;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.constant.Constants;
import com.coupon.customers.mapper.CouponTemplateRowMapper;
import com.coupon.customers.service.IGetCouponTemplateService;
import com.coupon.customers.utils.RowKeyGenUtil;
import com.coupon.customers.vo.CouponTemplate;
import com.coupon.customers.vo.GetCouponTemplateRequest;
import com.coupon.customers.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>User Get Coupon Service Implement</h1>
 */
@Slf4j
@Service
public class GetCouponTemplateServiceImpl implements IGetCouponTemplateService {

    /** HBase */
    private final HbaseTemplate hbaseTemplate;

    /** redis */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public GetCouponTemplateServiceImpl(HbaseTemplate hbaseTemplate,
                                       StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response getCouponTemplate(GetCouponTemplateRequest request) throws Exception {

        CouponTemplate couponTemplate;
        String couponTemplateId = RowKeyGenUtil.genCouponTemplateRowKey(
                request.getCouponTemplate());
        // verify
        try {
            couponTemplate = hbaseTemplate.get(
                    Constants.CouponTemplateTable.TABLE_NAME,
                    couponTemplateId,
                    new CouponTemplateRowMapper()
            );
        } catch (Exception ex) {
            log.error("Get CouponTemplate Error: {}",
                    JSON.toJSONString(request.getCouponTemplate()));
            return Response.failure("Get CouponTemplate Error!");
        }

        if (couponTemplate.getLimit() <= 1 && couponTemplate.getLimit() != -1) {
            log.error("CouponTemplate Limit Max: {}",
                    JSON.toJSONString(request.getCouponTemplate()));
            return Response.failure("CouponTemplate Limit Max!");
        }

        Date cur = new Date();
        if (!(cur.getTime() >= couponTemplate.getStart().getTime()
                && cur.getTime() < couponTemplate.getEnd().getTime())) {
            log.error("CouponTemplate ValidTime Error: {}",
                    JSON.toJSONString(request.getCouponTemplate()));
            return Response.failure("CouponTemplate ValidTime Error!");
        }

        // reduce limitation
        if (couponTemplate.getLimit() != -1) {
            List<Mutation> datas = new ArrayList<>();
            byte[] FAMILY_C = Constants.CouponTemplateTable.FAMILY_C.getBytes();
            byte[] LIMIT = Constants.CouponTemplateTable.LIMIT.getBytes();

            Put put = new Put(Bytes.toBytes(couponTemplateId));
            put.addColumn(FAMILY_C, LIMIT,
                    Bytes.toBytes(couponTemplate.getLimit() - 1));
            datas.add(put);

            hbaseTemplate.saveOrUpdates(Constants.CouponTemplateTable.TABLE_NAME,
                    datas);
        }

        // put it to user's coupon list
        if (!addCouponForUser(request, couponTemplate.getId(), couponTemplateId)) {
            return Response.failure("GetCouponTemplate Failure!");
        }

        return Response.success();
    }

    /**
     * <h2>add coupon to user</h2>
     * @param request {@link GetCouponTemplateRequest}
     * @param merchantsId Merchant id
     * @param couponTemplateId CouponTemplate id
     * @return true/false
     * */
    private boolean addCouponForUser(GetCouponTemplateRequest request,
                                   Integer merchantsId, String couponTemplateId) throws Exception {

        byte[] FAMILY_I = Constants.CouponTable.FAMILY_I.getBytes();
        byte[] USER_ID = Constants.CouponTable.USER_ID.getBytes();
        byte[] TEMPLATE_ID = Constants.CouponTable.TEMPLATE_ID.getBytes();
        byte[] TOKEN = Constants.CouponTable.TOKEN.getBytes();
        byte[] ASSIGNED_DATE = Constants.CouponTable.ASSIGNED_DATE.getBytes();
        byte[] CON_DATE = Constants.CouponTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genCouponRowKey(request)));
        put.addColumn(FAMILY_I, USER_ID, Bytes.toBytes(request.getUserId()));
        put.addColumn(FAMILY_I, TEMPLATE_ID, Bytes.toBytes(couponTemplateId));
        // token
        if (request.getCouponTemplate().getHasToken()) {
            String token = redisTemplate.opsForSet().pop(couponTemplateId);
            if (null == token) {
                log.error("Token not exist: {}", couponTemplateId);
                return false;
            }
            recordTokenToFile(merchantsId, couponTemplateId, token);
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes(token));
        } else {
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes("-1"));
        }
        // assigned date of coupon
        put.addColumn(FAMILY_I, ASSIGNED_DATE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        // consume date of coupon
        put.addColumn(FAMILY_I, CON_DATE, Bytes.toBytes("-1"));

        datas.add(put);

        hbaseTemplate.saveOrUpdates(Constants.CouponTable.TABLE_NAME, datas);

        return true;
    }

    /**
     * <h2>record used token </h2>
     * @param merchantsId Merchant id
     * @param couponTemplateId Coupon id
     * @param token distributed coupon token
     * */
    private void recordTokenToFile(Integer merchantsId, String couponTemplateId,
                                   String token) throws Exception {

        Files.write(
                Paths.get(Constants.TOKEN_DIR, String.valueOf(merchantsId),
                        couponTemplateId + Constants.USED_TOKEN_SUFFIX),
                (token + "\n").getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );
    }
}
