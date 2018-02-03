package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.Message;
import com.jingjie.forum_demo.model.User;
import com.jingjie.forum_demo.model.UserHolder;
import com.jingjie.forum_demo.model.ViewObject;
import com.jingjie.forum_demo.service.MessageService;
import com.jingjie.forum_demo.service.UserService;
import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import com.jingjie.forum_demo.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *  The controller for messages related operations.
 *
 *  @author jingjiejiang
 *  @history
 *  1. Created on Jan 23, 2017
 *
 */
@Controller
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int DEF_OFFSET = 10;
    private static final int NUM_LIMIT = 10;

    @Autowired
    UserHolder userHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    /**
     *
     * Get a limited num of messages that was send or received by the given
     * user.
     *
     * @param model
     * @return
     */
    @RequestMapping (path = {"msg/list"}, method = {RequestMethod.GET})
    public String getConvoList (Model model) {

        // check whether the user has login or not
        User user = userHolder.getUser();
        if (user == null) {
            return "redirect:/regislogin";
        }

        // get convo list via user id
        int userId = user.getId();
        List<Message> messages = messageService.getConversationList(userId,
                DEF_OFFSET, NUM_LIMIT);
        List<ViewObject> convoInfor = new LinkedList<>();

        // add message, user and unread msg infor to model
        for (Message message : messages) {

            ViewObject viewObj = new ViewObject();
            viewObj.set("message", message);
            // only keep the message send to/from other users, not the login one
            int targetUserId = message.getFromId() == userId ?
                    message.getToId() : message.getFromId();
            viewObj.set("user", userService.getUserViaId(targetUserId));
            viewObj.set("unread", messageService.getUnreadMsgCount(userId,
                    message.getConvoId()));

            convoInfor.add(viewObj);
        }
        model.addAttribute("convoInfor", convoInfor);

        return ForumDemoAppUtil.LETTER_TEMPLATE;
    }

    /**
     *
     * Get limited number of messages regarding a given conversation.
     *
     * @param model
     * @param convoId
     * @return
     */
    @RequestMapping (path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConvoDetails(Model model, @RequestParam("convoId") String convoId) {

        try {

            List<Message> msgList = messageService.getConvosViaId(convoId,
                    DEF_OFFSET, NUM_LIMIT);
            List<ViewObject> convos = new LinkedList<>();

            for (Message message : msgList) {

                ViewObject obj = new ViewObject();
                obj.set("message", message);
                // get the sender user infor
                obj.set("user", userService.getUserViaId(message.getFromId()));
                convos.add(obj);
            }

            model.addAttribute("convos", convos);
        }
        catch (Exception ex){
            logger.error("Fail to get the message details: " + ex.getMessage());
        }

        return ForumDemoAppUtil.LETTER_DETAIL_TEMPLATE;
    }

    /**
     *
     * Add a message.
     *
     * @param toName
     * @param content
     * @return
     */
    @RequestMapping (path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam ("toName") String toName,
                             @RequestParam ("content") String content) {

        try {

            // check whether the user has login
            User fromUser = userHolder.getUser();
            if (fromUser == null)
                return JSONUtil.getJSONStringMsg(ForumDemoAppUtil.UNLOGIN_USER,
                        "Unlogin");

            // check whether the user that the msg should sent to exist in DB
            User toUser = userService.getUserViaName(toName);
            if (toUser == null)
                return JSONUtil.getJSONStringMsg(ForumDemoAppUtil.MSG_ADD_FAIL,
                        "The user does not exist.");

            Message message = new Message();
            message.setContent(content);
            message.setCreateDate(new Date());
            message.setFromId(fromUser.getId());
            message.setToId(toUser.getId());
            message.setConvoId(message.getConvoId());
            message.setHasRead(ForumDemoAppUtil.MSG_UN_READ);
            messageService.addMessage(message);

            return JSONUtil.getJSONStringViaCode(ForumDemoAppUtil.MSG_ADD_SUCCESS);
        }
        catch (Exception ex) {
            logger.error("Fail to send a message: " + ex);
            return JSONUtil.getJSONStringMsg(ForumDemoAppUtil.MSG_ADD_FAIL,
                    "Fail to send a message.");
        }
    }


}
