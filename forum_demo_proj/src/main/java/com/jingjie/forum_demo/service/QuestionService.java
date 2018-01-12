package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.dao.QuestionDao;
import com.jingjie.forum_demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service for Question.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 12, 2018
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public Question getQuestiionViaId(int id){

        return questionDao.getQuestionViaId(id);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {

        return questionDao.getLatestQuestions(userId, offset, limit);
    }
}