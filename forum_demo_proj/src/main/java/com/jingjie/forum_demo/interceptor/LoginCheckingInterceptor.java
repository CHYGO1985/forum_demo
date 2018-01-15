package com.jingjie.forum_demo.interceptor;

import com.jingjie.forum_demo.model.UserHoler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * The interceptor is to check whether a user has login.
 *
 * @author jingjiejiang
 * @history
 * 1. Jan 15, 2018
 */
@Component
public class LoginCheckingInterceptor implements HandlerInterceptor{

    @Autowired
    UserHoler userHoler;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // check whether the userHolder have any user, if not,
        // redirect to login page with the current visited URI
        // added to path
        if (userHoler.getUser() == null) {
            httpServletResponse.sendRedirect("/regislogin?next=" + httpServletRequest.getRequestURI());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
