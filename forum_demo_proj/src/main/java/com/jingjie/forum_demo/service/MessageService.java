package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.dao.MessageDao;
import com.jingjie.forum_demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * The service for operations related to message table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 22, 2018
 */
@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitiveWordService sensitiveWordService;

    public int addMessage(Message message) {

        message.setContent( sensitiveWordService.filterWords(message.getContent()) );

        return messageDao.addMessage(message) > 0? message.getId() : 0;
    }


    /**
     *
     * Get a conversation via an conversation id.
     *
     * @param convoId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConvosViaId (String convoId, int offset, int limit) {

        return messageDao.getConvosViaId(convoId, offset, limit);
    }

    /**
     *
     * Get a list of messages regarding a conversation that is related to a
     * given user, including messsages are sent from and sent to the user.
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationList (int userId, int offset, int limit) {

        return messageDao.getConversationList(userId, offset, limit);
    }

    /**
     *
     * Get the number of unread messages sent to a user with respect to a
     * given conversation id.
     *
     * @param userId
     * @param convoId
     * @return
     */
    public int getUnreadMsgCount (int userId, String convoId) {

        return messageDao.getUnreadMsgCount(userId, convoId);
    }
}
