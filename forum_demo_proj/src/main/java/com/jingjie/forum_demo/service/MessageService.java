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


    public List<Message> getConvosViaId (String convoId, int offset, int limit) {

        return messageDao.getConvosViaId(convoId, offset, limit);
    }

    // get convos and the num of convos send to and from a user with limit, offset and in desc by createDate
    public List<Message> getConversationList (int userId, int offset, int limit) {

        return messageDao.getConversationList(userId, offset, limit);
    }

    // get the number of unread message send to a user with certain convo id
    public int getUnreadMsgCount (int userId, String convoId) {

        return messageDao.getUnreadMsgCount(userId, convoId);
    }
}
