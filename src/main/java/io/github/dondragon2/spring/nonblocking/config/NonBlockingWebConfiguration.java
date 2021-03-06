package io.github.dondragon2.spring.nonblocking.config;

import io.github.dondragon2.spring.nonblocking.NonBlockingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class NonBlockingWebConfiguration implements WebMvcConfigurer {
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new ObservableReturnValueHandler());
    }

    @Bean
    public NonBlockingAspect nonBlockingAspect() {
        return new NonBlockingAspect();
    }
}
