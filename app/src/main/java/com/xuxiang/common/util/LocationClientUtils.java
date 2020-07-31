package com.xuxiang.common.util;


import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.xuxiang.common.app.ComApplication;

/**
 * @describe 定位服务
 */
public class LocationClientUtils {
    private AMapLocationListener locationListener;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    public LocationClientUtils(AMapLocationListener locationListener) {
        this.locationListener = locationListener;
        init();
    }

    private void init() {
        mLocationClient = new AMapLocationClient(ComApplication.getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(locationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

    }

    public void start(){
        //启动定位
        mLocationClient.startLocation();
    }

    public void stop(){
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    public void setLocationListener(AMapLocationListener locationListener) {
        this.locationListener = locationListener;
        mLocationClient.setLocationListener(locationListener);
    }
}
