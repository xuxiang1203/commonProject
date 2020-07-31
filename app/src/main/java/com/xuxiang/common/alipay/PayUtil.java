package com.xuxiang.common.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public abstract class PayUtil {

	private static final int SDK_PAY_FLAG = 1;
	private Activity act;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //Toast.show("支付成功");
                        onSuccess();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(act,"支付失败", Toast.LENGTH_SHORT).show();
                        onError();
                    }
                    break;
				}
				default:
					break;
			}
		}
	};
	public  abstract void onSuccess();
	public  abstract void onError();
	public PayUtil(Activity act) {
		this.act = act;
	}

	/**
	 * 调用SDK支付
	 * @param orderInfo 从服务器端获取的支付请求字符串
     */
	public void pay(final String orderInfo) {

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(act);
				Map<String, String> result = alipay.payV2(orderInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
}
