package cn.onestore.dao;

import cn.onestore.domain.Shop;

import java.sql.SQLException;

/**
 * Created by 明柯 on 2017/4/17.
 * 商店DAO层接口
 */
public interface ShopDao {

    Shop findShopByUid(Integer uid) throws SQLException;

    void addShop(Shop shop, Integer uid) throws SQLException;
}
