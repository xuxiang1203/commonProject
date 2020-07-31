package com.xuxiang.common.util;

import android.content.Context;

import com.xuxiang.common.R;
import com.xuxiang.common.view.dialog.LoadingDialog;

public class LoadingUtil {
    private static LoadingDialog loadingDialog;

    public static void showLoading(Context context) {
        loadingDialog = new LoadingDialog(context);
        loadingDialog.setImg(R.drawable.gif_loading_hua);
        loadingDialog.setMsg("请稍后");
        loadingDialog.show();
    }

    public static void showLoading(Context context, String msg) {
        loadingDialog = new LoadingDialog(context);
        loadingDialog.setImg(R.drawable.gif_loading_hua);
        loadingDialog.setMsg(msg);
        loadingDialog.show();
    }

    public static void hide() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
