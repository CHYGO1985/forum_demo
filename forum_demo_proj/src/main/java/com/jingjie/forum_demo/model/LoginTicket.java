package com.jingjie.forum_demo.model;

import java.util.Date;

/**
 *
 * The model for table login_ticket.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 13, 2018
 */
public class LoginTicket {

    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;

    public LoginTicket () {

        this.expired = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
