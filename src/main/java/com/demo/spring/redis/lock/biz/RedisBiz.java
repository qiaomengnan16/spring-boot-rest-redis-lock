package com.demo.spring.redis.lock.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaomengnan
 * @ClassName: RedisRepository
 * @Description: redis使用层
 * @date 2019/6/28
 */
@Repository
public class RedisBiz {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<Object,Object> valOps;

    @Resource(name = "redisTemplate")
    private SetOperations<Object,Object> setOps;

    /**
     * @Title:
     * @Description:   保存K-V,并设置时间
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/06/28 01:51:32
     */
    public void save(Object key, Object value, long time){
        valOps.set(key,value,time,TimeUnit.SECONDS);
    }

    /**
     * @Title:
     * @Description:   获取过期时间
     * @param key
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/11/01 10:22:46
     */
    public Long getExpire(Object key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @Title:
     * @Description:   保存K-V,并设置时间
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/06/28 01:51:32
     */
    public Object get(Object key){
        return valOps.get(key);
    }

    /**
     * @Title:
     * @Description:   保存K-V
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/06/28 01:51:32
     */
    public void saveSetValue(Object key , Object ...value) {
        setOps.add(key, value);
    }

    /**
     * @Title:
     * @Description:   保存K-V
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/06/28 01:51:32
     */
    public void saveSetValue(Object key , long time , Object ...value) {
        setOps.add(key, value,time,TimeUnit.SECONDS);
    }

    /**
     * @Title:
     * @Description:   判断一个key是否存在
     * @param key
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/08/10 03:11:41
     */
    public boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @Title:
     * @Description:   删除key
     * @param key
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/09/04 09:30:18
     */
    public void delete(Object key) {
        redisTemplate.delete(key);
    }

    /**
     * @Title:
     * @Description:   获取Long类型的值
     * @param key 键
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/08/07 04:47:58
     */
    public Long getLong(Object key) {
        try {
            Object res = valOps.get(key);
            return res == null ? null : Long.parseLong(res.toString());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @Title:
     * @Description:   根据秒单位锁key
     * @param key  键
     * @param expirationTime 过期时间
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/08/07 04:48:12
     */
    public boolean lockBySecondsTime(String key, long expirationTime){
        // TODO: 2019/8/7  此方法只适用于在 expirationTime 时间段内进行锁竞争的场景。如果超过 expirationTime 时间段，锁自动失效，之前获取到锁的线程还在运行，就失去了分布式锁的意义，慎重根据自己的场景来使用。
        Long timeStamp = new Date().getTime() + (expirationTime * 1000);
        // 通过setNx获取锁
        return ifAbsent(key, String.valueOf(timeStamp), expirationTime, TimeUnit.SECONDS);
    }

    /**
     * @Title:
     * @Description:   通过setNx获取锁
     * @param key 键
     * @param value 值
     * @param expirationTime 过期时间
     * @param timeUnit 时间单位
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2019/08/07 04:52:23
     */
    public boolean ifAbsent(String key, String value, long expirationTime , TimeUnit timeUnit) {
        Boolean res = (Boolean) redisTemplate.execute(new RedisCallback() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.stringCommands().set(key.getBytes(), value.getBytes(),
                        Expiration.from(expirationTime, timeUnit), RedisStringCommands.SetOption.ifAbsent());
            }
        });
        return res == null ? false : res;
    }

}
