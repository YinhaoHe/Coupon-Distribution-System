package com.coupon.customers.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Log object</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogObject {

    /** Log action type */
    private String action;

    /** User id */
    private Long userId;

    /** Current timestamp */
    private Long timestamp;

    /** Client IP address */
    private String remoteIp;

    /** Log information */
    private Object info = null;
}
