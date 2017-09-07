package cn.onestore.dao;

import cn.onestore.domain.WrapOrders;
import cn.onestore.domain.WrapOrders_product;
import cn.onestore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单项（订单表和商品表的第三张表）的持久层接口的一个实现类
 */
public class Orders_productDaoImpl implements Orders_productDao{

    //根据订单中的订单项（第三张表的内容）插入数据到第三张表
    @Override
    public void addOrders_productDao(WrapOrders wrapOrders) throws SQLException {
        //1.获得订单项（第三张表的内容）
        List<WrapOrders_product> wrapOrders_productList = wrapOrders.getOrders_productList();
        //2.因为list集合中由多条数据且非一列  ，所以使用二元数组来拼装参数，最后直接传二原数组作为参数进行插入
        Object[][] params = new Object[wrapOrders_productList.size()][6];//已知有6列

        WrapOrders_product wrapOrders_product = new WrapOrders_product();
        for (int i = 0; i < wrapOrders_productList.size(); i++) {

            wrapOrders_product = wrapOrders_productList.get(i);

            params[i][0] = wrapOrders_product.getOid();
            params[i][1] = wrapOrders_product.getPid();
            params[i][2] = wrapOrders_product.getCount();
            params[i][3] = wrapOrders_product.getImgurl();
            params[i][4] = wrapOrders_product.getPname();
            params[i][5] = wrapOrders_product.getPrice();
        }

        //3.同样创建sql，通过无参构造方法创建QueryRunner对象
        String sql = "insert into orders_product values (?,?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.batch(DataSourceUtils.getConnection(),sql,params);

    }

    @Override //根据订单号查出该订单的所有商品
    public List<WrapOrders_product> findOrdersByOid(String oid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from orders_product where oid = ?";
        return queryRunner.query(sql,new BeanListHandler<WrapOrders_product>(WrapOrders_product.class),oid);
    }

    @Override //根据oid删除订单相关商品
    public void deleteUserOrdersByOid(WrapOrders wrapOrders) throws SQLException {
        //创建sql，通过无参构造方法创建QueryRunner对象
        String sql = "delete from orders_product where oid = ?";
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(DataSourceUtils.getConnection(),sql,wrapOrders.getOid());
    }
}
