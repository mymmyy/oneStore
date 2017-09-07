package cn.onestore.domain;

import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.
 * Orders实体类的包裹类 ---》扩展类
 */
public class WrapOrders extends Orders{
    private Integer uid;
    private List<WrapOrders_product> orders_productList;

    public List<WrapOrders_product> getOrders_productList() {
        return orders_productList;
    }

    public void setOrders_productList(List<WrapOrders_product> orders_productList) {
        this.orders_productList = orders_productList;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }



}
