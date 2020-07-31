package com.xuxiang.common.view.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.xuxiang.common.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tkk on 2017/8/21.
 * 底部弹出的
 */

public abstract class BaseBottomDialog extends DialogFragment {
    private static final String TAG = "base_bottom_dialog";
    protected float DEFAULT_DIM = 0.2f;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        View v = inflater.inflate(getLayoutRes(), container, false);
        unbinder =  ButterKnife.bind(this, v);
        bindView();
        return v;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView();

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height  =  getHeight() > 0?getHeight(): WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public int getHeight() {
        return 0;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return TAG;
    }



    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getFragmentTag());
    }
}
