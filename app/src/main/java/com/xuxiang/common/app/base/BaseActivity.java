package com.xuxiang.common.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xuxiang.common.R;
import com.xuxiang.common.app.AppCode;
import com.xuxiang.common.util.AutoDensityUtils;
import com.xuxiang.common.util.bar.StatusBarUtil;
import com.xuxiang.common.view.dialog.LoadSuccessAndFailDialog;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created  on 2018/6/25 0025
 *
 * @author 许翔
 * @describe
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected static final String TAG = "BaseActivity";
    public Unbinder unbinder;
    String FRAGMENTS_TAG = "android:support:fragments";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable(FRAGMENTS_TAG, null);
        }
//        AutoDensityUtils.setCustomDensity(this);
        ActivityInfo activityInfo = getClass().getAnnotation(ActivityInfo.class);
        setContentView(getLayout() == -1 ? activityInfo.layout() : getLayout());
        unbinder = ButterKnife.bind(this);
//         沉浸式状态栏设置 要在setContentView()之后执行
//        当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的padding,  为false时则可以用来设置图片视频沉浸
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
//        状态栏黑白主题切换
        StatusBarUtil.setStatusBarDarkTheme(this, false);
//        自定义状态栏颜色
//        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorWhite));
        init(savedInstanceState);
        init();
    }

    /**
     * 点击返回上一级
     */
    public void onBack(View v) {
        onBackPressed();
    }

    /**
     * 设置每个页面的title
     */
    public TextView setTitle(String title) {
        TextView tv = (TextView) findViewById(R.id.tv_title_name);
        if (tv != null) {
            tv.setText(title);
        }
        return tv;
    }

    /**
     * 标题栏返回图标
     */
    public ImageButton setBackIcon() {
        ImageButton backIcon = (ImageButton) findViewById(R.id.ibtn_title_bar_left);
        return backIcon;
    }
    /**
     * 标题栏右边第一个按钮
     */
//    public ImageButton setRightIcon() {
//        ImageButton button = (ImageButton) findViewById(R.id.ibtn_title_bar_right);
//        return button;
//    }

    /**
     * 标题栏标题栏右边第二个按钮
     */
//    public ImageButton setRightTwoIcon() {
//        ImageButton button = (ImageButton) findViewById(R.id.ibtn_title_bar_right_two);
//        return button;
//    }

    /**
     * 设置标题栏右边文字
     */
    public TextView setRightTitle(String str) {
        TextView button = (TextView) findViewById(R.id.btn_title_bar_right);
        if (button != null) {
            button.setVisibility(View.VISIBLE);
            button.setText(str);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        }
        return button;
    }


    /**
     * 初始化
     */
    protected abstract void init();

    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
        unbinder.unbind();
    }

    protected void showToast(String mes) {
        LoadSuccessAndFailDialog.showFail(this, mes);
    }

    protected void showSuccessToast(String mes) {
        LoadSuccessAndFailDialog.showSuccess(this, mes);
    }

    /**
     * 便捷指定线程
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T, T> getThread() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 跳转
     *
     * @param cla
     */
    public void startActivity(Class cla) {
        Intent intent = new Intent(this, cla);
        super.startActivity(intent);
    }

    /**
     * 跳转
     *
     * @param cla
     */
    public void startActivity(Class cla, int code) {
        Intent intent = new Intent(this, cla);
        super.startActivityForResult(intent, code);
    }

    /**
     * 点击键盘以外区域，收起键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return onTouchEvent(ev);
        }
    }

    protected boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }

    Disposable disposable;

    public void clearContent() {
        disposable = Observable.timer(1001, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    finish();
                    Intent intent = new Intent(this, getClass());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }).subscribe();
    }

    public void clearContent(Serializable object) {
        disposable = Observable.timer(1001, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    Intent intent = new Intent(this, getClass());
                    intent.putExtra(AppCode.INTENT_OBJECT, object);
                    startActivity(intent);
                    finish();
                }).subscribe();
    }

    public void clearFinish() {
        disposable = Observable.timer(1001, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    finish();
                }).subscribe();
    }

    public void clearFinishForResult(int resultCode) {
        disposable = Observable.timer(1001, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    setResult(resultCode);
                    finish();
                }).subscribe();
    }

    public void clearFinish(int time) {
        disposable = Observable.timer(time, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    finish();
                }).subscribe();
    }

    protected int getLayout() {
        return -1;
    }
}
