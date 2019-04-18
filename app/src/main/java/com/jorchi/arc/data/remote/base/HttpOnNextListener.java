package com.jorchi.arc.data.remote.base;

public interface HttpOnNextListener<T> {
    void onNext(T response);
    void onError();
}
