package com.coupon.customers.utils;

import com.coupon.customers.vo.CouponTemplate;
import com.coupon.customers.vo.Feedback;
import com.coupon.customers.vo.GetCouponTemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <h1>RowKey Generator</h1>
 */
@Slf4j
public class RowKeyGenUtil {

    /**
     * <h2>Generate RowKey based on the provided CouponTemplate object</h2>
     * @param couponTemplate {@link CouponTemplate}
     * @return String RowKey
     * */
    public static String genCouponTemplateRowKey(CouponTemplate couponTemplate) {

        String couponInfo = String.valueOf(couponTemplate.getId()) + "_" + couponTemplate.getTitle();
        String rowKey = DigestUtils.md5Hex(couponInfo);
        log.info("GenCouponTemplateRowKey: {}, {}", couponInfo, rowKey);

        return rowKey;
    }

    /**
     * <h2>Generate RowKey based on the provided coupon request, which can only be used when getting coupons</h2>
     * Coupon RowKey = reversed(userId) + inverse(timestamp) + CouponTemplate RowKey
     * @param request {@link GetCouponTemplateRequest}
     * @return String RowKey
     * */
    public static String genCouponRowKey(GetCouponTemplateRequest request) {

        return new StringBuilder(String.valueOf(request.getUserId())).reverse().toString()
                + (Long.MAX_VALUE - System.currentTimeMillis())
                + genCouponTemplateRowKey(request.getCouponTemplate());
    }


    /**
     * <h2>Generate RowKey from Feedback</h2>
     * @param feedback {@link Feedback}
     * @return String RowKey
     * */
    public static String genFeedbackRowKey(Feedback feedback) {
        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString() +
                (Long.MAX_VALUE - System.currentTimeMillis());
    }
}