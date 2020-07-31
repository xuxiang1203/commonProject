package com.xuxiang.common.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuxiang.common.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：加载成功或者失败
 */
public class LoadSuccessAndFailDialog extends AlertDialog {
    String type;
    ImageView iv_loadimg;
    TextView tv_loadtext;
    Disposable disposable;
    private static LoadSuccessAndFailDialog dialog;

    protected LoadSuccessAndFailDialog(Context context) {
        super(context, R.style.normal_dialog);
    }

    protected LoadSuccessAndFailDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, R.style.normal_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏透明
        getWindow().setBackgroundDrawableResource(R.drawable.shape_corner_5dp);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.dialog_loading_result);
        initWidget();
        init();
    }

    private void init() {

    }

    private void initWidget() {
        iv_loadimg = findViewById(R.id.iv_loadimg);
        tv_loadtext = findViewById(R.id.tv_loadtext);
        //dialog基本设置
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public static void showSuccess(Context context, String msg) {
        dialog = new LoadSuccessAndFailDialog(context);
        dialog.show();
        dialog.tv_loadtext.setText(msg);
        Glide.with(context).load(R.mipmap.ic_success).centerCrop().into(dialog.iv_loadimg);

    }

    public static void showSaveSuccess(Context context, String msg) {
        dialog = new LoadSuccessAndFailDialog(context);
        dialog.show();
        dialog.tv_loadtext.setText(msg);
        Glide.with(context).load(R.mipmap.ic_success).centerCrop().into(dialog.iv_loadimg);

    }

    public static void showFail(Context context, String msg) {
        dialog = new LoadSuccessAndFailDialog(context);
        dialog.show();
        dialog.tv_loadtext.setText(msg);
        Glide.with(context).load(R.mipmap.ic_fail).centerCrop().into(dialog.iv_loadimg);
    }

    public static void dissmiss(Activity activity, String msg) {
        dialog = new LoadSuccessAndFailDialog(activity);
        dialog.show();
        dialog.tv_loadtext.setText(msg);
        Glide.with(activity).load(R.mipmap.ic_fail).centerCrop().into(dialog.iv_loadimg);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            dialog.dismiss();
            activity.finish();
        }, 1000);
    }

    public void show() {
        super.show();
        disposable = Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    dismiss();
                }).subscribe();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
