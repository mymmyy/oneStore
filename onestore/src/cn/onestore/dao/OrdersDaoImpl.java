package cn.onestore.dao;

import cn.onestore.domain.WrapOrders;
import cn.onestore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.\
 * 订单表的持久层接口的一个实现类
 */
public class OrdersDaoImpl implements OrdersDao{

    @Override //1.根据订单内容在订单表中插入数据
    public void addOrders(WrapOrders wrapOrders) throws SQLException {
        //1.创建sql
        String sql = "insert into orders values(?,?,?,0,null,?,?)";
        //2.使用不带参的构造方法创建queryRunner对象
        QueryRunner queryRunner = new QueryRunner();

        queryRunner.update(DataSourceUtils.getConnection(),sql,wrapOrders.getOid(),
                wrapOrders.getOrdermoney(),wrapOrders.getReceiverinfo(),wrapOrders.getUid(),wrapOrders.getRemark());//此时需要传入链接
    }

    @Override  //根据用户id查询订单基本信息
    public List<WrapOrders> findOrdersByUid(Integer uid) throws SQLException {
        //1.创建QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from orders where uid=?";
        return queryRunner.query(sql,new BeanListHandler<WrapOrders>(WrapOrders.class),uid);
    }

    @Override //根据用户id和订单号进行模拟支付 修改支付状态
    public void updateOrdersPaystate(WrapOrders wrapOrders) throws SQLException {
        //1.创建QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "update orders set paystate=1 where uid=? and oid=?";
        queryRunner.update(sql,wrapOrders.getUid(),wrapOrders.getOid());
    }

    @Override//根据用户id和oid删除订单
    public void deleteUserOrdersByOid(WrapOrders wrapOrders) throws SQLException {
        //1.创建sql
        String sql = "delete from orders where uid=? and oid=?";
        //2.使用不带参的构造方法创建queryRunner对象
        QueryRunner queryRunner = new QueryRunner();

        //3.调用方法进行删除
        queryRunner.update(DataSourceUtils.getConnection(),sql,wrapOrders.getUid(),wrapOrders.getOid());
    }
}
