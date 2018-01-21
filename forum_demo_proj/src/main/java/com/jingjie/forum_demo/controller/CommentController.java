package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.Comment;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHolder;
import com.jingjie.forum_demo.service.CommentService;
import com.jingjie.forum_demo.service.QuestionService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 *
 * The controller for oprations related to comments.
 *
 * @author jingjiejiang
 * @history
 * 1. Creatd on Jan 21, 2018
 *
 */
@Controller
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommentService commentService;

    @Autowired
    UserHolder userHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping (path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment (@RequestParam("questionId") int questionId,
                              @RequestParam("content") String content) {

        try {

            // add a new comment
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreateDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(ForumDemoAppUtil.ENTITY_QUESTION);

            User user = userHolder.getUser();
            if (user == null) {
                comment.setUserId(ForumDemoAppUtil.ANONYMOUS_USERID);
            }
            else {
                comment.setUserId(user.getId());
            }

            comment.setStatus(ForumDemoAppUtil.COMMENT_VALID);
            commentService.addComment(comment);

            // update comment count of the question
            int count  = commentService.getCommentCountViaEntityType(questionId,
                    comment.getEntityType());

            questionService.updateCommentCount(questionId, count);
        }
        catch (Exception ex) {

            logger.error("Fail to add a comment: " + ex);
        }

        return "redirect:/question/" + questionId;
    }
}
