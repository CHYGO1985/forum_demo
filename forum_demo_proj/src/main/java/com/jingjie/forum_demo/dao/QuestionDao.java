package com.jingjie.forum_demo.dao;

import com.jingjie.forum_demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * DAO for question table.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 11, 2018
 */
@Mapper
public interface QuestionDao {

    String QUSTION_TABLE = "question";
    String INSERT_FIELDS = "title, content, user_id, created_date, comment_count";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    // insert question into question table
    @Insert ({"insert into ", QUSTION_TABLE, " (", INSERT_FIELDS,
            ") values (#{title}, #{content}, #{userId}, #{createDate}, #{commentCount})"})
    int addQuestion(Question question);

    // get a question via its id
    @Select ({"select ", SELECT_FIELDS, " from ", QUSTION_TABLE, " where id = #{id}"})
    Question getQuestionViaId(int id);

    // get latest questions

    /**
     *
     * Get a list of question ranked by its id in descending order.
     *
     * @param userId If userId equals 0, means that any posts will work.
     *               If not, only find the user's post.
     * @param offSet Offset count from the biggest id to smallest.
     * @param limit The number of questions that should be returned.
     * @return A list of questions.
     */
    List<Question> getLatestQuestions(@Param("userId") int userId,
                                      @Param("offset") int offSet,
                                      @Param("limit") int limit);
}
