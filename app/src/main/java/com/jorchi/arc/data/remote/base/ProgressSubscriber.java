package com.jorchi.arc.data.remote.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class ProgressSubscriber<T> implements Observer<T> {
    private HttpOnNextListener<T> mSubscriberOnNextListener;
    private WeakReference<Context> mActivity;
    private boolean cancel;
    private boolean showDialog;
    private ProgressDialog pb;
    private Disposable mDisposable;

    public ProgressSubscriber(Context context, HttpOnNextListener nextListener) {
        this.mActivity = new WeakReference<>(context);
        this.mSubscriberOnNextListener = nextListener;
        this.showDialog = true;
        this.cancel = true;
        this.initProgressDialog();
    }

    public ProgressSubscriber(Context context, HttpOnNextListener nextListener,
                              boolean showDialog) {
        this.mActivity = new WeakReference<>(context);
        this.mSubscriberOnNextListener = nextListener;
        this.showDialog = showDialog;
        this.cancel = true;
        this.initProgressDialog();
    }

    public ProgressSubscriber(Context context, HttpOnNextListener nextListener,
                              boolean showDialog, boolean cancel) {
        this.mActivity = new WeakReference<>(context);
        this.mSubscriberOnNextListener = nextListener;
        this.showDialog = showDialog;
        this.cancel = cancel;
        this.initProgressDialog();
    }

    private void initProgressDialog() {
        Context context = (Context) this.mActivity.get();
        if (this.pb == null && context != null) {
            this.pb = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            this.pb.setMessage("数据加载中，请稍后...");
            this.pb.setCancelable(this.cancel);
            if (this.cancel) {
                this.pb.setOnCancelListener(dialog -> {
                    ProgressSubscriber.this.onCancelProgress();
                });
            }
        }
    }

    /*
     * 取消对请求结果的订阅
     */
    private void onCancelProgress() {
        if (!this.mDisposable.isDisposed()) {
            this.mDisposable.dispose();
        }
    }

    private void showProgressDialog() {
        Context context = (Context) this.mActivity.get();
        if (this.pb != null && context != null) {
            try {
                if (!this.pb.isShowing()) {
                    this.pb.show();
                }
            } catch (Exception e) {
                ;
            }
        }
    }

    private void dismissProgressDialog() {
        try {
            if (this.pb != null && this.pb.isShowing()) {
                this.pb.dismiss();
            }
        } catch (Exception e) {
            ;
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            Toast.makeText(this.mActivity.get(), "网络中断，请检查您的网络", Toast.LENGTH_SHORT).show();
        }
        Log.e("retrofit", "onError------->" + e.toString());
        this.dismissProgressDialog();
        if (this.mSubscriberOnNextListener != null) {
            this.mSubscriberOnNextListener.onError();
        }
    }

    @Override
    public void onComplete() {
        this.dismissProgressDialog();
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        if (this.showDialog) {
            this.showProgressDialog();
        }
    }

    @Override
    public void onNext(T response) {
        if (this.mSubscriberOnNextListener != null) {
            this.mSubscriberOnNextListener.onNext(response);
        }
    }
}
