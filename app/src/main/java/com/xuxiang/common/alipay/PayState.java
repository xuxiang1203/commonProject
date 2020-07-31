package com.xuxiang.common.alipay;

public class PayState {

    /**
     * code : 4028e59c71dd90b70171ddbeffb20022
     * create_time : 2020-05-04T03:33:34.000+0000
     * addftime : 6分钟前
     * addtime : 2020-05-04 11:33:34
     * orderno : 658603811494785373
     * paytype : ali
     * goodscode : NEEDS_URGENT
     * price : 0.01
     * status : 0
     */

    private String code;
    private String create_time;
    private String addftime;
    private String addtime;
    private String orderno;
    private String paytype;
    private String goodscode;
    private String price;
    private int status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAddftime() {
        return addftime;
    }

    public void setAddftime(String addftime) {
        this.addftime = addftime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
