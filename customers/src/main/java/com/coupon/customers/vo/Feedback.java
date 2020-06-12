package com.coupon.customers.vo;

import com.coupon.customers.constant.FeedbackType;
import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>User comment/h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    /** User id */
    private Long userId;

    /** comment type */
    private String type;

    /** CouponTemplate RowKey, if the type is app, then it is null */
    private String templateId;

    /** content of the comment */
    private String comment;

    public boolean validate() {

        FeedbackType feedbackType = Enums.getIfPresent(
                FeedbackType.class, this.type.toUpperCase()
        ).orNull();

        return !(null == feedbackType || null == comment);
    }
}
