package com.jingjie.forum_demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
}
