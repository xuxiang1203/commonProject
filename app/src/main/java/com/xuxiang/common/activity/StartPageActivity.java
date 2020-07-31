package com.xuxiang.common.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.xuxiang.common.R;
import com.xuxiang.common.app.base.ActivityInfo;
import com.xuxiang.common.app.base.BaseActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@ActivityInfo(layout = R.layout.activity_start_page)
public class StartPageActivity extends BaseActivity {

    @Override
    protected void init() {
        StartPageActivityPermissionsDispatcher.onAgreePermissionWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onAgreePermission() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        }, 1000);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissionFailed() {
        showToast("获取存储权限失败");
        clearFinish();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        takePictureUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        StartPageActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
