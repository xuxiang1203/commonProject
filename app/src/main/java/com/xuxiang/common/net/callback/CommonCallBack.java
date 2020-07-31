package com.xuxiang.common.net.callback;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.xuxiang.common.net.HttpConfig;
import com.xuxiang.common.view.dialog.LoadSuccessAndFailDialog;
import com.xuxiang.common.view.dialog.LoadingDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class CommonCallBack<T> implements Observer<CommonResult<T>> {

    private boolean isShowDialog;
    private Context context;
    private String mes = "请稍后";
    LoadingDialog dialog;

    public CommonCallBack() {
    }

    public CommonCallBack(Context context) {
        this(false, context);
        if (context == null) {
            this.context = null;
        }
    }

    public CommonCallBack(boolean isShowDialog, Context context) {
        this.isShowDialog = isShowDialog;
        this.context = context;
    }

    public CommonCallBack(boolean isShowDialog, Context context, String mes) {
        this.isShowDialog = isShowDialog;
        this.context = context;
        this.mes = mes;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isShowDialog) {
            dialog = new LoadingDialog(context);
//            dialog.setImg(R.drawable.loading);
//            dialog.setMsg("请稍后");
            dialog.show();
        }
    }

    @Override
    public void onNext(CommonResult<T> tCommonResult) {
        int status = tCommonResult.getStatus();
        if (status == HttpConfig.HTTP_SUCCESS) {
            onCallBackSuccess(tCommonResult.getData());
        } else {
            onCallBackOther(tCommonResult);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (isShowDialog) {
            dialog.dismiss();
            dialog.cancel();
        }
        if (e instanceof SocketTimeoutException) {
            onErrorMsg("链接超时,请稍后再试");
        } else if (e instanceof ConnectException) {
            onErrorMsg("服务器连接失败");
        } else if (e instanceof JsonSyntaxException) {
            onErrorMsg("解析失败");
        } else {
            onErrorMsg("请求失败");
        }
        onCallBackError();
    }

    @Override
    public void onComplete() {
        if (isShowDialog) {
            dialog.dismiss();
        }
    }

    /**
     * 数据请求成功
     *
     * @param data
     */
    public abstract void onCallBackSuccess(T data);

    public void onCallBackError() {

    }

    /**
     * 其他情况
     *
     * @param result
     */
    public void onCallBackOther(CommonResult result) {
        if (context != null)
            LoadSuccessAndFailDialog.showFail(context, result.getMessage());
    }

    public void onErrorMsg(String msg) {
        if (context != null)
            LoadSuccessAndFailDialog.showFail(context, msg);
    }
}
