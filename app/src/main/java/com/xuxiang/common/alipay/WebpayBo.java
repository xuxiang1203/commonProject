package com.xuxiang.common.alipay;

import java.io.Serializable;

public class WebpayBo implements Serializable {

    /**
     * body (string, optional): 商品描述，可空 ,
     * out_trade_no (string, optional): 商户订单号，商户网站订单系统中唯一订单号，必填 ,
     * product_code (string, optional): 销售产品码 必填 ,
     * subject (string, optional): 订单名称，必填 ,
     * timeout_express (string, optional): 超时时间 可空 ,
     * total_amount (string, optional): 付款金额，必填
     * "weChatPayType": JSAPI, NATIVE, APP, MWEB
     */

    private String body;
    private String out_trade_no;
    private String product_code;
    private String subject;
    private String timeout_express;
    private String total_amount;
    private String weChatPayType;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getWeChatPayType() {
        return weChatPayType;
    }

    public void setWeChatPayType(String weChatPayType) {
        this.weChatPayType = weChatPayType;
    }
}
