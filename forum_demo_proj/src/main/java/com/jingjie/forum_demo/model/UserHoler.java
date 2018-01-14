package com.jingjie.forum_demo.model;

import org.springframework.stereotype.Component;

/**
 *
 * The class to hold login users to use in interceptor.
 *
 * @author jingjiejiang
 * @history
 * 1. Jan 14, 2018
 */
@Component
public class UserHoler {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public User getUser() {
        return userThreadLocal.get();
    }

    public void setUser(User user) {
        userThreadLocal.set(user);
    }

    public void removeUser() {
        userThreadLocal.remove();
    }
}
