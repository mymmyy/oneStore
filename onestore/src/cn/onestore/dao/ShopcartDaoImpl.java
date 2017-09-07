package cn.onestore.dao;

import cn.onestore.domain.Scp;
import cn.onestore.domain.WrapOrders;
import cn.onestore.domain.WrapOrders_product;
import cn.onestore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/26.
 * Shopcart的Dao层接口的一个实现类
 */
public class ShopcartDaoImpl implements ShopcartDao{

    //根据用户id为用户创建一个购物车
    @Override
    public void createShopcartByUid(Integer uid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.插入
        String sql = "insert into shopcart values(null,?)";
        queryRunner.update(sql,uid);
    }

    @Override //根据uid添加商品信息到购物车
    public void addShopcart(Integer uid, Scp scp) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

        //2.创建语句。多表查询和插入  先查询出uid对应的shopcartid 再插入
        String sql = "INSERT INTO scp VALUES ((SELECT shopcartid FROM shopcart WHERE uid = ?),?,?,NULL)";
        //3.执行更新
        queryRunner.update(sql,uid,scp.getPid(),scp.getCount());

    }

    @Override  //根据uid查找scp表中是否存在该商品
    public Map<String,Object> findShopcartidFromScpByUid(Integer uid, Integer pid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建语句 多表查询
        String sql = "SELECT shopcartid,count FROM scp LEFT JOIN shopcart USING(shopcartid) WHERE uid=? AND pid=?";
        return queryRunner.query(sql,new MapHandler(),uid,pid);
    }

    @Override //根据购物车id和、pid来更改数量
    public void updateCountByShopcartidAndPid(Integer shopcartid, Scp scp) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.更新
        String sql = "update scp set count=? where shopcartid=? and pid=?";
        queryRunner.update(sql,scp.getCount(),shopcartid,scp.getPid());
    }

    @Override //根据uid查到第三张表所有相关信息
    public List<Scp> findAllScpByUid(Integer uid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql查询
        String sql = "SELECT shopcartid,pid,COUNT,updatetime FROM shopcart JOIN scp USING(shopcartid) WHERE uid = ?";
        return queryRunner.query(sql,new BeanListHandler<Scp>(Scp.class),uid);
    }

    @Override //根据商品pid和uid删除从购物车中删除商品
    public void deleteProductByUidAndPid(Integer uid, Integer pid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建 删除sql
        String sql = "delete from scp where shopcartid=(select shopcartid from shopcart where uid=?) and pid=?";
        queryRunner.update(sql,uid,pid);
    }

    @Override  //根据订单中的pid数组删除对应商品
    public void deleteProductByUidAndPid(WrapOrders wrapOrders,Integer shopcartId) throws SQLException {
        //1.把pid和shopcartId放到一个二元数组中
        List<WrapOrders_product> wrapOrders_productList = wrapOrders.getOrders_productList();
        Object[][] params = new Object[wrapOrders_productList.size()][2];
        WrapOrders_product wrapOrders_product = new WrapOrders_product();
        for(int i=0;i<wrapOrders_productList.size();i++){
            wrapOrders_product = wrapOrders_productList.get(i);

            params[i][0] = wrapOrders_product.getPid();
            params[i][1] = shopcartId;
        }

        //2.创建sql语句，并且根据QueryRunner无参构造方法创建对象
        String sql = "delete from scp where pid=? and shopcartid=?";
        QueryRunner queryRunner = new QueryRunner();

        //3.调用方法进行删除
        queryRunner.batch(DataSourceUtils.getConnection(),sql,params);

    }

    @Override  //根据用户查询购物车id  使用QuerRunner无参构造创建对象
    public Integer findShopcartidByUid(Integer uid) throws SQLException {
        //1.创建对象--通过无参构方法
        QueryRunner queryRunner = new QueryRunner();
        //2.创建sql语句
        String sql = "select shopcartid from shopcart where uid=?";


        return (Integer) queryRunner.query(DataSourceUtils.getConnection(),sql,new ScalarHandler(),uid);
    }
}
