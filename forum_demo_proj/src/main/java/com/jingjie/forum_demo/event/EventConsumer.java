package com.jingjie.forum_demo.event;

import com.alibaba.fastjson.JSON;
import com.jingjie.forum_demo.util.JedisAdapter;
import com.jingjie.forum_demo.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * The class is to fetch event from event queue, and hand it to the corresponding
 * handler.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 31, 2018
 *
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<EventType, List<EventHandler>> eventHandlerMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * Get all the event handler defined in the application and add to a mep.
     * Launch a thread to handle all the event.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {

            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> supportedEventType = entry.getValue().getSupportEventType();

                for (EventType type : supportedEventType) {
                    if (eventHandlerMap.containsKey(type) == false) {
                        eventHandlerMap.put(type, new LinkedList<EventHandler>());
                    }

                    eventHandlerMap.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    String key = RedisKeyUtil.getKeyEventQueue();
                    // pop an event from event queuqe (key, event)
                    List<String> events = jedisAdapter.popFromListTail(key, 0);

                    for (String message : events) {
                        // skip the first key
                        if (message.equals(key)) {
                            continue;
                        }

                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (eventHandlerMap.containsKey(eventModel.getType()) == false) {
                            logger.error("Wrong event type.");
                            continue;
                        }

                        // handle event
                        for (EventHandler handler : eventHandlerMap.get(eventModel.getType())) {
                            handler.handleEvent(eventModel);
                        }
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
