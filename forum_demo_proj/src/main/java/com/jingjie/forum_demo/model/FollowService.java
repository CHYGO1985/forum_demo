package com.jingjie.forum_demo.model;

import com.jingjie.forum_demo.util.JedisAdapter;
import com.jingjie.forum_demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    /**
     *
     * Get a list of followers ids (userIds) from tail to head.
     * NB: start from 0.
     *
     * @param entityType
     * @param entityId
     * @param count
     * @return
     */
    public List<Integer> getFollowersId (int entityType, int entityId, int count) {

        String followerKey = RedisKeyUtil.getKeyFollower(entityType, entityId);

        return getIdsFromSet(jedisAdapter.getSortedSetElemsRev(followerKey,
                0, count));
    }

    /**
     *
     * Get a list of followers ids (userIds) from tail to head.
     * NB: start from offset.
     *
     * @param entityType
     * @param entityId
     * @param offset
     * @param count
     * @return
     */
    public List<Integer> getFollowersIdWithOffset (int entityType, int entityId,
                                                   int offset, int count) {
        String followerKey = RedisKeyUtil.getKeyFollower(entityType, entityId);

        return getIdsFromSet(jedisAdapter.getSortedSetElemsRev(followerKey,
                offset, offset + count));
    }

    /**
     *
     * Get a list of followee ids (entityIds) from tail to head.
     * NB: start from 0.
     *
     * @param userId
     * @param entityType
     * @param count
     * @return
     */
    public List<Integer> getFollowees (int userId, int entityType, int count) {

        String followeeKey = RedisKeyUtil.getKeyFollowee(userId, entityType);

        return getIdsFromSet(jedisAdapter.getSortedSetElemsRev(followeeKey,
                0, count));
    }

    /**
     *
     * Get a list of followee ids (entityIds) from tail to head.
     * NB: start from offset.
     *
     * @param userId
     * @param entityType
     * @param offset
     * @param count
     * @return
     */
    public List<Integer> getFolloweesIdWithOffset (int userId, int entityType,
                                                   int offset, int count) {
        String followeeKey = RedisKeyUtil.getKeyFollowee(userId, entityType);

        return getIdsFromSet(jedisAdapter.getSortedSetElemsRev(followeeKey,
                offset, offset + count));
    }

    private List<Integer> getIdsFromSet (Set<String> idSet) {

        List<Integer> ids = new ArrayList<>();
        for (String str : idSet) {
            ids.add(Integer.parseInt(str));
        }

        return ids;
    }

    /**
     *
     * Get the number of followers for a given entity.
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public long getFollowerCount (int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getKeyFollower(entityType, entityId);

        return jedisAdapter.getCardOfSortedSet(followerKey);
    }

    /**
     *
     * Get the number of followee for a given user with a given entityType.
     *
     * @param userId
     * @param entityType
     * @return
     */
    public long getFolloweeCount (int userId, int entityType) {

        String followeeKey = RedisKeyUtil.getKeyFollowee(userId, entityType);

        return jedisAdapter.getCardOfSortedSet(followeeKey);
    }

    /**
     *
     * Check whether a user follows a given entity.
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean isFollower (int userId, int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getKeyFollower(entityType, entityId);

        return jedisAdapter.getSoreInSortedSet(followerKey, String.valueOf(userId)) != null;
    }
}
