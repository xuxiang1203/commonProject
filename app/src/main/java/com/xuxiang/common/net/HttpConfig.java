package com.xuxiang.common.net;

/**
 * @author
 * @describe
 */
public class HttpConfig {
    public static final int HTTP_SUCCESS = 200;
    public static final int HTTP_TIME = 15;
    //    public static String BASE_URL = "http://api.anoax.com/";
//    public static String BASE_URL = "http://47.96.88.206:10005/";
    public static String BASE_URL = "https://fenshui.deslibs.com";
//    public static String BASE_URL = "http://test.deslibs.com";
//    public static String BASE_URL = "http://192.168.101.28:10005/";

    public static String getURL() {
        return BASE_URL;
    }
}
