package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.Question;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHolder;
import com.jingjie.forum_demo.service.QuestionService;
import com.jingjie.forum_demo.service.UserService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import com.jingjie.forum_demo.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import java.util.Date;

/**
 *
 * The controller for question related actions.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 19, 2018
 */
@Controller
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuestionService questionService;

    @Autowired
    UserHolder userHolder;

    @Autowired
    UserService userService;

    @RequestMapping (value = {"/question/add"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {

        try {

            // created a new question instance
            Question question = new Question();
            question.setTitle(title);
            question.setCreateDate(new Date());
            question.setContent(content);
            question.setCommentCount(0);

            // if it is an anonymous user, set anonymous user id
            User curUser = userHolder.getUser();
            if (curUser == null) {
                question.setUserId(ForumDemoAppUtil.ANONYMOUS_USERID);
            }
            else {
                // otherwise set the user id from UserHoler
                question.setUserId(curUser.getId());
            }

            if (questionService.addQuestion(question) >
                    ForumDemoAppUtil.QUESTION_ADD_FAIL) {
                // return JSONString code 0, means add successfully
                return JSONUtil.getJSONStringCode(ForumDemoAppUtil.
                        QUESTION_SUBMIT_SUCCESS);
            }
        }
        catch (Exception ex) {

            logger.error("话题添加失败: " + ex.getMessage());
        }

        return JSONUtil.getJSONStringMsg(
                ForumDemoAppUtil.QUESTOON_SUBMIT_FAIL, "Submit Failed!");
    }

    @RequestMapping (path = {"/question/{qid}"}, method = {RequestMethod.GET})
    public String showQuestionViaId (Model model,
                                     @PathVariable("qid") int qid) {

        Question question = questionService.getQuestiionViaId(qid);

        // add question and poster's information to the model
        model.addAttribute("question", question);
        model.addAttribute("user", userService.getUserViaId(
                question.getUserId()));

        return "detail";
    }
}
