package com.jingjie.forum_demo.service;

import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import com.jingjie.forum_demo.util.JedisAdapter;
import com.jingjie.forum_demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * The class for like/dislike related operations.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 30, 2018
 *
 */
@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    // get the count of like for a given entity
    public long getLikeCount (int entityType, int entityId) {

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);

        return jedisAdapter.getNumOfMembersViaKey(likeKey);
    }

    // get the like/dislike status of a given user of a given entity
    public int getLikeStatus (int userId, int entityType, int entityId) {

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);

        if(jedisAdapter.isMember(likeKey, String.valueOf(userId)) == true) {
            return ForumDemoAppUtil.LIKED_STATUS;
        }

        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);

        return jedisAdapter.isMember(dislikeKey, String.valueOf(userId)) == true ?
                ForumDemoAppUtil.DISLIKED_STATUS : ForumDemoAppUtil.NEUTRAl_STATUS;
    }

    // set like status for a given user of a given entity
    public long like (int userId, int entityType, int entityId) {

        // like + 1
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.addMemberToSet(likeKey, String.valueOf(userId));

        // dislike - 1
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.removeFromSet(dislikeKey, String.valueOf(userId));

        // return the current num of likes
        return jedisAdapter.getNumOfMembersViaKey(likeKey);
    }


    // set dislike status for a given user of a given entity
    public long dislike (int userId, int entityType, int entityId) {

        // dislike + 1
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.addMemberToSet(dislikeKey, String.valueOf(userId));

        // like - 1
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.removeFromSet(likeKey, String.valueOf(userId));

        // return the current num of dislikes
        return jedisAdapter.getNumOfMembersViaKey(dislikeKey);
    }
}
