package com.sando.spring.nonblocking;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import rx.Observable;

@Aspect
@Component
public class NonBlockingAspect {

    @Around("@annotation(com.sando.spring.nonblocking.annotations.NonBlocking)")
    public Observable<?> handleRequest(ProceedingJoinPoint joinPoint){
        return Observable.fromCallable(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new Exception(throwable);
            }
        });
    }
}
