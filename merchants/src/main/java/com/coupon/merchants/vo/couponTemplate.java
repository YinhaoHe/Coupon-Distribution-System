package com.coupon.merchants.vo;

import com.coupon.merchants.constant.ErrorCode;
import com.coupon.merchants.dao.MerchantsDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>Definition of coupon objects</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class couponTemplate {

    /** Merchant id */
    private Integer id;

    /** Coupon Title */
    private String title;

    /** Coupon Summary */
    private String summary;

    /** Coupon Description */
    private String desc;

    /** Maximum Coupon */
    private Long limit;

    /** Whether the coupon has Token for merchant verification */
    private Boolean hasToken; // token stored in the Redis Set, obtained from Redis

    /** Coupon background color */
    private Integer background;

    /** Coupon start time */
    private Date start;

    /** Coupon end time */
    private Date end;

    /**
     * <h2>Verify coupons/h2>
     * @param merchantsDao {@link MerchantsDao}
     * @return {@link ErrorCode}
     * */
    public ErrorCode validate(MerchantsDao merchantsDao) {

        if (null == merchantsDao.findById(id)) {
            return ErrorCode.MERCHANTS_NOT_EXIST;
        }

        return ErrorCode.SUCCESS;
    }
}
