package com.xuxiang.common.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuxiang.common.view.dialog.LoadSuccessAndFailDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 纯净Fragment
 */
public abstract class BaseFragment extends RxFragment {

    protected Activity mActivity;
    Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getResId(), container, false);
        unbinder = ButterKnife.bind(this, v);
        onRealLoaded();
        return v;
    }

    /**
     * 获取真正的数据视图
     *
     * @return
     */
    protected abstract int getResId();

    /**
     * 当视图真正加载时调用
     */
    protected abstract void onRealLoaded();

    @Override
    public void onDestroyView() {
        try {
            if (unbinder != null) unbinder.unbind();
        } catch (Exception e) {

        }
        super.onDestroyView();
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

    protected void showToast(String mes) {
        LoadSuccessAndFailDialog.showFail(mActivity, mes);
    }

    protected void showSuccessToast(String mes) {
        LoadSuccessAndFailDialog.showSuccess(mActivity, mes);
    }
}
