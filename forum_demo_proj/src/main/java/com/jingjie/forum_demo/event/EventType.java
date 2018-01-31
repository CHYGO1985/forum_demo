package com.jingjie.forum_demo.event;

import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;

/**
 *
 * The enumtype for events.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 31, 2018
 *
 */
public enum EventType {

    LIkE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5),
    ADD_QUESTION(6);

    private int value;

    EventType (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
