package com.demo.spring.redis.lock.aop;

import com.demo.spring.redis.lock.biz.RedisBiz;
import com.demo.spring.redis.lock.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiaomengnan
 * @ClassName: ConcurrentLockAop
 * @Description: 分布式并发锁
 * @date 2020/7/2
 */
@Component
@Aspect
public class ConcurrentLockAop {

    /**
     * @Fields  : redisBiz
     * @author qiaomengnan
     */
    @Autowired
    private RedisBiz redisBiz;

    @Around("execution(* com.demo..*.*(..)) && @annotation(concurrentLock)")
    public Object around(ProceedingJoinPoint joinPoint, ConcurrentLock concurrentLock) throws Throwable {
        String key = buildKey(concurrentLock);
        try {
            // 在执行前上锁,以用户名或者ID为单位作为锁
            if(redisBiz.lockBySecondsTime(key, concurrentLock.time())) {
                return joinPoint.proceed();
            } else {
                // 没有获取到锁,则把key赋值为null,以免finally里解锁
                key = null;
                throw new ServiceException(concurrentLock.message());
            }
        } finally {
            // 执行完毕后解锁
            if(key != null) {
                redisBiz.delete(key);
            }
        }
    }

    /**
     * @Title:
     * @Description:  构建redis锁 key
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2020/7/2
     */
    private String buildKey(ConcurrentLock concurrentLock) {
        // 自行根据系统业务构建出唯一的key，例如 lock_用户id  , return lock_用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return concurrentLock.key() + request.getParameter("userId");
    }

}
