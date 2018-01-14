package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * The controller for login and register.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 13, 2018
 */
@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    final String USER_TOKEN = "ticket";
    final int COOKIE_DEF_AVAIL_TIME = 2600 * 24 * 5;
    final String LOGIN_ERROR_MSG_KEY = "msg";

    @Autowired
    UserService userService;

    // The method for register a user, password and username will be passed from
    // a form
    @RequestMapping (path = {"/reg/"}, method = {RequestMethod.POST})
    public String register (Model model,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam(value = "rememberme", defaultValue = "false")
                            boolean isRemembered,
                            HttpServletResponse httpServletResponse) {

        // register the user via UserService
        // 1)if map does not contains "ticket", return the content of "msg", return to
        // login page
        // 2) otherwise get the ticket and put it into the cookies of http response, redirect to home page
        try {

            Map<String, Object> map = userService.register(username, password);

            if (map.containsKey(USER_TOKEN)) {

                Cookie cookie = new Cookie(USER_TOKEN,
                        map.get(USER_TOKEN).toString());
                cookie.setPath("/");
                if (isRemembered == true) {
                    cookie.setMaxAge(COOKIE_DEF_AVAIL_TIME);
                }
                httpServletResponse.addCookie(cookie);

                return "redirect:/";
            }

            model.addAttribute(LOGIN_ERROR_MSG_KEY,
                    map.get(LOGIN_ERROR_MSG_KEY));

            return "login";
        }
        catch (Exception exp) {

            log.error("Login Error :" + exp.getMessage());
            return "login";
        }
    }

    // The method for both login and register
    @RequestMapping (path = {"/regislogin"}, method = {RequestMethod.GET})
    public String registerAndLogin () {

        return "login";
    }

    // The method for login a user.
    @RequestMapping (path = {"/login/"}, method = {RequestMethod.POST})
    public String login (Model model,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam(value = "rememberme", defaultValue = "false")
                         boolean isRemembered,
                         HttpServletResponse httpServletResponse) {

        // use UserService to login a user
        // 1)if the returned map contains "ticket", add the the ticket to cookie
        // and redirect to HOME page
        // 2) else add "msg" to model, and redirect to "/regislogin" page
        try {

            Map<String, Object> map = userService.login(username, password);

            if (map.containsKey(USER_TOKEN)) {

                Cookie cookie = new Cookie(USER_TOKEN,
                        map.get(USER_TOKEN).toString());
                cookie.setPath("/");
                if (isRemembered == true) {
                    cookie.setMaxAge(COOKIE_DEF_AVAIL_TIME);
                }
                httpServletResponse.addCookie(cookie);

                return "redirect:/";
            }

            model.addAttribute(LOGIN_ERROR_MSG_KEY,
                    map.get(LOGIN_ERROR_MSG_KEY));

            return "login";
        }
        catch (Exception exp) {

            log.error("Login Error :" + exp.getMessage());
            return "login";
        }

    }
}
