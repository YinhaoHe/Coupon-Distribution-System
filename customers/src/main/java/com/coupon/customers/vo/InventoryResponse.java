package com.coupon.customers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1>Inventory request response</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    /** Uer id */
    private Long userId;

    /** Coupon Template Information */
    private List<CouponTemplateInfo> couponTemplateInfos;
}
