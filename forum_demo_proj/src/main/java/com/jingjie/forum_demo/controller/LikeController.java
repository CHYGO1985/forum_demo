package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.event.EventModel;
import com.jingjie.forum_demo.event.EventProducer;
import com.jingjie.forum_demo.event.EventType;
import com.jingjie.forum_demo.model.Comment;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHolder;
import com.jingjie.forum_demo.service.CommentService;
import com.jingjie.forum_demo.service.LikeService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import com.jingjie.forum_demo.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * The controller for likeing or disliking a comment.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 30, 2018
 *
 */
@Controller
public class LikeController {

    @Autowired
    UserHolder userHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    /**
     *
     * Like a comment.
     *
     * @param commentId
     * @return The number of likes regarding the given comment.
     */
    @RequestMapping (path = "/like", method = {RequestMethod.POST})
    @ResponseBody
    public String like (@RequestParam("commentId") int commentId) {

        User user = userHolder.getUser();
        if (user == null) {
            return JSONUtil.getJSONStringViaCode(ForumDemoAppUtil.
                    UNLOGIN_USER);
        }

        // get the liked comment
        Comment comment = commentService.getCommentViaId(commentId);
        // trigger a like event for sending a message
        eventProducer.triggerEvent(new EventModel(EventType.LIkE).
                setActorId(user.getId()).setEntityOwnerId(comment.getUserId()).
                setEntityType(ForumDemoAppUtil.ENTITY_COMMENT).
                setEntityId(commentId).setExtension("questionId",
                String.valueOf(comment.getEntityId())));

        // get like count
        long likeCount = likeService.like(user.getId(),
                ForumDemoAppUtil.ENTITY_COMMENT,
                commentId);

        return JSONUtil.getJSONStringMsg(
                ForumDemoAppUtil.UPDATE_LIKE_DISLIKE_SUC,
                    String.valueOf(likeCount));
    }

    /**
     *
     * Dislike a comment.
     *
     * @param commentId
     * @return The number of dislikes regarding the given comment.
     */
    @RequestMapping (path = "/dislike", method = {RequestMethod.POST})
    @ResponseBody
    public String dislike (@RequestParam("commentId") int commentId) {

        User user = userHolder.getUser();
        if (user == null) {
            return JSONUtil.getJSONStringViaCode(ForumDemoAppUtil.
                    UNLOGIN_USER);
        }

        long dislikeCount = likeService.dislike(user.getId(),
                ForumDemoAppUtil.ENTITY_COMMENT,
                commentId);

        return JSONUtil.getJSONStringMsg(
                ForumDemoAppUtil.UPDATE_LIKE_DISLIKE_SUC,
                String.valueOf(dislikeCount));
    }

}
