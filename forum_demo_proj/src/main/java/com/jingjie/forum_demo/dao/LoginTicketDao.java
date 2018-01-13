package com.jingjie.forum_demo.dao;

import com.jingjie.forum_demo.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 *
 * The DAO for login_ticket.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 13, 2018
 */
@Mapper
public interface LoginTicketDao {

    final String LOGTIC_TABLE  = "login_ticket";
    final String INSERT_FIELDS = " user_id, expired, status, ticket ";
    final String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     *
     * Insert a record into login_ticket table.
     *
     * @param ticket
     * @return
     */
    @Insert({"insert into ", LOGTIC_TABLE, "(", INSERT_FIELDS,
            ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    /**
     *
     * Get a ticket according to a given ID.
     *
     * @param ticket
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", LOGTIC_TABLE, " where ticket=#{ticket}"})
    LoginTicket getTicketViaId(String ticket);

    /**
     *
     * Update the status of a ticket according to the given ticket value.
     *
     * @param ticket
     * @param status
     */
    @Update({"update ", LOGTIC_TABLE, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
