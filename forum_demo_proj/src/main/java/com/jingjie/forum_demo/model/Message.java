package com.jingjie.forum_demo.model;

import java.util.Date;

/**
 *
 * The model for message table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 22, 2018
 *
 */
public class Message {

    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createDate;
    private int hasRead;
    private String convoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConvoId() {

        // conversation id is in int_int format, small number comes first
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }
        else {
            return String.format("%d_%d", toId, fromId);
        }
    }

    public void setConvoId(String convoId) {
        this.convoId = convoId;
    }
}
