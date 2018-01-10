package com.jingjie.forum_demo.daotest;

import com.jingjie.forum_demo.ForumDemoApplication;
import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 *
 * Test DAO interfaces.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 10, 2018.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
// by default. it searches from src/main/resources
@Sql("/database/forum_demo.sql")
//@Import(UserDao.class)
@SpringBootTest(classes = ForumDemoApplication.class)
public class UserDaoTest {

    @Autowired
    UserDao userDaoTest;

    // Test addUser() method
    @Test
    public void addUserTest() {

        Random random = new Random();

        for (int i = 0; i < 11; i ++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("user%d", i + 1));
            user.setPassword("");
            user.setSalt("");
            userDaoTest.addUser(user);
        }

    }

    // Test getUserViaId(int):User method

    // Test getUserViaName(String):User method

    // Test deleteUserViaId(int):void method

    // Test updatePassword(int):void method

}
