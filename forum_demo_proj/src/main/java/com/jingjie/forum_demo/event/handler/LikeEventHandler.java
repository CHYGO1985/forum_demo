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
 * The event hanlder for like event.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 31, 2018
 */
@Component
public class LikeEventHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    /**
     *
     * When a user's comment is liked by another user, send a message to the
     * owner of the comment.
     *
     * @param model
     */
    @Override
    public void handleEvent(EventModel model) {

        Message message = new Message();
        message.setFromId(ForumDemoAppUtil.SYS_USER);
        message.setToId(model.getEntityOwnerId());
        message.setCreateDate(new Date());
        User user = userService.getUserViaId(model.getActorId());
        message.setContent("The user " + user.getName() + " liked your comment, " +
                "http://127.0.0.1:8080/question/" + model.getValueInExt("questionId"));

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIkE);
    }
}
