package io.github.dondragon2.spring.nonblocking;

import io.github.dondragon2.spring.nonblocking.callbacks.Callback;
import io.github.dondragon2.spring.nonblocking.callbacks.VoidCallback;
import rx.Observable;

public abstract class NonBlockingService {
    /**
     *
     * @param callback wraps process method that returns T
     * @return Observable T
     */
    protected Observable<?> process(Callback callback) {
        return Observable.fromCallable(callback::process);
    }

    /**
     *
     * @param callback wraps process method that returns void
     * @return Observable void
     */
    protected Observable<?> process(VoidCallback callback) {
        return Observable.fromCallable(() -> {
            callback.process();
            return null; //returns null because a Observable<Void> fails onSubscribe cast
        });
    }
}
