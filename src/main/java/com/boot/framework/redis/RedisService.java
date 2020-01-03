package com.boot.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Liuweidong 
 * 		         使用API操作基本redis基本数据类型
 * 
 *         spring提供哪些接口操作redis基本数据？ 
 *         第一组:
 *         ValueOperations：字符串类型操作
 *         ListOperations：列表类型操作 
 *         SetOperations：集合类型操作
 *         ZSetOperations：有序集合类型操作 
 *         HashOperations：散列操作
 *         第二组:
 *         BoundValueOperations：字符串类型操作 
 *         BoundListOperations：列表类型操作
 *         BoundSetOperations：集合类型操作 
 *         BoundZSetOperations：有序集合类型操作
 *         BoundHashOperations：散列操作
 *	第二组API只是在第一组API的上面将key值的绑定放在获得接口时了，此举方便了每次操作基本数据类型的时候不用反复的去填写key值，只需要操作具体的value就行了。	
 */
@Component
public class RedisService {
	@Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            //BoundValueOperations<String,Object> aa = redisTemplate.boundValueOps(key);
            //aa.set(value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    public boolean set(final String key, Object value, Long expire) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param expire 过期时间
     * @return
     */
    public boolean set(final String key,Long expire) {
        boolean result = false;
        try {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 批量删除对应的key
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 模糊匹配批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        //KEYS * 命令慎用，当数据规模较大时使用，会严重影响Redis性能，也非常危险立马改掉项目中使用redisTemplate.keys()这个方法，
        // 改了后立马cpu降下来，延迟恢复正常，而且控制台中也没有read time out,connect time out 等异常信息了
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的key
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Hash添加数据
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * Hash获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * List列表添加
     *
     * @param key
     * @param value
     */
    public void lPush(String key,Object value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(key,value);
    }

    /**
     * List列表获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lRange(String key, long start, long end){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(key,start,end);
    }

    /**
     * Set集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * Set集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * ZSet有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * ZSet有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * ZSet有序集合范围获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
	public Set<Object> range(String key, long start, long end) {
		ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
		return zset.range(key, start, end);
	}

    /**
     * Redis SetNX 指令
     */
    public boolean setNx1(String key, Object value) {
        return (boolean) redisTemplate.execute((RedisConnection conn) -> {
            try {
                byte[] rediskey = redisTemplate.getKeySerializer().serialize(key);
                byte[] redisvalue = redisTemplate.getValueSerializer().serialize(value.toString());
                return conn.setNX(rediskey,redisvalue);
            } finally {
                conn.close();
            }
        });
    }

	public boolean setNx2(String key, Object value) {
        RedisConnection redisConnect = redisTemplate.getConnectionFactory().getConnection();
        boolean result = false;
        try {
            byte[] rediskey = redisTemplate.getKeySerializer().serialize(key);
            byte[] redisvalue = redisTemplate.getValueSerializer().serialize(value.toString());
            result = redisConnect.setNX(rediskey, redisvalue);
        }finally {
            redisConnect.close();
        }
        return result;
	}

	public boolean setNx3(String key, Object value) {
		return this.redisTemplate.opsForValue().setIfAbsent(key, value);
	}

    /**
     * redis 分布式锁
     * @param key 键
     * @param value 值
     * @param expire 过期时间(秒)
     * @return
     */
    public boolean lock1(String key, String value, Long expire) {
        RedisConnection redisConnect = redisTemplate.getConnectionFactory().getConnection();
        boolean result = false;
        try {
            boolean flag = this.setNx1(key, value);
            if (flag) {
                if(expire!=null){
                    //redisConnect.expire(key.getBytes(),expire);
                    this.set(key, expire);
                }
                result = true;
            }
        } finally {
            redisConnect.close();
        }
        return result;
    }

    /**
     * redis 分布式锁
     * @param key 键
     * @param value 值
     * @param expire 过期时间(秒)
     * @return
     */
    public boolean lock2(String key, String value, Long expire) {
        if(expire != null){
            return this.redisTemplate.opsForValue().setIfAbsent(key,value,expire,TimeUnit.SECONDS);
        }else{
            return this.setNx3(key,value);
        }
    }

    public void releaseLock(String key) {
        this.remove(key);
    }
}