package cn.onestore.dao;

import cn.onestore.domain.WrapOrders;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单表的持久层接口
 */
public interface OrdersDao {
    void addOrders(WrapOrders wrapOrders) throws SQLException;//1.根据订单内容在订单表中插入数据

    List<WrapOrders> findOrdersByUid(Integer uid) throws SQLException;//根据用户id查询订单基本信息

    void updateOrdersPaystate(WrapOrders wrapOrders) throws SQLException;//根据用户id和订单号进行模拟支付 修改支付状态

    void deleteUserOrdersByOid(WrapOrders wrapOrders) throws SQLException;//根据用户id和oid删除订单
}
