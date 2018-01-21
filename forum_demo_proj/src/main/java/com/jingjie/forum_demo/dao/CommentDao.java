package com.jingjie.forum_demo.dao;

import com.jingjie.forum_demo.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 * The DAO for comment table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 20, 2018
 */
@Mapper
public interface CommentDao {

    String COMMENT_TABLE = "comment";
    String INSERT_FIELDS = "user_id, created_date, entity_id, entity_type, content, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select ({"select " + SELECT_FIELDS + " from " + COMMENT_TABLE + " where id = #{id}"})
    int getCommentViaId(int id);

    @Select ({"select count(id) from " + COMMENT_TABLE + " where user_id = #{userId}"})
    int getUserCommentCount(int userId);

    @Insert ({"insert into " + COMMENT_TABLE + " (" + INSERT_FIELDS + ") " +
            "values (#{userId}, #{createDate}, #{entityId}, #{entityType}, #{content}, #{status})"})
    int addComment(Comment comment);

    @Select ({"select " + SELECT_FIELDS + " from " + COMMENT_TABLE + " where entity_id = #{entityId} and entity_type = #{entityType} " +
            "order by created_date desc"})
    List<Comment> getCommentViaEntity(@Param("entityId") int entityId,
                                      @Param("entityType") int entityType);

    @Update ({"update " + COMMENT_TABLE + " set status = #{status} where id = #{id}"})
    int updateCommentStatus(@Param("id") int id,
                            @Param("status") int status);

    @Select ({"select " + SELECT_FIELDS + " from " + COMMENT_TABLE + " where entity_id = #{entityId} and entity_type = #{entityType}"})
    int getCommentCountViaEntity(@Param("entityId") int entityId,
                                 @Param("entityType") int entityType);
}
