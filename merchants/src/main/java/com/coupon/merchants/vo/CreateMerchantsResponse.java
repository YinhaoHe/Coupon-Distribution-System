package com.coupon.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Merchant Creation Response Obj</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsResponse {

    /** Merchant id: -1 if the creation fails */
    private Integer id;
}
