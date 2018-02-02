package com.jingjie.forum_demo.event.handler;

import com.jingjie.forum_demo.event.EventHandler;
import com.jingjie.forum_demo.event.EventModel;
import com.jingjie.forum_demo.event.EventType;
import com.jingjie.forum_demo.model.Message;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.service.MessageService;
import com.jingjie.forum_demo.service.UserService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * The handler for the follow event.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Feb 2, 2018
 *
 */
@Component
public class FollowHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void handleEvent(EventModel model) {

        Message message = new Message();
        message.setFromId(ForumDemoAppUtil.SYS_USER);
        message.setToId(model.getEntityOwnerId());
        message.setCreateDate(new Date());
        User user = userService.getUserViaId(model.getActorId());

        if (model.getEntityType() == ForumDemoAppUtil.ENTITY_QUESTION) {

            message.setContent("The user " + user.getName() + " followed " +
                    "your question, http://127.0.0.1:8080/qudestion" +
                    model.getEntityId());
        }
        else if (model.getEntityType() == ForumDemoAppUtil.ENTITY_USER) {

            message.setContent("The user " + user.getName() + " followed " +
                    "your, http://127.0.0.1:8080/user" +
                    model.getActorId());
        }

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
