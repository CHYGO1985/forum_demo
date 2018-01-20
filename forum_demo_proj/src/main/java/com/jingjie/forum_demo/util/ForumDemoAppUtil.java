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

    /* templates related constants */
    public static final String INDEX_TEMPLATE = "index";
    public static final String LOGIN_TEMPLATE = "login";


    /* Login related constants */
    public static final String USER_TOKEN = "ticket";
    public static final String LOGIN_ERROR_MSG_KEY = "msg";

    /* Login ticket status */
    public static final int INVALID_TICKET = 1;
    public static final int VALID_TICKET = 0;

    /* Server JSON code */
    public static final int QUESTION_SUBMIT_SUCCESS = 0;
    public static final int QUESTOON_SUBMIT_FAIL = 1;

    /* Database related code */
    public static final int QUESTION_ADD_FAIL = 0;

    /* Sensitive word replacement*/
    public static final String SENS_WORD_REPLACE = "**";

}
