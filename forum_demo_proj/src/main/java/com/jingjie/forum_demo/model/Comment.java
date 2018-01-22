package com.jingjie.forum_demo.model;

import java.util.Date;

/**
 *
 * The model for comment table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 20, 2018
 *
 */
public class Comment {

    private int id;
    private String content;
    private int userId;
    private Date createDate;
    private int entityId;
    private int entityType;
    private int status;

    public Comment() {

        //createDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
