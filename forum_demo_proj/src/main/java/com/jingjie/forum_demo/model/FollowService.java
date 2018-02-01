package com.jingjie.forum_demo.model;

import com.jingjie.forum_demo.util.JedisAdapter;
import com.jingjie.forum_demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;
import java.util.List;

/**
 *
 * Follow and unfollow related services.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Feb 1, 2018
 */
@Service
public class FollowService {

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     *
     * Follow an entity.
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean follow (int userId, int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getKeyFollower(entityType, entityId);
        String followeeKey = RedisKeyUtil.getKeyFollowee(userId, entityId);

        Date date = new Date();
        // user transaction to finish a follow action
        // 1) add a follower to zset
        // 2) add a followee to zset
        Jedis jedis = jedisAdapter.getJedis();
        Transaction trans = jedisAdapter.startOfTransac(jedis);
        // score is the date that follow happens
        trans.zadd(followerKey, date.getTime(), String.valueOf(userId));
        trans.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> res = jedisAdapter.execTransac(trans, jedis);

        return res.size() == 2 && (long) res.get(0) > 0 && (long) res.get(1) > 0;
    }

    /**
     *
     * Unfollow an entity.
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean unfollow (int userId, int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getKeyFollower(entityType, entityId);
        String followeeKey = RedisKeyUtil.getKeyFollowee(userId, entityId);

        Date date = new Date();
        // user transaction to finish a follow action
        // 1) add a follower to zset
        // 2) add a followee to zset
        Jedis jedis = jedisAdapter.getJedis();
        Transaction trans = jedisAdapter.startOfTransac(jedis);
        // score is the date that follow happens
        trans.zrem(followerKey, String.valueOf(userId));
        trans.zrem(followeeKey, String.valueOf(entityId));
        List<Object> res = jedisAdapter.execTransac(trans, jedis);

        return res.size() == 2 && (long) res.get(0) > 0 && (long) res.get(1) > 0;
    }
}
