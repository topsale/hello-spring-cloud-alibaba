package com.funtl.spring.cloud.alibaba.commons.redisson.annotation;

import com.funtl.spring.cloud.alibaba.commons.redisson.enums.RedissonLockModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

    /**
     * 锁的模式：如果不设置，自动模式，当参数只有一个使用 REENTRANT 参数多个 MULTIPLE
     *
     * @return
     */
    RedissonLockModel lockModel() default RedissonLockModel.AUTO;

    /**
     * 如果 keys 有多个，如果不设置则使用联锁
     *
     * @return
     */
    String[] keys() default {};

    /**
     * key 的静态常量：当 key 的 spel 的值是 List，数组时使用 + 号连接将会被 spel 认为这个变量是个字符串，只能产生一把锁，达不到我们的目的
     * 而我们如果又需要一个常量的话这个参数将会在拼接在每个元素的后面
     *
     * @return
     */
    String keyConstant() default "";

    /**
     * 锁超时时间，默认 30000 毫秒
     *
     * @return
     */
    long lockWatchdogTimeout() default 0;

    /**
     * 等待加锁超时时间，默认 10000 毫秒 -1 则表示一直等待
     *
     * @return
     */
    long attemptTimeout() default 0;


}
