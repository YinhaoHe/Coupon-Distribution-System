package com.coupon.customers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>User Object</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** User id */
    private Long id;

    /** User basic info */
    private BaseInfo baseInfo;

    /** User extra info */
    private OtherInfo otherInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseInfo {

        private String name;
        private Integer age;
        private String gender;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherInfo {

        private String phone;
        private String address;
    }
}