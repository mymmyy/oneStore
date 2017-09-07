package cn.onestore.dao;

import cn.onestore.domain.Scp;
import cn.onestore.domain.WrapOrders;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/26.
 * Shopcart的Dao层接口
 */
public interface ShopcartDao {
    void createShopcartByUid(Integer uid) throws SQLException;//根据用户id为用户创建一个购物车

    void addShopcart(Integer uid, Scp scp) throws SQLException;//根据uid添加商品信息到购物车

    Map<String,Object> findShopcartidFromScpByUid(Integer uid, Integer pid) throws SQLException;//根据uid查找scp表中是否存在该商品

    void updateCountByShopcartidAndPid(Integer shopcartid, Scp scp) throws SQLException;//根据购物车id和、pid来更改数量

    List<Scp> findAllScpByUid(Integer uid) throws SQLException;//根据uid查到第三张表所有相关信息

    void deleteProductByUidAndPid(Integer uid, Integer pid) throws SQLException;//根据商品pid和uid删除从购物车中删除商品

    void deleteProductByUidAndPid(WrapOrders wrapOrders, Integer shopcartId) throws SQLException;//根据订单中的pid数组删除对应商品

    Integer findShopcartidByUid(Integer uid) throws SQLException;//根据用户查询购物车id
}
