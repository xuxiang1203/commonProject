package com.xuxiang.common.wxapi;

import android.app.Activity;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuxiang.common.view.dialog.LoadSuccessAndFailDialog;

public class WxPayUtil {
    public static final String APPID = "wxa6fa0fc1c37f2b94";
    public static final String APPSECRET = "3262a9bd6a0742dd219adde218ce2cf6";

    private IWXAPI api;
    Activity act;
    PayReq req;

    public WxPayUtil(Activity act, PayReq req) {
        api = WXAPIFactory.createWXAPI(act, APPID);
        api.registerApp(APPID);
        this.act = act;
        this.req = req;
        pay();
    }

    //PayReq req = new PayReq();
    //                            req.appId = WxPayUtil.APPID;
    //                            req.partnerId = data.getPartnerid();
    //                            req.prepayId = data.getPrepayid();
    //                            req.nonceStr = data.getNoncestr();
    //                            req.timeStamp = data.getTimestamp();
    //                            req.packageValue = data.getPackageValue();
    //                            req.sign = data.getSign();
    //                            req.extData = "app data"; // optional
    //
    //                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
    //                            new WxPayUtil(PayDialogActivity.this, req);
    //

    public void pay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            LoadSuccessAndFailDialog.showFail(act, "未安装微信或微信版本过低,请先下载安装!");
            return;
        }
        api.sendReq(req);
    }
}
