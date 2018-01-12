package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User getUserViaId(int id) {

        return userDao.getUserViaId(id);
    }

    public User getUserViaName(String name) {

        return userDao.getUserViaName(name);
    }
}
