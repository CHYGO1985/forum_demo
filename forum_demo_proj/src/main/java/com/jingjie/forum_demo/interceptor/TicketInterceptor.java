package com.jingjie.forum_demo.interceptor;

import com.jingjie.forum_demo.dao.LoginTicketDao;
import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.LoginTicket;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHoler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import util.ForumDemoAppUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 * The interceptor for getting user from a valid ticket from request.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 14, 2018
 */
@Component
public class TicketInterceptor implements HandlerInterceptor {

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    UserHoler userHoler;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String ticket = null;

        // check whether the request has "ticket"
        // if it has get the value of the ticket, otherwise return false
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ForumDemoAppUtil.USER_TOKEN)) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        // according to the ticket value, get the login ticket and check
        // whether it is expired
        if (ticket != null) {

            LoginTicket loginTicket = loginTicketDao.getTikcetiaTikcetVal(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date())
                    || loginTicket.getStatus() == ForumDemoAppUtil.INVALID_TICKET) {
                return true;
            }

            // get the user according to the userId stored in UserHoler
            User user = userDao.getUserViaId(loginTicket.getUserId());
            userHoler.setUser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        // if UserHolder has a user instance, add it to modelandview
        if (modelAndView != null && userHoler.getUser() != null) {
            modelAndView.addObject("user", userHoler.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        // remove the user from UserHolder
        userHoler.removeUser();
    }
}
