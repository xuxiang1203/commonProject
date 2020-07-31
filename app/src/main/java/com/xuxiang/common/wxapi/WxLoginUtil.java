package com.xuxiang.common.wxapi;

import android.content.Context;
import android.util.Log;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tkk.api.RxEventProcessor;
import com.xuxiang.common.app.AppCode;
import com.xuxiang.common.view.dialog.LoadSuccessAndFailDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WxLoginUtil {
    private IWXAPI api;
    private Context context;

    public WxLoginUtil(Context context) {
        this.context = context;
        api = WXAPIFactory.createWXAPI(context, WxPayUtil.APPID, true);
        api.registerApp(WxPayUtil.APPID);
    }

    public void sendAuth() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            LoadSuccessAndFailDialog.showFail(context, "未安装微信或微信版本过低,请先下载安装!");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    public void getWxOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WxPayUtil.APPID +
                "&secret=" + WxPayUtil.APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("onFailure", call.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseStr = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseStr);
                                String openId = jsonObject.getString("openid");
                                String access_token = jsonObject.getString("access_token");
                                getWxUser(openId, access_token);
                            } catch (JSONException e) {

                            }
                        }
                    }
                });
    }

    public void getWxUser(String openid, String token) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
            + token
            + "&openid=" + openid;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseStr = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseStr);
                                String nickname = jsonObject.getString("nickname");
                                String headimgurl = jsonObject.getString("headimgurl");
                                RxEventProcessor.get().post(AppCode.APP_OPENID, openid, nickname, headimgurl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
