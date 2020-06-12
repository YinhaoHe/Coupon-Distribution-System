package com.coupon.merchants.dao;

import com.coupon.merchants.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>Merchants Dao Interface</h1>
 */

public interface MerchantsDao extends JpaRepository<Merchants, Integer> {
    /**
     * <h2>Get merchant object based on id</h2>
     * @param id Merchant id
     * @return {@link Merchants}
     * */
    Merchants findById(Integer id);

    /**
     * <h2>Obtain merchant object based on merchant name</h2>
     * @param name Merchant name
     * @return {@link Merchants}
     * */
    Merchants findByName(String name);
}
