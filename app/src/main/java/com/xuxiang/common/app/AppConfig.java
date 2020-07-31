package com.xuxiang.common.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * 应用配置信息类
 */
public class AppConfig {
    private static final String DEFAULT_PREFERENCES_NAME = "appconfig";
    private Context m_appContext;
    private static AppConfig instance;
    private UserInfo user;
    /**
     * 是否存在崩溃
     */
    private boolean m_bCrashHappen;
    /**
     * 是否第一次安装进入
     */
    private boolean m_bIsFirstOpenWelcome;

    /**
     * 是否存在版本更新
     */
    private boolean m_isHaveVersionUpdate;

    /**
     * 是否同意用户协议
     */
    private boolean m_isAgree;

    /**
     * 用户登录的token
     */
    private String mToken;

    /**
     * token对应的关键字
     */
    private String mTokenKey;


    /**
     * 获取单件实例
     *
     * @return
     */
    public static AppConfig getInstance() {
        if (null == instance)
            instance = new AppConfig();
        return instance;
    }

    public void init(Context c) {
        m_appContext = c;
        SharedPreferences sp = c.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        m_bCrashHappen = sp.getBoolean("crashHappen", false);
        m_bIsFirstOpenWelcome = sp.getBoolean("IsFirstOpenWelcome",
                true);
        // m_proId = prefDefault.getInt("proId", -1);
        m_isHaveVersionUpdate = sp.getBoolean("IsHaveVersionUpdate",
                false);
        m_isAgree = sp.getBoolean("m_isAgree", false);
        mToken = sp.getString("Token", "");
        mTokenKey = sp.getString("TokenKey", "");

        String userJson = sp.getString("UserBean", "");
        if (TextUtils.isEmpty(userJson)) {
            user = null;
        } else {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, UserInfo.class);
        }
    }

    public UserInfo getUser() {
        return user;
    }

    /**
     * 保存对象
     */
    public void setUser(UserInfo newUser) {
        Gson gson = new Gson();
        SharedPreferences sp = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        String userStr = gson.toJson(newUser);
        editor.putString("UserBean", userStr);
        editor.commit();
        user = newUser;
    }

    /**
     * 更新保存的user信息
     */
    public void upDataUser() {
        Gson gson = new Gson();
        SharedPreferences sp = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        String userStr = gson.toJson(user);
        editor.putString("UserBean", userStr);
        editor.commit();
    }

    /**
     * 设置是否第一次安装进入
     *
     * @param isFirstOpen
     */
    public void setIsFirstOpenWelcome(boolean isFirstOpen) {
        m_bIsFirstOpenWelcome = isFirstOpen;
        SharedPreferences prefDefault = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = prefDefault.edit();
        editor.putBoolean("IsFirstOpenWelcome", isFirstOpen);
        editor.apply();
    }

    /**
     * 获取是否第一次安装进入
     *
     * @return
     */
    public boolean getIsFirstOpenWelcome() {
        return m_bIsFirstOpenWelcome;
    }

    /**
     * 设置存在崩溃
     *
     * @param bHappen
     */
    public void setCrashHappen(boolean bHappen) {
        m_bCrashHappen = bHappen;
        SharedPreferences prefDefault = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = prefDefault.edit();
        editor.putBoolean("crashHappen", bHappen);
        editor.apply();
    }

    /**
     * 获取是否存在崩溃
     *
     * @return
     */
    public boolean getCrashHappen() {
        return m_bCrashHappen;
    }

    /**
     * 是否有版本更新
     *
     * @return
     */
    public boolean isHaveVersionUpdate() {
        return m_isHaveVersionUpdate;
    }

    /**
     * 是否有版本更新
     *
     * @param isHaveVersionUpdate
     */
    public void setIsHaveVersionUpdate(boolean isHaveVersionUpdate) {
        this.m_isHaveVersionUpdate = isHaveVersionUpdate;
        SharedPreferences prefDefault = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = prefDefault.edit();
        editor.putBoolean("IsHaveVersionUpdate", m_isHaveVersionUpdate);
        editor.apply();
    }

    public boolean isAgree() {
        return m_isAgree;
    }

    public void setIsAgree(boolean isAgree) {
        this.m_isAgree = isAgree;
        SharedPreferences prefDefault = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = prefDefault.edit();
        editor.putBoolean("m_isAgree", isAgree);
        editor.apply();
    }

    /**
     * 获取登录后返回的token
     *
     * @return
     */
    public String getToken() {
        return mToken;
    }

    /**
     * 设置登录后返回的token
     *
     * @param token
     */
    public void setToken(String token) {
        this.mToken = token;
        SharedPreferences prefDefault = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = prefDefault.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    /**
     * 获取token对应的关键字
     *
     * @return
     */
    public String getTokenKey() {
        return mTokenKey;
    }

    /**
     * 设置token对应的关键字
     *
     * @param mTokenKey
     */
    public void setTokenKey(String mTokenKey) {
        this.mTokenKey = mTokenKey;
        SharedPreferences prefDefault = m_appContext.getSharedPreferences(
                DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = prefDefault.edit();
        editor.putString("TokenKey", mTokenKey);
        editor.apply();
    }



    /**
     * 退出登录 清除用户数据
     */
    public void logout() {
        setToken("");
        setTokenKey("");
        setUser(null);

//        Intent intent = new Intent(m_appContext, MainActivity.class);
//        intent.setAction(AppCode.EXIT_LOGIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        m_appContext.startActivity(intent);
    }

    /**
     * 进入登录页面
     */
    public void login() {
//        Intent intent = new Intent(m_appContext, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        m_appContext.startActivity(intent);
    }

    /**
     * 根据token是否已登录
     *
     * @return
     */
    public boolean isLogin() {
        if (TextUtils.isEmpty(mToken) || user == null)
            return false;
        else
            return true;
    }
}
