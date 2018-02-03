package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.Question;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHolder;
import com.jingjie.forum_demo.model.ViewObject;
import com.jingjie.forum_demo.service.CommentService;
import com.jingjie.forum_demo.service.FollowService;
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

    private static final boolean IS_NOT_FOLLOW = false;
    private static final int ANY_USER = 0;
    private static final int DEF_OFFSET = 0;
    private static final int NUMBER_LIMIT = 10;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserHolder userHolder;

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
            viewObject.set("followCount", followService.getFollowerCount(
                    ForumDemoAppUtil.ENTITY_QUESTION,
                    question.getId()
            ));
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

        model.addAttribute("viewObjList", getLastestQuestionsInfo(ANY_USER,
                DEF_OFFSET, NUMBER_LIMIT));
        return ForumDemoAppUtil.INDEX_TEMPLATE;
    }

    /**
     *
     * Get the lastest 10 questions posted by a given user and user profile.
     * User profile includes:
     * 1) user instance
     * 2) the number of comments the user posted
     * 3) the number of followers of the user
     * 4) the number of followees that the user follows
     * 5) if it is a login user, check whether it follows the given user
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping (path = {"/userId/{userId}"}, method = {RequestMethod.GET})
    public String home(Model model, @PathVariable("userId") int userId) {

        model.addAttribute("viewObjList", getLastestQuestionsInfo(userId,
                DEF_OFFSET, NUMBER_LIMIT));
        User user = userService.getUserViaId(userId);
        ViewObject viewObj = new ViewObject();

        viewObj.set("user", user);
        viewObj.set("commentCount", commentService.getUserCommentCount(userId));
        viewObj.set("followers", followService.getFollowerCount(
                ForumDemoAppUtil.ENTITY_USER, userId));
        viewObj.set("followees", followService.getFolloweeCount(userId,
                ForumDemoAppUtil.ENTITY_USER));
        User loginUser = userHolder.getUser();
        if (loginUser != null) {
            viewObj.set("followed", followService.isFollower(
                    loginUser.getId(),
                    ForumDemoAppUtil.ENTITY_USER,
                    userId
            ));
        }
        else {
            viewObj.set("followed", IS_NOT_FOLLOW);
        }

        model.addAttribute("userProfile", viewObj);

        return ForumDemoAppUtil.USER_PROFILE_TEMPLATE;
    }
}
