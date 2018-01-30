package com.jingjie.forum_demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    public long removeFromSet (String key, String value) {

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
    public long addMemberToSet (String key, String value) {

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

    // zrem method
    public long removeFromSortedSet (String key, String value) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrem(key, value);
        }
        catch (Exception ex) {
            logger.error("Fail to remove from sorted set: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // multi method
    public Transaction startOfTransac (Jedis jedis) {

        try {
            return jedis.multi();
        }
        catch (Exception ex) {
            logger.error("Fail to start a transaction: " + ex.getMessage());
        }
        finally {

        }

        return null;
    }

    // exec method
    public List<Object> execTransac (Transaction trans, Jedis jedis) {

        try {
            return trans.exec();
        }
        catch (Exception ex) {
            logger.error("Fail to execute transactions: " + ex.getMessage());
            trans.discard();
        }
        finally {
            if (trans != null) {
                try {
                    trans.close();
                }
                catch (IOException ioe) {

                }
            }

            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    // zrange method
    public Set<String> getSortedSetElems (String key, int start, int end) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrange(key, start, end);
        }
        catch (Exception ex) {
            logger.error("Fail to get elems from sorted set: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    // zrevrange method
    public Set<String> getSortedSetElemsRev (String key, int start, int end) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        }
        catch (Exception ex) {
            logger.error("Fail to get elemes from sorted set in rev order: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    // zcard method
    public long getCardOfSortedSet (String key) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        }
        catch (Exception ex) {
            logger.error("Fail to get the cardinality of sorted set:" +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // zscore method
    public Double getSoreInSortedSet (String key, String member) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        }
        catch (Exception ex) {
            logger.error("Fail to get a score from sorted set: " +
                    ex.getMessage());
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }
}
