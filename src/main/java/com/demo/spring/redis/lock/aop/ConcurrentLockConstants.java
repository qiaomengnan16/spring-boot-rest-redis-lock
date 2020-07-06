package com.demo.spring.redis.lock.aop;

/**
 * @author qiaomengnan
 * @ClassName: ConcurrentLockConstants
 * @Description: 并发锁常量配置类
 * @date 2020/7/2
 */
public class ConcurrentLockConstants {

    /**
     * @Fields  : 默认key
     * @author qiaomengnan
     */
    public static final String DEFAULT_KEY = "CONCURRENT_LOCK:";

    /**
     * @Fields  : 未获取到锁默认提示语
     * @author qiaomengnan
     */
    public static final String DEFAULT_MESSAGE = "您的操作过于频繁,请稍后再试";

}
