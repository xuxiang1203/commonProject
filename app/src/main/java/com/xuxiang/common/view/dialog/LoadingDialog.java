package com.xuxiang.common.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xuxiang.common.R;
import com.xuxiang.common.app.base.GlideApp;


public class LoadingDialog extends AlertDialog {

    ImageView iv_loadimg;
    TextView tv_loadtext;
    int layoutid;
    String msg;
    int resid = R.drawable.gif_loading_hua;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.normal_dialog_white);
    }

    public LoadingDialog(@NonNull Context context, int layoutid) {
        super(context, R.style.normal_dialog_white);
        this.layoutid = layoutid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏透明
        getWindow().setBackgroundDrawableResource(R.color.trans00);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.dialog_loading);
        initWidget();
        init();
    }

    private void init() {
        tv_loadtext.setText(msg);
        GlideApp.with(getContext()).asGif().load(resid).diskCacheStrategy(DiskCacheStrategy.DATA).into(iv_loadimg);
    }


    private void initWidget() {
        iv_loadimg = findViewById(R.id.iv_loadimg);
        tv_loadtext = findViewById(R.id.tv_loadtext);
        //dialog基本设置
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setImg(int resid) {
        this.resid = resid;
    }
}

