package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * The service for User model and UserDao.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 12, 2018
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserViaId (int id) {

        return userDao.getUserViaId(id);
    }

    public User getUserViaName (String name) {

        return userDao.getUserViaName(name);
    }

    public Map<String, Object> register (String username, String password) {

        Map<String, Object> map = new HashMap<>();

        // check whether the username is null
        if (StringUtils.isBlank(username) == true) {

            map.put("msg", "The user name cannot be NULL!");
            return map;
        }

        // check whether the password is null
        if (StringUtils.isBlank(password) == true) {

            map.put("msg", "The password cannot be NULL!");
            return map;
        }

        // check whether the username exist already
        if (userDao.getUserViaName(username) != null) {

            map.put("msg", "The given username" + username + "has been used.");
            return map;
        }

        // encrypt password

        return map;
    }
}

