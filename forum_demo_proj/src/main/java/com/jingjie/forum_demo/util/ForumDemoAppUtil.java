package com.jingjie.forum_demo.util;

/**
 *
 * The class is to hold global constants.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 14, 2018
 */
public class ForumDemoAppUtil {

    public static final int ANONYMOUS_USERID = 1;
    public static final int SYS_USER = 2;

    /* templates related constants */
    public static final String INDEX_TEMPLATE = "index";
    public static final String LOGIN_TEMPLATE = "login";
    public static final String USER_PROFILE_TEMPLATE = "profile";
    public static final String FOLLOWEE_TEMPLATE = "followees";
    public static final String FOLLOWER_TEMPLATE = "followers";


    /* Login related constants */
    public static final String USER_TOKEN = "ticket";
    public static final String LOGIN_ERROR_MSG_KEY = "msg";

    /* Login ticket status */
    public static final int INVALID_TICKET = 1;
    public static final int VALID_TICKET = 0;

    /* Server JSON code */
    public static final int QUESTION_SUBMIT_SUCCESS = 0;
    public static final int QUESTOON_SUBMIT_FAIL = 1;

    /* Message JSON code*/
    public static final int MSG_ADD_SUCCESS = 0;
    public static final int MSG_ADD_FAIL = 1;
    public static final int MSG_READ = 1;
    public static final int MSG_UN_READ = 0;

    /* Database related code */
    public static final int QUESTION_ADD_FAIL = 0;

    /* Sensitive word replacement*/
    public static final String SENS_WORD_REPLACE = "**";

    /* Entity Types */
    public static final int ENTITY_QUESTION = 1;
    public static final int ENTITY_COMMENT = 2;
    public static final int ENTITY_USER = 3;

    /* Comment status code */
    public static final int COMMENT_VALID = 0;
    public static final int COMMENT_DELETED = 1;

    /* Un login user */
    public static final int UNLOGIN_USER = 999;

    /* Like/Dislike status code */
    public static final int LIKED_STATUS = 1;
    public static final int DISLIKED_STATUS = -1;
    public static final int NEUTRAl_STATUS = 0;

    /* like/dislike related JSON code*/
    public static final int UPDATE_LIKE_DISLIKE_SUC = 0;

    /* follow/unfollow related JSON code*/
    public static final int FOLLOW_SUCCESS = 0;
    public static final int FOLLOW_FAIL = 1;
    public static final int UNFOLLOW_SUCCESS = 0;
    public static final int UNFOLLOW_FAIL = 1;
}
