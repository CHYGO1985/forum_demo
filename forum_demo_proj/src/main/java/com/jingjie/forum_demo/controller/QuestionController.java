package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.*;
import com.jingjie.forum_demo.service.*;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import com.jingjie.forum_demo.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private static final int FOLLOWER_LIMIT = 20;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserHolder userHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;

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
                return JSONUtil.getJSONStringViaCode(ForumDemoAppUtil.
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

        // get the question's comments infor
        List<Comment> comList = commentService.getCommentViaEntity(qid,
                ForumDemoAppUtil.ENTITY_QUESTION);
        List<ViewObject> comments = new LinkedList<>();
        for (Comment comment : comList) {

            ViewObject obj = new ViewObject();
            obj.set("comment", comment);
            User user = userHolder.getUser();
            if (user == null) {
                obj.set("liked", 0);
            }
            else {
                // get the like status related to the login user
                obj.set("liked", likeService.getLikeStatus(user.getId(),
                        ForumDemoAppUtil.ENTITY_COMMENT,
                        comment.getId()));
            }

            obj.set("likeCount", likeService.getLikeCount(
                    ForumDemoAppUtil.ENTITY_COMMENT,
                    comment.getId()));

            obj.set("user", userService.getUserViaId(comment.getUserId()));
            comments.add(obj);
        }
        model.addAttribute("comments", comments);

        // get followers infor
        List<Integer> followerIds = followService.getFollowersId(
                ForumDemoAppUtil.ENTITY_QUESTION,
                qid,
                FOLLOWER_LIMIT
        );
        List<ViewObject> followUsers = new LinkedList<>();

        for (Integer userId : followerIds) {

            User user = userService.getUserViaId(userId);
            if (user == null) {
                continue;
            }

            ViewObject obj = new ViewObject();
            obj.set("name", user.getName());
            obj.set("headUrl", user.getHeadUrl());
            obj.set("id", user.getId());
            followUsers.add(obj);
        }

        model.addAttribute("followUsers", followUsers);
        // check whether current login user follows the question
        User loginUser = userHolder.getUser();
        if (loginUser != null) {
            model.addAttribute("followed",
                    followService.isFollower(loginUser.getId(),
                            ForumDemoAppUtil.ENTITY_QUESTION,
                            qid
                            ));
        }
        else {
            model.addAttribute("followed", false);
        }

        return "detail";
    }
}
