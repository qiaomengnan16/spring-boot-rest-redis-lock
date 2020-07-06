package com.demo.spring.redis.lock.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qiaomengnan
 * @ClassName: ConcurrentLockConstants
 * @Description: 并发锁注解
 * @date 2020/7/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcurrentLock {

    /**
     * @Fields  : 分布式锁key
     * @author qiaomengnan
     */
    String key() default ConcurrentLockConstants.DEFAULT_KEY;

    /**
     * @Fields  : 失效时间 单位(秒)
     * @author qiaomengnan
     */
    int time() default 120;

    /**
     * @Fields  : 获取锁失败提示消息
     * @author qiaomengnan
     */
    String message() default ConcurrentLockConstants.DEFAULT_MESSAGE;

}
