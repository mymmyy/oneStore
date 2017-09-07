package cn.onestore.domain;

import java.util.Date;

/**
 * Created by 明柯 on 2017/4/19.
 * 所有商品的domain基类
 */
public class Product {
    private Integer pid;
    private String pname;
    private double price;
    private String category;
    private Integer pnum;
    private String imgurl;
    private String description;
    private Date updatetime;//更新时间，一个时间戳
    private Integer adstate;//广告状态

    public Integer getAdstate() {
        return adstate;
    }

    public void setAdstate(Integer adstate) {
        this.adstate = adstate;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPnum() {
        return pnum;
    }

    public void setPnum(Integer pnum) {
        this.pnum = pnum;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
