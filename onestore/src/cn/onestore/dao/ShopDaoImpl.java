package cn.onestore.dao;

import cn.onestore.domain.Shop;
import cn.onestore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Created by 明柯 on 2017/4/17.
 * 商店DAO接口的实现类
 */
public class ShopDaoImpl implements ShopDao{

    @Override
    public Shop findShopByUid(Integer uid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行查找
        String sql = "select * from shop where uid=?";
        //先把密码通过加密进行比对，因为数据库中存放的是加密的密码字符串
        return queryRunner.query(sql,new BeanHandler<Shop>(Shop.class),uid);
    }

    @Override //创建店铺
    public void addShop(Shop shop, Integer uid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行插入
        String sql = "insert into shop values (null,?,?)";
        queryRunner.update(sql,shop.getShopname(),uid);
    }
}
