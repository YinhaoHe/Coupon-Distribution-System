package com.coupon.customers.controller;

import com.coupon.customers.log.LogConstants;
import com.coupon.customers.log.LogGenerator;
import com.coupon.customers.service.IUserService;
import com.coupon.customers.vo.Response;
import com.coupon.customers.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Create user Controller
 */
@Slf4j
@RestController
@RequestMapping("/coupon")
public class CreateUserController {

    /** Create User Service */
    private final IUserService userService;

    /** HttpServletRequest */
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public CreateUserController(IUserService userService,
                                HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * <h2>Create User</h2>
     * @param user {@link User}
     * @return {@link Response}
     * */
    @ResponseBody
    @PostMapping("/createuser")
    Response createUser(@RequestBody User user) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                -1L,
                LogConstants.ActionName.CREATE_USER,
                user
        );
        return userService.createUser(user);
    }
}
