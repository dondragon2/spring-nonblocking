package io.github.dondragon2.spring.nonblocking.callbacks;

public interface Callback {
    Object process() throws Exception;
}
