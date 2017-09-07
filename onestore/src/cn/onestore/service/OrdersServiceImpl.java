package cn.onestore.service;

import cn.onestore.dao.*;
import cn.onestore.domain.WrapOrders;
import cn.onestore.domain.WrapOrders_product;
import cn.onestore.exception.UpdateException;
import cn.onestore.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单的service层接口的一个实现类
 */
public class OrdersServiceImpl implements OrdersService{
    private OrdersDao ordersDao = new OrdersDaoImpl();//订单表的接口
    private Orders_productDao orders_productDao = new Orders_productDaoImpl();//订单项的接口
    private ProductDao productDao = new ProductDaoImpl();//商品表的接口
    private ShopcartDao shopcartDao = new ShopcartDaoImpl();//购物车的接口

    @Override //根据订单内容创建订单
    public void createOrders(WrapOrders wrapOrders) {
        //要做的事：根据订单内容在订单表中插入数据 ， 然后根据订单中的订单项（第三张表的内容）插入数据到第三张表
        //然后把购物车表中的数据删除，最后把商品表中的数量进行减去对应数量


        try {
            // 开启事务
            DataSourceUtils.startTransaction();

            //1.根据订单内容在订单表中插入数据
            ordersDao.addOrders(wrapOrders);
            //2.根据订单中的订单项（第三张表的内容）插入数据到第三张表
            orders_productDao.addOrders_productDao(wrapOrders);

            //3.然后把购物车表中的数据删除
            //3.1，先把用户对应的购物车id查出，避免数据库多次查询（需要删除多次）
            Integer shopcartId = shopcartDao.findShopcartidByUid(wrapOrders.getUid());
            shopcartDao.deleteProductByUidAndPid(wrapOrders,shopcartId);

            //4.商品表中的数量进行减去对应数量
            productDao.updateProductByPid(wrapOrders);
        } catch (SQLException e) {
            e.printStackTrace();

            //出了错就回滚
            try {
                DataSourceUtils.rollback();
                /*throw new UpdateException("出错");*/
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                DataSourceUtils.commitAndReleased(); // 事务提交与释放，因为一定要释放当前线程
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override //查询用户的订单
    public List<WrapOrders> findMyOrdersByUid(Integer uid) {
        List<WrapOrders> wrapOrdersList = null;

        //1.先查询订单基本信息
        try {
            wrapOrdersList = ordersDao.findOrdersByUid(uid);//根据用户id查询所有订单的基本信息

            for (WrapOrders wrapOrders: wrapOrdersList) {
                //循环查出该订单的所有商品
                List<WrapOrders_product> wrapOrders_productList = orders_productDao.findOrdersByOid(wrapOrders.getOid());

                wrapOrders.setOrders_productList(wrapOrders_productList);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wrapOrdersList;
    }

    @Override  //根据用户id和订单号进行模拟支付 修改支付状态
    public void payOrdersByOrdersInfo(WrapOrders wrapOrders) {
        try {
            ordersDao.updateOrdersPaystate(wrapOrders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override  //根据用户id和oid删除订单
    public void deleteUserOrdersByOid(WrapOrders wrapOrders) {
        //操作多表，开启事务
        // 开启事务
        try {
            DataSourceUtils.startTransaction();

            //1.删除订单项表中的数据（丁单和商品的第三张表）
            orders_productDao.deleteUserOrdersByOid(wrapOrders);

            //2.删除订单表中的数据
            ordersDao.deleteUserOrdersByOid(wrapOrders);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DataSourceUtils.rollback();//出了错回滚事务
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            //最后一定要释放当前线程，关闭事务
            try {
                DataSourceUtils.commitAndReleased();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
