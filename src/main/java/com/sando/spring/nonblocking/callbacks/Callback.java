package com.sando.spring.nonblocking.callbacks;

public interface Callback {
    Object process() throws Exception;
}
