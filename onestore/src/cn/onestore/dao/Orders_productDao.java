package cn.onestore.dao;

import cn.onestore.domain.WrapOrders;
import cn.onestore.domain.WrapOrders_product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单项（订单表和商品表的第三张表）的持久层接口
 */
public interface Orders_productDao {
    void addOrders_productDao(WrapOrders wrapOrders) throws SQLException;//根据订单中的订单项（第三张表的内容）插入数据到第三张表

    List<WrapOrders_product> findOrdersByOid(String oid) throws SQLException;//根据订单号查出该订单的所有商品

    void deleteUserOrdersByOid(WrapOrders wrapOrders) throws SQLException;//根据oid删除订单相关商品
}
