package com.coupon.customers.controller;

import com.coupon.customers.log.LogConstants;
import com.coupon.customers.log.LogGenerator;
import com.coupon.customers.service.IFeedbackService;
import com.coupon.customers.service.IGetCouponTemplateService;
import com.coupon.customers.service.IInventoryService;
import com.coupon.customers.service.IUserCouponService;
import com.coupon.customers.vo.Coupon;
import com.coupon.customers.vo.Feedback;
import com.coupon.customers.vo.GetCouponTemplateRequest;
import com.coupon.customers.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>Coupon Rest Controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController {

    /** User Coupon Service */
    private final IUserCouponService userCouponService;

    /** coupon inventory Service */
    private final IInventoryService inventoryService;

    /** get CouponTemplate Service */
    private final IGetCouponTemplateService getCouponTemplateService;

    /** feedback Service */
    private final IFeedbackService feedbackService;

    /** HttpServletRequest */
    private final HttpServletRequest httpServletRequest;
    
    @Autowired
    public CouponController(IUserCouponService userCouponService,
                            IInventoryService inventoryService,
                            IGetCouponTemplateService getCouponTemplateService,
                            IFeedbackService feedbackService,
                            HttpServletRequest httpServletRequest) {
        this.userCouponService = userCouponService;
        this.inventoryService = inventoryService;
        this.getCouponTemplateService = getCouponTemplateService;
        this.feedbackService = feedbackService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * <h2>Get user coupon information</h2>
     * @param userId user id
     * @return {@link Response}
     * */
    @ResponseBody
    @GetMapping("/usercouponinfo")
    Response userCouponInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_COUPON_INFO,
                null
        );
        return userCouponService.getUserCouponInfo(userId);
    }

    /**
     * <h2>Get user used coupon information</h2>
     * @param userId User id
     * @return {@link Response}
     * */
    @ResponseBody
    @GetMapping("userusedcouponinfo")
    Response userUsedCouponInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId, LogConstants.ActionName.USER_USED_COUPON_INFO,
                null
        );
        return userCouponService.getUserUsedCouponInfo(userId);
    }

    /**
     * <h2>User use coupon</h2>
     * @param coupon {@link Coupon}
     * @return {@link Response}
     * */
    @ResponseBody
    @PostMapping("/userusecoupon")
    Response userUseCoupon(@RequestBody Coupon coupon) {

        LogGenerator.genLog(
                httpServletRequest,
                coupon.getUserId(),
                LogConstants.ActionName.USER_USE_COUPON,
                coupon
        );
        return userCouponService.userUseCoupon(coupon);
    }

    /**
     * <h2>get inventory info</h2>
     * @param userId user id
     * @return {@link Response}
     * */
    @ResponseBody
    @GetMapping("/inventoryinfo")
    Response inventoryInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.INVENTORY_INFO,
                null
        );
        return inventoryService.getInventoryInfo(userId);
    }

    /**
     * <h2>User get coupon</h2>
     * @param request {@link GetCouponTemplateRequest}
     * @return {@link Response}
     * */
    @ResponseBody
    @PostMapping("/gaincoupontemplate")
    Response gainCouponTemplate(@RequestBody GetCouponTemplateRequest request)
            throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                request.getUserId(),
                LogConstants.ActionName.GET_COUPON_TEMPLATE,
                request
        );
        return getCouponTemplateService.getCouponTemplate(request);
    }

    /**
     * <h2>user create feedback</h2>
     * @param feedback {@link Feedback}
     * @return {@link Response}
     * */
    @ResponseBody
    @PostMapping("/createfeedback")
    Response createFeedback(@RequestBody Feedback feedback) {

        LogGenerator.genLog(
                httpServletRequest,
                feedback.getUserId(),
                LogConstants.ActionName.CREATE_FEEDBACK,
                feedback
        );
        return feedbackService.createFeedback(feedback);
    }

    /**
     * <h2>user get all feedback </h2>
     * @param userId 用户 id
     * @return {@link Response}
     * */
    @ResponseBody
    @GetMapping("/getfeedback")
    Response getFeedback(Long userId) {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.GET_FEEDBACK,
                null
        );
        return feedbackService.getFeedback(userId);
    }

    /**
     * <h2>exception demo interface</h2>
     * @return {@link Response}
     * */
    @ResponseBody
    @GetMapping("/exception")
    Response exception() throws Exception {
        throw new Exception("Welcome To the demo of exception");
    }
}
