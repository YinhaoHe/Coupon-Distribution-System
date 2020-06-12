package com.coupon.merchants.constant;

public enum TemplateColor {
    RED(1, "Red"),
    GREEN(2, "Green"),
    BLUE(3, "Blue");

    /** Color Code */
    private Integer code;

    /** Color description */
    private String color;

    TemplateColor(Integer code, String color) {
        this.code = code;
        this.color = color;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getColor() {
        return this.color;
    }
}
