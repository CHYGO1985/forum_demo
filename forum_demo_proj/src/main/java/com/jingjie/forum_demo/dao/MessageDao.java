package com.jingjie.forum_demo.dao;

import com.jingjie.forum_demo.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * The DAO for operations related to message table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 22, 2018
 *
 */
@Mapper
public interface MessageDao {

    String MESSAGE_TABLE = "message";
    String INSERT_FIELDS = "from_id, to_id, content, created_date, has_read, convo_id";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    // add message
    @Insert ({"insert into " + MESSAGE_TABLE + " (" + INSERT_FIELDS + ") values " +
            "(#{fromId}, #{toId}, #{content}, #{createDate}, #{hasRead}, #{convoId})"})
    int addMessage(Message message);

    // get convos via convo_id with limit, offer and in desc order by createDate
    @Select ({"select * from message where convo_id = #{convoId} order by created_date desc " +
            "limit #{limit} offset #{offset}"})
    List<Message> getConvosViaId (@Param("convoId") String convoId,
                        @Param("offset") int offset,
                        @Param("limit") int limit);

    // get convos and the num of convos send to and from a user with limit, offset and in desc by createDate
    @Select({"select ", INSERT_FIELDS, " , count(id) as id from ( select * from ", MESSAGE_TABLE,
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by convo_id order by " +
                    "created_date desc limit #{limit} offset #{offset}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    // get the number of unread message send to a user with certain convo id
    @Select ({"select count(id) from " + MESSAGE_TABLE + " where has_read = 0 and to_id = #{userId}" +
            " and convo_id = #{convoId}"})
    int getUnreadMsgCount (@Param("userId") int userId,
                           @Param("convoId") String convoId);
}
