package cn.onestore.domain;

/**
 * Created by 明柯 on 2017/4/17.
 * 商店实体类
 */
public class Shop {
    private Integer shopid;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    private String shopname;
}
