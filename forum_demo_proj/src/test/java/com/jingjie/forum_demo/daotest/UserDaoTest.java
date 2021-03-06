package com.jingjie.forum_demo.daotest;

import com.jingjie.forum_demo.ForumDemoApplication;
import com.jingjie.forum_demo.dao.QuestionDao;
import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.service.FollowService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
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
@Sql("/database/user.sql")
//@Import(UserDao.class)
@SpringBootTest(classes = ForumDemoApplication.class)
public class UserDaoTest {

    @Autowired
    UserDao userDaoTest;

    // This method is to init the Question and User table at the same time without
    // dropping the chang of another.
    @Autowired
    QuestionDao questionDaoTest;

    @Autowired
    FollowService followService;

    // init user table method
    @Test
    public void initUserTable() {

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

    @Test
    public void addFollower () {

        for (int i = 0; i < 11; i ++) {
            for (int j = 1; j < i; j ++) {

                followService.follow(j, ForumDemoAppUtil.ENTITY_USER, i);
            }
        }
    }

    /**
     * Test UserDao methods.
     * 1) addUser(User):void method
     * 2) getUserViaId(int):User method
     * 3) getUserViaName(String):User method
     * 4) deleteUserViaId(int):void method
     * 5) updatePassword(int):void method
     */
    @Test
    public void userDaoTest() {

        initUserTable();

        // getUserViaId
        User userTest = new User();

        userTest = userDaoTest.getUserViaId(2);
        Assert.assertEquals(2, userTest.getId());

        userTest = userDaoTest.getUserViaId(12);
        Assert.assertNull(userTest);

        // getUSerViaName
        userTest = userDaoTest.getUserViaName("user2");
        Assert.assertEquals("user2", userTest.getName());

        userTest = userDaoTest.getUserViaName("random");
        Assert.assertNull(userTest);

        // deleteUserViaId
        userDaoTest.deleteUserViaId(2);
        userTest = userDaoTest.getUserViaName("user2");
        Assert.assertNull(userTest);

        // updatePassword
        userTest = userDaoTest.getUserViaId(3);
        userTest.setPassword("333");
        userDaoTest.updatePassword(userTest);
        userTest = userDaoTest.getUserViaId(3);
        Assert.assertEquals("333", userTest.getPassword());

    }
}
