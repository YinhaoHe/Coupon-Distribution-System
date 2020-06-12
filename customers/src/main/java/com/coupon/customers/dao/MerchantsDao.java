package com.coupon.customers.dao;

import com.coupon.customers.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

    /**
     * <h2>Get merchant objects based on a list of id</h2>
     * @param ids 商户 ids
     * @return {@link Merchants}
     * */
    List<Merchants> findByIdIn(List<Integer> ids);
}

