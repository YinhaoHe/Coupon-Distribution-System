package com.coupon.customers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Controller unified response</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** rror code: return 0 -- correct */
    private Integer errorCode = 0;

    /** Error message, return empty string when correct */
    private String errorMsg = "";

    /** Return value object */
    private Object data;

    /**
     * <h2>Correct response constructor</h2>
     * */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * <h2>success response</h2>
     * */
    public static Response success() {
        return new Response();
    }

    /**
     * <h2>failure response</h2>
     * */
    public static Response failure(String errorMsg) {
        return new Response(-1, errorMsg, null);
    }
}