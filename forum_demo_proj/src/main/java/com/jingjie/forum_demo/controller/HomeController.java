package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.Question;
import com.jingjie.forum_demo.model.ViewObject;
import com.jingjie.forum_demo.service.QuestionService;
import com.jingjie.forum_demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Controller for the index page.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 11, 2018.
 *
 */
@Controller
public class HomeController {

    // Define logger object for the class
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    /**
     *
     * Get the number of "limit" questions.
     *
     * @param userId If userID equals 0, then return the lastest question
     *               posted by any user; otherwise, only return quesiton
     *               posted by the given user.
     * @param offset The number of question will be skipped from the newest
     *               to the oldest.
     * @param limit The number of questions will be returned.
     * @return
     */
    public List<ViewObject> getLastestQuestionsInfo (int userId, int offset,
                                                     int limit) {

        List<Question> queList = questionService.getLatestQuestions(
                userId, offset, limit);
        List<ViewObject> viewObjectList = new LinkedList<>();

        for (Question question : queList) {

            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUserViaId(
                    question.getUserId()));

            viewObjectList.add(viewObject);
        }

        return viewObjectList;
    }


    /**
     *
     * Get the latest 10 questions posted by any user.
     *
     * @param model
     * @return
     */
    @RequestMapping (path = {"/", "/index"}, method = {RequestMethod.GET})
    public String home(Model model) {

        model.addAttribute("viewObjList", getLastestQuestionsInfo(0, 0, 10));
        return ForumDemoAppUtil.INDEX_TEMPLATE;
    }

    /**
     *
     * Get the lastest 10 questions posted by a given user.
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping (path = {"/userId/{userId}"}, method = {RequestMethod.GET})
    public String home(Model model, @PathVariable("userId") int userId) {

        model.addAttribute("viewObjList", getLastestQuestionsInfo(userId, 0, 10));

        return ForumDemoAppUtil.INDEX_TEMPLATE;
    }
}
