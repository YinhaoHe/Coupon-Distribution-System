package com.coupon.customers.constant;

/**
 * <h1>Coupon Status</h1>
 */
public enum CouponStatus {

    UNUSED(1, "UNUSED"),
    USED(2, "USED"),
    ALL(3, "ALL DISTRIBUTED");

    /** Status Code */
    private Integer code;

    /** Status Description */
    private String desc;

    CouponStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
