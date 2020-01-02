package com.funtl.spring.cloud.alibaba.commons.redisson.aop;

import com.funtl.spring.cloud.alibaba.commons.redisson.annotation.RedissonLock;
import com.funtl.spring.cloud.alibaba.commons.redisson.enums.RedissonLockModel;
import com.funtl.spring.cloud.alibaba.commons.redisson.excepiton.RedissonLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Order(-10)
public class RedissonLockAop {

    /**
     * 等待加锁超时时间 -1 一直等待
     */
    private static final Long ATTEMPT_TIMEOUT = 10000L;

    /**
     * 看门狗
     * 在一个分布式环境下，多个服务实例请求获取锁，其中服务实例 A 成功获取到了锁，在执行业务逻辑的过程中，服务实例突然挂掉了可以采用锁超时机制解决
     * 如果服务实例 A 没有宕机但是业务执行还没有结束，锁释放掉了就会导致线程问题（误删锁）。此时就一定要实现自动延长锁有效期的机制
     * 看门狗的主要作用：只要这台服务实例没有挂掉，并且没有主动释放锁，看门狗都会每隔十秒给你续约一下，保证锁一直在你手中
     */
    private static final Long LOCK_WATCH_DOG_TIMEOUT = 30000L;

    private RedissonLockModel lockModel;

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(redissonLock)")
    public void controllerAspect(RedissonLock redissonLock) {
    }

