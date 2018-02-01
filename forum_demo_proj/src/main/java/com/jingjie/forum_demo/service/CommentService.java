package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.dao.CommentDao;
import com.jingjie.forum_demo.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 *
 * Comment Services.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 21, 2018
 *
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    SensitiveWordService sensitiveWordService;


    public int addComment (Comment comment) {

        // skip HTML content and filter sensitive words
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveWordService.filterWords(comment.getContent()));

        return commentDao.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public int getCommentCountViaEntityType (int entityId, int entityType) {

        return commentDao.getCommentCountViaEntity(entityId, entityType);
    }

    public List<Comment> getCommentViaEntity (int entityId, int entityType) {

        return commentDao.getCommentViaEntity(entityId, entityType);
    }

    public Comment getCommentViaId (int id) {

        return commentDao.getCommentViaId(id);
    }

    public int getUserCommentCount (int userId) {

        return commentDao.getUserCommentCount(userId);
    }

    public int deleteComment (int id, int status) {

        return commentDao.updateCommentStatus(id, status);
    }

}
