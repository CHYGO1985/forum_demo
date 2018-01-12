package com.jingjie.forum_demo.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * The class is for holding both instances of Question and User.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 12, 2018
 */
public class ViewObject {

    private Map<String, Object> userQueMap = new HashMap<>();

    public void set (String key, Object value) {

        userQueMap.put(key, value);
    }

    public Object get (String key) {
        return userQueMap.get(key);
    }
}
