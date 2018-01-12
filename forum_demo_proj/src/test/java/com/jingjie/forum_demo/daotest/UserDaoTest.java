package com.jingjie.forum_demo.daotest;

import com.jingjie.forum_demo.ForumDemoApplication;
import com.jingjie.forum_demo.dao.QuestionDao;
import com.jingjie.forum_demo.dao.UserDao;
import com.jingjie.forum_demo.model.Question;
import com.jingjie.forum_demo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
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

    // This method is to init the Question and User table at the same time without
    // dropping the chang of another.
    @Autowired
    QuestionDao questionDaoTest;

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

        for (int i = 0; i < 11; i ++) {

            Question question = new Question();
            question.setUserId(i + 1);
            question.setTitle(String.format("Test %d", i + 1));
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * i * 5);
            question.setCreateDate(date);
            question.setContent(String.format("XXXXXX %d", i + 1));
            question.setCommentCount(i);

            questionDaoTest.addQuestion(question);
        }
    }

    /*
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

    */

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
