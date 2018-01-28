package com.jingjie.forum_demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 *
 * The adpater the methods in Jedis.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 26, 2018
 *
 */
@Service
public class JedisAdapter implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool pool;


    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    // srem method
    public long removeMember (String key, String value) {

        Jedis jedis = null;

        try {

            jedis = pool.getResource();
            return jedis.srem(key, value);
        }
        catch (Exception ex) {
            logger.error("Fail to remove a member from Redis: " + ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // sadd method
    public long addMember (String key, String value) {

        Jedis jedis = null;
        try {

            jedis = pool.getResource();
            return jedis.sadd(key, value);
        }
        catch (Exception ex) {
            logger.error("Fail to add a memeber to Redis: " + ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // scard method
    public long getNumOfMembersViaKey (String key) {

        Jedis jedis = null;
        try {

            jedis = pool.getResource();
            return jedis.scard(key);
        }
        catch (Exception ex) {
            logger.error("Fail to get the number of members of a key: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // sismember method
    public boolean isMember (String key, String value) {

        Jedis jedis = null;
        try {

            jedis = pool.getResource();
            return jedis.sismember(key, value);
        }
        catch (Exception ex) {
            logger.error("Fail to check membership: " + ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }

    // brpop method (blocking list pop up method)
    public List<String> popFromListTail (String key, int timeout) {

        Jedis jedis = null;
        try {

            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        }
        catch (Exception ex) {
            logger.error("Fail to pop an element from the tail of a list: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    // lpush method
    public long addToListHead (String key, String value) {

        Jedis jedis = null;
        try {

            jedis = pool.getResource();
            return jedis.lpush(key, value);
        }
        catch (Exception ex) {
            logger.error("Fail to add element to the head of a list: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // lrange
    public List<String> getListElemsWithRange (String key, int start, int end) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        }
        catch (Exception ex) {
            logger.error("Fail to get elems from list:" + ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    // zadd method
    public long addToSortSet (String key, double score, String value) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zadd(key, score, value);
        }
        catch (Exception ex) {
            logger.error("Fail to add member to sorted set: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }


}
