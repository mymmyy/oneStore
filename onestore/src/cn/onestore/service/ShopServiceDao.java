package cn.onestore.service;

import cn.onestore.domain.Shop;
import cn.onestore.exception.CreateShopException;
import cn.onestore.exception.FindException;

/**
 * Created by 明柯 on 2017/4/17.
 * Shop业务逻辑接口
 */
public interface ShopServiceDao {
    Shop findShopByUid(Integer uid);

    void createShop(Shop shop, Integer uid) throws CreateShopException;
}
