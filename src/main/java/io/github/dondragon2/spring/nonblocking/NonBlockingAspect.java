package io.github.dondragon2.spring.nonblocking;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import rx.Observable;

@Aspect
public class NonBlockingAspect {

    @Around("@annotation(io.github.dondragon2.spring.nonblocking.annotations.NonBlocking)")
    public Observable<?> handleRequest(ProceedingJoinPoint joinPoint) {
        return Observable.fromCallable(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                if (throwable instanceof Exception) {
                    throw (Exception) throwable;
                }

                throw new Exception(throwable);
            }
        });
    }
}
