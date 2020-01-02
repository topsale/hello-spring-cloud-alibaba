package com.funtl.spring.cloud.alibaba.commons.redisson.excepiton;

public class RedissonLockException extends RuntimeException {

    public RedissonLockException() {
    }

    public RedissonLockException(String message) {
        super(message);
    }

    public RedissonLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedissonLockException(Throwable cause) {
        super(cause);
    }

    public RedissonLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
