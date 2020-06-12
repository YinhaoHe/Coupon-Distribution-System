package com.coupon.merchants.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <h1>Merchant Obj Model</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchants {
    /** Merchant id, Primary Key*/
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    /** Merchant name, globally unique */
    @Basic
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** Merchant logo */
    @Basic
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    /** Merchant License */
    @Basic
    @Column(name = "business_license_url", nullable = false)
    private String businessLicenseUrl;

    /** Merchant Phone Number */
    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    /** Merchant Address */
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    /** if Merchant Authorized  */
    @Basic
    @Column(name = "is_audit", nullable = false)
    private Boolean isAudit = false;

}
