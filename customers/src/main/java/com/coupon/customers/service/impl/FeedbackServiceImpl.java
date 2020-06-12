package com.coupon.customers.service.impl;

import com.alibaba.fastjson.JSON;
import com.coupon.customers.constant.Constants;
import com.coupon.customers.mapper.FeedbackRowMapper;
import com.coupon.customers.service.IFeedbackService;
import com.coupon.customers.utils.RowKeyGenUtil;
import com.coupon.customers.vo.Feedback;
import com.coupon.customers.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>Feedback Service Implement</h1>
 */
@Slf4j
@Service
public class FeedbackServiceImpl implements IFeedbackService {

    /** HBase */
    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public FeedbackServiceImpl(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public Response createFeedback(Feedback feedback) {

        if (!feedback.validate()) {
            log.error("Feedback Error: {}", JSON.toJSONString(feedback));
            return Response.failure("Feedback Error");
        }

        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genFeedbackRowKey(feedback)));

        put.addColumn(
                Bytes.toBytes(Constants.Feedback.FAMILY_I),
                Bytes.toBytes(Constants.Feedback.USER_ID),
                Bytes.toBytes(feedback.getUserId())
        );
        put.addColumn(
                Bytes.toBytes(Constants.Feedback.FAMILY_I),
                Bytes.toBytes(Constants.Feedback.TYPE),
                Bytes.toBytes(feedback.getType())
        );
        put.addColumn(
                Bytes.toBytes(Constants.Feedback.FAMILY_I),
                Bytes.toBytes(Constants.Feedback.TEMPLATE_ID),
                Bytes.toBytes(feedback.getTemplateId())
        );
        put.addColumn(
                Bytes.toBytes(Constants.Feedback.FAMILY_I),
                Bytes.toBytes(Constants.Feedback.COMMENT),
                Bytes.toBytes(feedback.getComment())
        );

        hbaseTemplate.saveOrUpdate(Constants.Feedback.TABLE_NAME, put);

        return Response.success();
    }

    @Override
    public Response getFeedback(Long userId) {

        byte[] reverseUserId = new StringBuilder(String.valueOf(userId)).reverse().toString().getBytes();
        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(reverseUserId));

        List<Feedback> feedbacks = hbaseTemplate.find(Constants.Feedback.TABLE_NAME, scan, new FeedbackRowMapper());

        return new Response(feedbacks);
    }
}