package com.coupon.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * General Response Object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    /** Error code, correct->return 0 */
    private Integer errorCode = 0;

    /** Error message, correct->return empty string */
    private String errorMsg = "";

    /** return Object */
    private Object data;

    /**
     * <h2>correct response constructor</h2>
     * @param data return value-object
     * */
    public Response(Object data) {
        this.data = data;
    }
}
