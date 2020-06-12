package com.coupon.merchants.constant;

/**
 * <h2>Error Code enum Definition</h2>
 */
public enum ErrorCode {
    SUCCESS(0, ""),
    DUPLICATE_NAME(1, "Duplicate Merchant Name"),
    EMPTY_LOGO(2, "Empty Merchant logo"),
    EMPTY_BUSINESS_LICENSE(3, "Empty Merchant Business License"),
    ERROR_PHONE(4, "Merchant Phone Number Error"),
    EMPTY_ADDRESS(5, "Empty Merchant Address"),
    MERCHANTS_NOT_EXIST(6, "Merchant Not Exist"),
    EMPTY_NAME(7, "Empty Merchant Name");

    /** Error Code */
    private Integer code;

    /** Error Description */
    private String desc;

    ErrorCode(Integer code, String desc) {
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
