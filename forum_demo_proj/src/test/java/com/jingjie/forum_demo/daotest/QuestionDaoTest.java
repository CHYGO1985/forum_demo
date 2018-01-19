package com.jingjie.forum_demo.daotest;

import com.jingjie.forum_demo.ForumDemoApplication;
import com.jingjie.forum_demo.dao.QuestionDao;
import com.jingjie.forum_demo.model.Question;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 *
 * Test QuestionDao interface.
 * NB: I used different name convention compared with UserDaoTest.
 *
 * @author jingjiejiang
 * @history
 * 1. Created Jan 11, 2018
 */
@RunWith (SpringJUnit4ClassRunner.class)
@Sql ("/database/question.sql")
@SpringBootTest (classes = ForumDemoApplication.class)
public class QuestionDaoTest {

    @Autowired
    QuestionDao questionDaoTest;

    /**
     * Test addQuestion(Question):void method.
     * 1) insert 11 questions into question table.
     */
    @Test
    public void questionsShouldAdded() {

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

    /**
     * Test getQuestionViaId(int):Question
     * 1) use an valid id
     * 2) use an invalid id
     */
    @Test
    public void givenIdThenReturnQuestion() {

        questionsShouldAdded();

        Question questionTest = new Question();
        questionTest = questionDaoTest.getQuestionViaId(22);
        Assert.assertEquals("Test 3", questionTest.getTitle());

        questionTest = questionDaoTest.getQuestionViaId(35);
        Assert.assertNull(questionTest);
    }

    /**
     * Test getLastestQuestions(int, int, int):List<Question>
     */
    @Test
    public void givenOffsetAndLimitThenReturnQues() {

        questionsShouldAdded();

        List<Question> list = questionDaoTest.getLatestQuestions(0, 0, 5);
        Assert.assertEquals("Test 11", list.get(0).getTitle());
    }
}
