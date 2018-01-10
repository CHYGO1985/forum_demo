package com.jingjie.forum_demo.dao;

import com.jingjie.forum_demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 *
 * The DAO for user table.
 *
 * @author jingjiejiang
 * @history
 * 1. created on Jan, 2018
 */
@Mapper
@Repository
public interface UserDao {

    String USER_TABLE = "user";
    String INSERT_FIELDS = " name, password, salt, head_url";
    String SELECT_FIELDS = " id, name, password, salt, head_url";

    // insert a record into user table
    @Insert({"Insert into ", USER_TABLE, " (" + INSERT_FIELDS + ") values " +
            "(#{name}, #{password}, #{salt}, #{headUrl})"})
    void addUser(User user);

    // select a record according to a given user id
    @Select({"select ", SELECT_FIELDS, " from ", USER_TABLE, " where id = #{id}"})
    User getUserViaId(int id);

    // select a record according to a given user name
    @Select({"select ", SELECT_FIELDS , " from ", USER_TABLE, " where name = #{name}"})
    User getUserViaName(String name);

    // update a user's password accroding to a given user id
    @Update({"update ", USER_TABLE, " set password = #{password} where id = #{id}"})
    void updatePassword(int id);

    // delete a user's record accroding to a given user id
    @Delete({"delete from ", USER_TABLE, " where id = #{id]"})
    void deleteUserViaId(int id);

}
