package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.event.EventModel;
import com.jingjie.forum_demo.event.EventProducer;
import com.jingjie.forum_demo.event.EventType;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHolder;
import com.jingjie.forum_demo.model.ViewObject;
import com.jingjie.forum_demo.service.CommentService;
import com.jingjie.forum_demo.service.FollowService;
import com.jingjie.forum_demo.service.QuestionService;
import com.jingjie.forum_demo.service.UserService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import com.jingjie.forum_demo.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
public class FollowController {

    @Autowired
    FollowService followService;

    @Autowired
    UserHolder userHolder;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    CommentService commentService;

    private static final boolean IS_NOT_FOLLOW = false;
    private static final int MIN_ITEMS_NUM = 10;
    private static final int UNLOGIN_USER_ID = 0;

    /**
     *
     * Follow a user.
     *
     * @param userId
     * @return The current number of followees of current user.
     */
    @RequestMapping (path = {"/follower"}, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public String followUser (@RequestParam("userId") int userId) {

        User user = userHolder.getUser();
        if (user == null) {
            return JSONUtil.getJSONStringViaCode(ForumDemoAppUtil.
                    UNLOGIN_USER);
        }

        boolean res = followService.follow(user.getId(),
                ForumDemoAppUtil.ENTITY_USER,
                userId);

        if (res == true) {
            eventProducer.triggerEvent(new EventModel(EventType.FOLLOW).
                    setActorId(user.getId()).
                    setEntityId(userId).
                    setEntityOwnerId(userId).
                    setEntityType(ForumDemoAppUtil.ENTITY_USER));
        }

        // a new follow happen, so return the new num of followee regarding current user
        // (enetity that is followed  by the current user)
        return JSONUtil.getJSONStringMsg(res == true?
                ForumDemoAppUtil.FOLLOW_SUCCESS : ForumDemoAppUtil.FOLLOW_FAIL,
                String.valueOf(followService.getFolloweeCount(user.getId(),
                        ForumDemoAppUtil.ENTITY_USER)) );
    }

    /**
     *
     * Unfollow a user.
     *
     * @param userId
     * @return The new num of followee about the current user.
     *          (entity that is followed by the current user)
     */
    @RequestMapping (path = {"/unfollower"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser (@RequestParam("userId") int userId) {

        User user = userHolder.getUser();
        if (user == null) {
            return JSONUtil.getJSONStringViaCode(ForumDemoAppUtil.
                    UNLOGIN_USER);
        }

        boolean res = followService.unfollow(user.getId(),
                ForumDemoAppUtil.ENTITY_USER, userId);

        if (res == true) {

            eventProducer.triggerEvent(new EventModel(EventType.UNFOLLOW)
                    .setEntityType(ForumDemoAppUtil.ENTITY_USER)
                    .setActorId(user.getId())
                    .setEntityOwnerId(userId)
                    .setEntityId(userId));
        }

        return JSONUtil.getJSONStringMsg(res == true ?
                ForumDemoAppUtil.UNFOLLOW_SUCCESS : ForumDemoAppUtil.UNFOLLOW_FAIL,
                String.valueOf(followService.getFolloweeCount(userId,
                        ForumDemoAppUtil.ENTITY_USER)));
    }

    /**
     *
     * Get the number of followers that is following the current user.
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping (path = {"/user/{userId}/followers"}, method = {RequestMethod.GET})
    public String showFollowers (Model model,
                                 @PathVariable("userId") int userId) {

        List<Integer> followerIds = followService.getFollowees(userId,
                ForumDemoAppUtil.ENTITY_USER, MIN_ITEMS_NUM);
        User user = userHolder.getUser();
        if (user != null) {
            model.addAttribute("followers", getUsersInfor(user.getId(),
                    followerIds));
        }
        else {
            model.addAttribute("followers", getUsersInfor(UNLOGIN_USER_ID,
                    followerIds));
        }

        model.addAttribute("followerCount", followService.getFolloweeCount(userId,
                ForumDemoAppUtil.ENTITY_USER));
        model.addAttribute("curUser", userService.getUserViaId(userId));

        return "followers";
    }

    /**
     *
     * Get the number of followees that is followed by the user with a given userId.
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping (path = {"/user/{userId}/followees"}, method = {RequestMethod.GET})
    public String showFollowees (Model model,
                                 @PathVariable("userId") int userId) {

        List<Integer> followeeIds = followService.getFollowees(userId,
                ForumDemoAppUtil.ENTITY_USER, MIN_ITEMS_NUM);
        User user = userHolder.getUser();

        if (user != null) {
            model.addAttribute("followees", getUsersInfor(user.getId(),
                    followeeIds));
        }
        else {
            model.addAttribute("followees", getUsersInfor(UNLOGIN_USER_ID,
                    followeeIds));
        }

        model.addAttribute("followeeCount", followService.getFolloweeCount(
                userId, ForumDemoAppUtil.ENTITY_USER));
        model.addAttribute("curUser", userService.getUserViaId(userId));

        return "followees";
    }

    /**
     *
     * Get the infor of users that are following the local user. including:
     * 1) user instance
     * 2) the number of comments that a user has posted
     * 3) the number of followers of a user
     * 4) the number of followees of a user (a user follows others)
     * 5) if current user exist, whether the current user follows the user.
     *
     * @param curUserId
     * @param userIds
     * @return
     *
     */
    private List<ViewObject> getUsersInfor (int curUserId, List<Integer> userIds) {

        List<ViewObject> userInfor = new LinkedList<>();

        for (Integer id : userIds) {

            User user = userService.getUserViaId(id);
            if (user == null) {
                continue;
            }

            ViewObject obj = new ViewObject();
            obj.set("user", user);
            // the number of comments that the user has posted
            obj.set("commentCount", commentService.getCommentViaId(id));
            obj.set("followerCount", followService.getFollowerCount(id,
                    ForumDemoAppUtil.ENTITY_USER));
            obj.set("followeeCount", followService.getFolloweeCount(id,
                    ForumDemoAppUtil.ENTITY_USER));
            if (curUserId != UNLOGIN_USER_ID) {
                obj.set("followed", followService.isFollower(curUserId,
                        ForumDemoAppUtil.ENTITY_USER, id));
            }
            else {
                obj.set("followed", IS_NOT_FOLLOW);
            }

            userInfor.add(obj);
        }

        return userInfor;
    }
}
