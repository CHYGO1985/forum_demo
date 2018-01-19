package com.jingjie.forum_demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 *
 * The class is to generate JSON strings.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 19, 2108
 */
public class JSONUtil {

    // get code string
    public static String getJSONStringCode (int code) {

        JSONObject json = new JSONObject();
        json.put("code", code);

        return json.toString();
    }

    // get code, message string
    public static String getJSONStringMsg (int code, String msg) {

        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);

        return json.toString();
    }

    // get code, hashtable string
    public static String getJSONStringMap (int code, Map<String, Object> map) {

        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }

        return json.toString();
    }
}
