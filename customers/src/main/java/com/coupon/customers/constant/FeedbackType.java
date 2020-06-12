package com.coupon.customers.constant;

/**
 * <h1>Feedback Type</h1>
 */
public enum FeedbackType {

    COUPON("coupon", "Comments on coupons"),
    APP("app", "Comments on app");

    /** Comment type code */
    private String code;

    /** Comment type description */
    private String desc;

    FeedbackType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}