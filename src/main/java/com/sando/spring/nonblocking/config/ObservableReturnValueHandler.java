package com.sando.spring.nonblocking.config;


import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import rx.Observable;

import javax.annotation.Nullable;

public class ObservableReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {

    @Override
    public boolean isAsyncReturnValue(Object returnValue, @Nullable MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        Class parameterType = returnType.getParameterType();
        return Observable.class.isAssignableFrom(parameterType);
    }

    public void handleReturnValue(Object returnValue,
                                  @Nullable MethodParameter returnType,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  @Nullable NativeWebRequest webRequest) throws Exception {
        if (returnValue == null && mavContainer != null) {
            mavContainer.setRequestHandled(true);
            return;
        }

        final Observable<?> observable = (Observable) returnValue;
        WebAsyncUtils.getAsyncManager(webRequest)
            .startDeferredResultProcessing(new ObservableAdapter<>(observable), mavContainer);
    }

    public class ObservableAdapter<T> extends DeferredResult<T> {
        ObservableAdapter(Observable<T> observable) {
            if (observable != null) {
                observable.subscribe(this::setResult, this::setErrorResult);
            }
        }
    }
}
