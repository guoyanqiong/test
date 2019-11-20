package com.example.demo.utils.framework.redis;

import com.example.demo.utils.common.utils.FunctionUtil;
import com.example.demo.utils.common.utils.SerializeUtil;
import com.example.demo.utils.framework.core.FrameWorkConfig;
import com.example.demo.utils.framework.log.Log;
import com.example.demo.utils.framework.serialization.BytesUtil;
import com.example.demo.utils.framework.serialization.JsonUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 */

public class Redis {
    private static JedisPool readPool = null;
    private static JedisPool writePool = null;

    /**
     * 一分钟
     */
    public static final int ONE_MINUTE = 60;
    /**
     * 半小时
     */
    public static final int HALF_HOUR = 1800;
    /**
     * 一小时
     */
    public static final int ONE_HOUR = 3600;
    /**
     * 一天
     */
    public static final int ONE_DAY = 3600 * 24;
    /**
     * 一周
     */
    public static final int ONE_WEEK = 3600 * 24 * 7;
    /**
     * 一个月
     */
    public static final int ONE_MONTH = 3600 * 24 * 30;

    /**
     * 获取连接池.
     *
     * @return 连接池实例
     */
    private static JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(FrameWorkConfig.getRedisConfig().getMaxactive());
        config.setMaxIdle(FrameWorkConfig.getRedisConfig().getMaxIde());
        config.setMaxWait(FrameWorkConfig.getRedisConfig().getMaxwait());
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        return config;
    }

    private synchronized static JedisPool getReadPool() {
        if (readPool != null)
            return readPool;
        try {
            RedisElement readRedis = null;
            for (RedisElement item : FrameWorkConfig.getRedisConfig().getRedislist()) {
                if (item.getReadonly() == true) {
                    readRedis = item;
                    break;
                }
            }
            if (readRedis != null) {
                if (FunctionUtil.IsNullOrEmpty(FrameWorkConfig.getRedisConfig().getPwd())) {
//                    System.out.println("redis.config.pwd.r1:" + FrameWorkConfig.getRedisConfig().getPwd());
                    readPool = new JedisPool(getJedisPoolConfig(), readRedis.getPath(), readRedis.getPort(),
                            FrameWorkConfig.getRedisConfig().getTimeout());
                } else {
//                    System.out.println("redis.config.pwd.r2:" + FrameWorkConfig.getRedisConfig().getPwd());
                    readPool = new JedisPool(getJedisPoolConfig(), readRedis.getPath(), readRedis.getPort(),
                            FrameWorkConfig.getRedisConfig().getTimeout(), FrameWorkConfig.getRedisConfig().getPwd());
                }
            }
        } catch (Exception e) {
            Log.logError("Create Jedis Pool Error!", e);
        }
        return readPool;
    }

    private synchronized static JedisPool getWritePool() {
        if (writePool != null)
            return writePool;
        try {
            RedisElement writeRedis = null;
            for (RedisElement item : FrameWorkConfig.getRedisConfig().getRedislist()) {
                if (item.getReadonly() == false) {
                    writeRedis = item;
                    break;
                }
            }
            if (writeRedis != null) {
                if (FunctionUtil.IsNullOrEmpty(FrameWorkConfig.getRedisConfig().getPwd())) {
//                    System.out.println("redis.config.pwd.w1:" + FrameWorkConfig.getRedisConfig().getPwd());
                    writePool = new JedisPool(getJedisPoolConfig(), writeRedis.getPath(), writeRedis.getPort(),
                            FrameWorkConfig.getRedisConfig().getTimeout());
                } else {
//                    System.out.println("redis.config.pwd.w2:" + FrameWorkConfig.getRedisConfig().getPwd());
                    writePool = new JedisPool(getJedisPoolConfig(), writeRedis.getPath(), writeRedis.getPort(),
                            FrameWorkConfig.getRedisConfig().getTimeout(), FrameWorkConfig.getRedisConfig().getPwd());
                }
            }
        } catch (Exception e) {
            Log.logError("Create Jedis Pool Error!", e);
        }
        return writePool;
    }

    public static boolean hBytesSet(byte[] hName, byte[] key, byte[] value) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            result = jedis.hset(hName, key, value) >= 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static boolean hSet(String hName, String key, String value) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(RedisDB.Default.getDBIndex());
            result = jedis.hset(hName, key, value) >= 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean hSet(RedisKey.H_Name h_Name, String value) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(RedisDB.Default.getDBIndex());
            result = jedis.hset(h_Name.getHName(), h_Name.getKey(), value) >= 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean hSet(String hName, String key, String value, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.hset(hName, key, value) >= 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean hSet(RedisKey.H_Name h_Name, String value, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.hset(h_Name.getHName(), h_Name.getKey(), value) >= 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    /**
     * key string 在key对应list的头部添加字符串元素，返回1表示成功，0表示key存在且不是list类型
     *
     * @param key
     * @param item
     * @return
     */
    public static Long lpush(String key, String item) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        Long result = 0L;
        try {
            jedis.select(RedisDB.Queue.getDBIndex());
            result = jedis.lpush(key, item);
        } finally {
            pool.returnResource(jedis);
        }

        return result;
    }

    /**
     * key string 在key对应list的头部添加字符串元素，返回1表示成功，0表示key存在且不是list类型
     *
     * @param key
     * @param item
     * @return
     */
    public static <T> Long lpushObjectToJson(String key, T clazz) {
        if (clazz != null) {
            String str = JsonUtil.toString(clazz);
            return lpush(key, str);
        }
        return 0L;
    }

    /**
     * key 从list的尾部部删除元素，并返回删除元素。如果key对应list不存在或者是空返回nil，如果key对应值不是list返回错误
     *
     * @param key
     * @return
     */
    public static String rpop(String key) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String item = null;
        try {
            jedis.select(RedisDB.Queue.getDBIndex());
            item = jedis.rpop(key);
        } finally {
            pool.returnResource(jedis);
        }
        return item;
    }

    //
    // public static String rpop(String key,int size) {
    // JedisPool pool = getWritePool();
    // Jedis jedis = pool.getResource();
    // String item = null;
    // try {
    // jedis.select(RedisDB.Queue.getDBIndex());
    // item = jedis.rpop(key);
    // } finally{
    // pool.returnResource(jedis);
    // }
    // return item;
    // }

    /**
     * key 从list的尾部部删除元素，并返回删除元素。如果key对应list不存在或者是空返回nil，如果key对应值不是list返回错误
     *
     * @param key
     * @return
     */
    public static <T> T rpopJsonObject(String key, Class<T> clazz) {
        String value = rpop(key);
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

    public static byte[] hBytesGet(byte[] hName, byte[] key) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        byte[] result = null;
        try {
            result = jedis.hget(hName, key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static String hGet(String hName, String key) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(RedisDB.Default.getDBIndex());
            result = jedis.hget(hName, key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String hGet(RedisKey.H_Name h_Name) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(RedisDB.Default.getDBIndex());
            result = jedis.hget(h_Name.getHName(), h_Name.getKey());
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static String hGet(String hName, String key, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.hget(hName, key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String hGet(RedisKey.H_Name h_Name, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.hget(h_Name.getHName(), h_Name.getKey());
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean exists(String key) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        Boolean result = false;
        try {
            result = jedis.exists(key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean exists(String key, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        Boolean result = false;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.exists(key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static byte[] bytesGet(byte[] key) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        byte[] result = null;
        try {
            result = jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static byte[] bytesGet(byte[] key, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        byte[] result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static String get(String key) {
        return get(key, RedisDB.Default);
    }

    public static String get(RedisKey.Key key) {
        return get(key, RedisDB.Default);
    }

//    @Deprecated
    public static String get(String key, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String get(RedisKey.Key key, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.get(key.getKey());
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static boolean remove(String Key) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            result = jedis.del(Key) > 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean remove(RedisKey.Key Key) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            result = jedis.del(Key.getKey()) > 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static boolean remove(String Key, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.del(Key) > 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean remove(RedisKey.Key Key, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.del(Key.getKey()) > 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String bytesSet(byte[] key, byte[] value) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            result = jedis.set(key, value);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String bytesSet(byte[] key, byte[] value, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.set(key, value);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String bytesSet(byte[] key, byte[] value, int seconds, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            if (seconds > 0) {
                result = jedis.setex(key, seconds, value);
            } else {
                result = jedis.set(key, value);
            }
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

//    @Deprecated
    public static String set(String key, String value) {
        return set(key, value, RedisDB.Default);
    }

    public static String set(RedisKey.Key key, String value) {
        return set(key, value, RedisDB.Default);
    }

//    @Deprecated
    public static String set(String key, String value, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.set(key, value);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String set(RedisKey.Key key, String value, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.set(key.getKey(), value);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String set(String key, String value, int seconds) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = "";
        try {
            if (seconds > 0) {
                result = jedis.setex(key, seconds, value);
            } else {
                result = jedis.set(key, value);
            }
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String set(String key, String value, int seconds, RedisDB redisDB) {
        JedisPool pool = getWritePool();

        String result = "";
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(redisDB.getDBIndex());
            if (seconds > 0) {
                result = jedis.setex(key, seconds, value);
            } else {
                result = jedis.set(key, value);
            }
        } finally {
            if (jedis != null)
                pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean expire(String key, int seconds) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            result = jedis.expire(key, seconds) > 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static boolean expire(String key, int seconds, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean result = false;
        try {
            jedis.select(redisDB.getDBIndex());
            result = jedis.expire(key, seconds) > 0;
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static <T> String setObjectToJson(String key, T clazz) {
        return setObjectToJson(key, clazz, RedisDB.Default);
    }

//    @Deprecated
    public static <T> String setObjectToJson(String key, T clazz, RedisDB redisDB) {
        if (clazz != null) {
            String value = JsonUtil.toString(clazz);
            return set(key, value, redisDB);
        }
        return "";
    }

    public static <T> String setObjectToJson(RedisKey.Key key, T clazz, RedisDB redisDB) {
        if (clazz != null) {
            String value = JsonUtil.toString(clazz);
            return set(key.getKey(), value, redisDB);
        }
        return "";
    }

//    @Deprecated
    public static <T> String setObjectToJson(String key, T clazz, int seconds, RedisDB redisDB) {
        if (clazz != null) {
            String value = JsonUtil.toString(clazz);
            return set(key, value, seconds, redisDB);
        }
        return "";
    }

    public static <T> String setObjectToJson(RedisKey.Key key, T clazz, int seconds, RedisDB redisDB) {
        if (clazz != null) {
            String value = JsonUtil.toString(clazz);
            return set(key.getKey(), value, seconds, redisDB);
        }
        return "";
    }

    public static <T> String setObjectToBytes(String key, T clazz) {
        try {
            if (clazz != null) {
                byte[] bkey = key.getBytes("UTF-8");
                byte[] bvalue = BytesUtil.toBytes(clazz);
                return bytesSet(bkey, bvalue);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> String setObjectToBytes(String key, T clazz, RedisDB redisDB) {
        try {
            if (clazz != null) {
                byte[] bkey = key.getBytes("UTF-8");
                byte[] bvalue = BytesUtil.toBytes(clazz);
                return bytesSet(bkey, bvalue, redisDB);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> String setObjectToBytes(String key, T clazz, int seconds, RedisDB redisDB) {
        try {
            if (clazz != null) {
                byte[] bkey = key.getBytes("UTF-8");
                byte[] bvalue = BytesUtil.toBytes(clazz);
                return bytesSet(bkey, bvalue, seconds, redisDB);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> String setListToBytes(String key, List<T> clazz, int seconds, RedisDB redisDB) {
        try {
            if (clazz != null) {
                byte[] bkey = key.getBytes("UTF-8");
                byte[] bvalue = SerializeUtil.serializeList(clazz);
                return bytesSet(bkey, bvalue, seconds, redisDB);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

//    @Deprecated
    public static <T> T getJsonObject(String key, Class<T> clazz) {
        return getJsonObject(key, clazz, RedisDB.Default);
    }

    public static <T> T getJsonObject(RedisKey.Key key, Class<T> clazz) {
        return getJsonObject(key, clazz, RedisDB.Default);
    }

//    @Deprecated
    public static <T> T getJsonObject(String key, Class<T> clazz, RedisDB redisDB) {
        String value = get(key, redisDB);
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

    public static <T> T getJsonObject(RedisKey.Key key, Class<T> clazz, RedisDB redisDB) {
        String value = get(key.getKey(), redisDB);
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

    public static <T extends Serializable> T getBytesObject(String key, Class<T> clazz, RedisDB redisDB)
            throws Exception {
        T model = null;
        byte[] bkey = key.getBytes("UTF-8");
        byte[] value = bytesGet(bkey, redisDB);
        if (value != null && value.length > 0) {
            model = BytesUtil.toObject(value, clazz);
        }
        return model;
    }

    public static <T extends Serializable> List<T> getBytesList(String key, Class<T> clazz, RedisDB redisDB)
            throws Exception {
        List<T> model = null;
        byte[] bkey = key.getBytes("UTF-8");
        byte[] value = bytesGet(bkey, redisDB);
        if (value != null && value.length > 0) {
            model = SerializeUtil.unserializeList(value, clazz);
        }
        return model;
    }

    public static <T extends Serializable> T getBytesObject(String key, Class<T> clazz) throws Exception {
        T model = null;
        byte[] bkey = key.getBytes("UTF-8");
        byte[] value = bytesGet(bkey);
        if (value != null && value.length > 0) {
            model = BytesUtil.toObject(value, clazz);
        }
        return model;
    }

    public static <T> boolean hSetObjectToBytes(String hname, String key, T clazz) throws Exception {

        if (clazz != null) {
            byte[] bname = hname.getBytes("UTF-8");
            byte[] bkey = key.getBytes("UTF-8");
            byte[] bvalue = BytesUtil.toBytes(clazz);
            return hBytesSet(bname, bkey, bvalue);
        }
        return false;
    }

//    @Deprecated
    public static <T> boolean hSetObjectJson(String hname, String key, T clazz) {
        if (clazz != null) {
            String value1 = JsonUtil.toString(clazz);
            return hSet(hname, key, value1);
        }
        return false;
    }

    public static <T> boolean hSetObjectJson(RedisKey.H_Name h_Name, T clazz) {
        if (clazz != null) {
            String value1 = JsonUtil.toString(clazz);
            return hSet(h_Name.getHName(), h_Name.getKey(), value1);
        }
        return false;
    }

//    @Deprecated
    public static <T> boolean hSetObjectJson(String hname, String key, T clazz, RedisDB redisDB) {
        if (clazz != null) {
            String value1 = JsonUtil.toString(clazz);
            return hSet(hname, key, value1, redisDB);
        }
        return false;
    }

    public static <T> boolean hSetObjectJson(RedisKey.H_Name h_Name, T clazz, RedisDB redisDB) {
        if (clazz != null) {
            String value1 = JsonUtil.toString(clazz);
            return hSet(h_Name.getHName(), h_Name.getKey(), value1, redisDB);
        }
        return false;
    }

    public static <T extends Serializable> T hGetBytesObject(String hname, String key, Class<T> clazz)
            throws Exception {
        byte[] bname = hname.getBytes("UTF-8");
        byte[] bkey = key.getBytes("UTF-8");
        byte[] value = hBytesGet(bname, bkey);
        if (value != null && value.length > 0) {
            return BytesUtil.toObject(value, clazz);
        }
        return null;
    }

//    @Deprecated
    public static <T> T hGetJsonObject(String hname, String key, Class<T> clazz) {
        String value = hGet(hname, key);
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

    public static <T> T hGetJsonObject(RedisKey.H_Name h_Name, Class<T> clazz) {
        String value = hGet(h_Name.getHName(), h_Name.getKey());
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

//    @Deprecated
    public static <T> T hGetJsonObject(String hname, String key, Class<T> clazz, RedisDB redisDB) {
        String value = hGet(hname, key, redisDB);
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

    public static <T> T hGetJsonObject(RedisKey.H_Name h_Name, Class<T> clazz, RedisDB redisDB) {
        String value = hGet(h_Name.getHName(), h_Name.getKey(), redisDB);
        if (value != null && value != "") {
            return (T) JsonUtil.toObject(value, clazz);
        }
        return null;
    }

    public static boolean hExists(String hname, String key) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        Boolean bool = false;
        try {
            bool = jedis.hexists(hname, key);
        } finally {
            pool.returnResource(jedis);
        }
        return bool;
    }

    public static boolean hExists(String hname, String key, RedisDB redisDB) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        Boolean bool = false;
        try {
            jedis.select(redisDB.getDBIndex());
            bool = jedis.hexists(hname, key);
        } finally {
            pool.returnResource(jedis);
        }
        return bool;
    }

    public static boolean hRemove(String hname, String key) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean bool;
        try {
            bool = (jedis.hdel(hname, key) == 1);
        } finally {
            pool.returnResource(jedis);
        }
        return bool;
    }

    public static boolean hRemove(String hname, String key, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        boolean bool;
        try {
            jedis.select(redisDB.getDBIndex());
            bool = (jedis.hdel(hname, key) == 1);
        } finally {
            pool.returnResource(jedis);
        }
        return bool;
    }

    public static Map<String, String> hGetAll(String hname) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        Map<String, String> result = null;
        try {
            result = jedis.hgetAll(hname);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static long hLen(String hname) {
        JedisPool pool = getReadPool();
        Jedis jedis = pool.getResource();
        long result = 0;
        try {
            result = jedis.hlen(hname);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public static String incr(String key, RedisDB redisDB) {
        JedisPool pool = getWritePool();
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            jedis.select(redisDB.getDBIndex());
            jedis.incr(key);
            result = jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

}
