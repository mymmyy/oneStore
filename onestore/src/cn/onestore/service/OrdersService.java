package cn.onestore.service;

import cn.onestore.domain.WrapOrders;
import cn.onestore.exception.UpdateException;

import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单的service层接口
 */
public interface OrdersService {
    void createOrders(WrapOrders wrapOrders) ;//根据订单内容创建订单

    List<WrapOrders> findMyOrdersByUid(Integer uid);//查询用户的订单

    void payOrdersByOrdersInfo(WrapOrders wrapOrders);//根据用户id和订单号进行模拟支付 修改支付状态

    void deleteUserOrdersByOid(WrapOrders wrapOrders); //根据用户id和oid删除订单
}
