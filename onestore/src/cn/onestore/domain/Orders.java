package cn.onestore.domain;

import java.util.Date;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单表对应的实体类
 */
public class Orders {


    private String oid;
    private double ordermoney;
    private String receiverinfo;
    private Integer paystate;
    private Date ordertime;
    private String remark;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }



    public double getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(double ordermoney) {
        this.ordermoney = ordermoney;
    }

    public String getReceiverinfo() {
        return receiverinfo;
    }

    public void setReceiverinfo(String receiverinfo) {
        this.receiverinfo = receiverinfo;
    }

    public Integer getPaystate() {
        return paystate;
    }

    public void setPaystate(Integer paystate) {
        this.paystate = paystate;
    }



}
