package cn.onestore.domain;

import java.util.List;

/**
 * Created by 明柯 on 2017/4/26.
 * 用户购物车实体类
 */
public class Shopcart {
    private Integer shopcartid;
    //购物车和用户是一对一关系
    private Integer uid;

    //product和购物车表是多对多的关系，所以有第三张表进行维护
    //这里可以通过第三张表来获取的购物车的该用户的所有商品
    private List<Product> products;

    private List<Scp> scps;

    public List<Scp> getScps() {
        return scps;
    }

    public void setScps(List<Scp> scps) {
        this.scps = scps;
    }



    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }





    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getShopcartid() {
        return shopcartid;
    }

    public void setShopcartid(Integer shopcartid) {
        this.shopcartid = shopcartid;
    }



}
