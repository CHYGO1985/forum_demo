package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.dao.LoginTicketDao;
import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.LoginTicket;
import com.jingjie.forum_demo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.CryptUtil;

import java.util.*;

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

    @Autowired
    LoginTicketDao loginTicketDao;


    public User getUserViaId (int id) {

        return userDao.getUserViaId(id);
    }

    public User getUserViaName (String name) {

        return userDao.getUserViaName(name);
    }

    /**
     *
     * According to the given username and password, create and add a user to
     * the user table.
     *
     * @param username
     * @param password
     * @return
     */
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

        // encrypt password and add the new user to the database
        User user = new User();
        user.setName(username);
        String salt = UUID.randomUUID().toString().substring(0, 5);
        user.setSalt(salt);
        String url = String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000));
        user.setHeadUrl(url);
        user.setPassword(CryptUtil.MD5(password + salt));
        userDao.addUser(user);

        return map;
    }

    /**
     *
     * Tne method is to login a user.
     *
     * @param username
     * @param password
     * @return
     */
    public Map<String, Object> login (String username, String password) {

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

        // check whether username exists in the user table
        User user = userDao.getUserViaName(username);
        if(user == null) {

            map.put("msg", "The username or password is not correct.");
            return map;
        }

        // check whether the password matches the password in the user table
        if (CryptUtil.MD5(password + user.getSalt()).equals(
                user.getPassword()) == false) {

            map.put("msg", "The username or password is not correct.");
        }

        // create a login ticket, add it to the databse and add it to the returning map
        String ticket = createTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId());

        return map;
    }

    /**
     *
     * The method is to generate a ticket for the user.
     *
     * @param userId
     * @return
     */
    public String createTicket(int userId) {

        LoginTicket logTicket = new LoginTicket();
        logTicket.setUserId(userId);
        logTicket.setStatus(0);

        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        logTicket.setExpired(date);

        String ticket = UUID.randomUUID().toString().replaceAll("-", "");
        logTicket.setTicket(ticket);

        loginTicketDao.addTicket(logTicket);

        return logTicket.getTicket();
    }
}