    /**
     * 通过 Spring SpEL 获取参数
     *
     * @param key            定义的 key值以 # 开头 例如：#user
     * @param parameterNames 形参
     * @param values         形参值
     * @param keyConstant    key的常亮
     * @return
     */
    public List<String> getVauleBySpel(String key, String[] parameterNames, Object[] values, String keyConstant) {
        List<String> keys = new ArrayList<>();
        if (!key.contains("#")) {
            String s = "redisson:lock:" + key + keyConstant;
            log.info("没有使用 SpEL 表达式 value -> {}", s);
            keys.add(s);
            return keys;
        }
        // SpEL 解析器
        ExpressionParser parser = new SpelExpressionParser();
        // SpEL 上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], values[i]);
        }
        Expression expression = parser.parseExpression(key);
        Object value = expression.getValue(context);
        if (value != null) {
            if (value instanceof List) {
                List value1 = (List) value;
                for (Object o : value1) {
                    keys.add("redisson:lock:" + o.toString() + keyConstant);
                }
            } else if (value.getClass().isArray()) {
                Object[] obj = (Object[]) value;
                for (Object o : obj) {
                    keys.add("redisson:lock:" + o.toString() + keyConstant);
                }
            } else {
                keys.add("redisson:lock:" + value.toString() + keyConstant);
            }
        }
        log.info("SpEL 表达式 key={}, value={}", key, keys);
        return keys;
    }

    @Around("controllerAspect(redissonLock)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, RedissonLock redissonLock) throws Throwable {
        String[] keys = redissonLock.keys();
        if (keys.length == 0) {
            throw new RuntimeException("keys 不能为空");
        }
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(((MethodSignature) proceedingJoinPoint.getSignature()).getMethod());
        Object[] args = proceedingJoinPoint.getArgs();

        long attemptTimeout = redissonLock.attemptTimeout();
        if (attemptTimeout == 0) {
            attemptTimeout = ATTEMPT_TIMEOUT;
        }
        long lockWatchdogTimeout = redissonLock.lockWatchdogTimeout();
        if (lockWatchdogTimeout == 0) {
            lockWatchdogTimeout = LOCK_WATCH_DOG_TIMEOUT;
        }
        RedissonLockModel lockModel = redissonLock.lockModel();
        if (lockModel.equals(RedissonLockModel.AUTO)) {
            RedissonLockModel lockModel1 = lockModel;
            if (lockModel1 != null && !lockModel1.equals(RedissonLockModel.AUTO)) {
                lockModel = lockModel1;
            } else if (keys.length > 1) {
                lockModel = RedissonLockModel.REDLOCK;
            } else {
                lockModel = RedissonLockModel.REENTRANT;
            }
        }
        if (!lockModel.equals(RedissonLockModel.MULTIPLE) && !lockModel.equals(RedissonLockModel.REDLOCK) && keys.length > 1) {
            throw new RuntimeException("参数有多个, 锁模式为 -> " + lockModel.name() + ".无法锁定");
        }
        log.info("锁模式 -> {}, 等待锁定时间 -> {} 秒.锁定最长时间 -> {} 秒", lockModel.name(), attemptTimeout / 1000, lockWatchdogTimeout / 1000);
        boolean res = false;
        RLock rLock = null;
        // 一直等待加锁
        switch (lockModel) {
            case FAIR:
                rLock = redissonClient.getFairLock(getVauleBySpel(keys[0], parameterNames, args, redissonLock.keyConstant()).get(0));
                break;
            case REDLOCK:
                List<RLock> rLocks = new ArrayList<>();
                for (String key : keys) {
                    List<String> vauleBySpel = getVauleBySpel(key, parameterNames, args, redissonLock.keyConstant());
                    for (String s : vauleBySpel) {
                        rLocks.add(redissonClient.getLock(s));
                    }
                }
                RLock[] locks = new RLock[rLocks.size()];
                int index = 0;
                for (RLock r : rLocks) {
                    locks[index++] = r;
                }
                rLock = new RedissonRedLock(locks);
                break;
            case MULTIPLE:
                rLocks = new ArrayList<>();

                for (String key : keys) {
                    List<String> vauleBySpel = getVauleBySpel(key, parameterNames, args, redissonLock.keyConstant());
                    for (String s : vauleBySpel) {
                        rLocks.add(redissonClient.getLock(s));
                    }
                }
                locks = new RLock[rLocks.size()];
                index = 0;
                for (RLock r : rLocks) {
                    locks[index++] = r;
                }
                rLock = new RedissonMultiLock(locks);
                break;
            case REENTRANT:
                List<String> vauleBySpel = getVauleBySpel(keys[0], parameterNames, args, redissonLock.keyConstant());
                //如果spel表达式是数组或者LIST 则使用红锁
                if (vauleBySpel.size() == 1) {
                    rLock = redissonClient.getLock(vauleBySpel.get(0));
                } else {
                    locks = new RLock[vauleBySpel.size()];
                    index = 0;
                    for (String s : vauleBySpel) {
                        locks[index++] = redissonClient.getLock(s);
                    }
                    rLock = new RedissonRedLock(locks);
                }
                break;
            case READ:
                RReadWriteLock rwlock = redissonClient.getReadWriteLock(getVauleBySpel(keys[0], parameterNames, args, redissonLock.keyConstant()).get(0));
                rLock = rwlock.readLock();
                break;
            case WRITE:
                RReadWriteLock rwlock1 = redissonClient.getReadWriteLock(getVauleBySpel(keys[0], parameterNames, args, redissonLock.keyConstant()).get(0));
                rLock = rwlock1.writeLock();
                break;
        }

        // 执行 AOP
        if (rLock != null) {
            try {
                if (attemptTimeout == -1) {
                    res = true;
                    // 一直等待加锁
                    rLock.lock(lockWatchdogTimeout, TimeUnit.MILLISECONDS);
                } else {
                    res = rLock.tryLock(attemptTimeout, lockWatchdogTimeout, TimeUnit.MILLISECONDS);
                }
                if (res) {
                    Object obj = proceedingJoinPoint.proceed();
                    return obj;
                } else {
                    throw new RedissonLockException("获取锁失败");
                }
            } finally {
                if (res) {
                    rLock.unlock();
                }
            }
        }
        throw new RedissonLockException("获取锁失败");
    }

}
