package com.coupon.merchants.vo;

import com.coupon.merchants.constant.ErrorCode;
import com.coupon.merchants.dao.MerchantsDao;
import com.coupon.merchants.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h2>Merchant Creation Request Obj</h2>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsRequest {

    /** Merchant Name */
    private String name;

    /** Merchant logo */
    private String logoUrl;

    /** Merchant License */
    private String businessLicenseUrl;

    /** Merchant phone number */
    private String phone;

    /** Merchant Address */
    private String address;

    /**
     * <h2>Request Verification</h2>
     * @param merchantsDao {@link MerchantsDao}
     * @return {@link ErrorCode}
     * */
    public ErrorCode validate(MerchantsDao merchantsDao) {

        if (null == this.name) {
            return ErrorCode.EMPTY_NAME;
        }

        if (merchantsDao.findByName(this.name) != null) {
            return ErrorCode.DUPLICATE_NAME;
        }

        if (null == this.logoUrl) {
            return ErrorCode.EMPTY_LOGO;
        }

        if (null == this.businessLicenseUrl) {
            return ErrorCode.EMPTY_BUSINESS_LICENSE;
        }

        if (null == this.address) {
            return ErrorCode.EMPTY_ADDRESS;
        }

        if (null == this.phone) {
            return ErrorCode.ERROR_PHONE;
        }

        return ErrorCode.SUCCESS;
    }

    /**
     * <h2>Convert request objects to merchant objects</h2>
     * @return {@link Merchants}
     * */
    public Merchants toMerchants() {

        Merchants merchants = new Merchants();

        merchants.setName(name);
        merchants.setLogoUrl(logoUrl);
        merchants.setBusinessLicenseUrl(businessLicenseUrl);
        merchants.setPhone(phone);
        merchants.setAddress(address);

        return merchants;
    }
}