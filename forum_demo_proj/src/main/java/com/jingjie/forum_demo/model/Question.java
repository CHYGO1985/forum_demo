package com.jingjie.forum_demo.model;

import java.util.Date;

/**
 *
 * Model for question table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 11, 2018
 */
public class Question {

    private int id;
    private String title;
    private String content;
    private int userId;
    private Date createDate;
    private int commentCount;

    // NOTE: The createDate must be initialised, otherwise it will not receive date ]
    // from datebase when reading
    public Question() {
        createDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
