package com.jingjie.forum_demo.event;

import com.alibaba.fastjson.JSONObject;
import com.jingjie.forum_demo.util.JedisAdapter;
import com.jingjie.forum_demo.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * The class is for put a event <String:key, String: EventModel> into
 * event queue.
 *
 * @author jingjiejiang
 * @hsitory
 * 1. Created on Jan 31, 2018
 *
 */
@Service
public class EventProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean triggerEvent (EventModel eventModel) {

        try {

            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getKeyEventQueue();
            jedisAdapter.addToListHead(key, json);
            return true;
        }
        catch (Exception ex) {
            logger.error("Fail to trigger an event: " + ex.getMessage());
            return false;
        }
    }
}
