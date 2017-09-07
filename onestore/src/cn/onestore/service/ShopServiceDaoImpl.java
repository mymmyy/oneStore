package cn.onestore.service;

import cn.onestore.dao.ShopDao;
import cn.onestore.dao.ShopDaoImpl;
import cn.onestore.dao.UserDao;
import cn.onestore.dao.UserDaoImpl;
import cn.onestore.domain.Shop;
import cn.onestore.domain.User;
import cn.onestore.exception.CreateShopException;
import cn.onestore.exception.FindException;

import java.sql.SQLException;

/**
 * Created by 明柯 on 2017/4/17.
 * 商店业务逻辑接口的实现类
 */
public class ShopServiceDaoImpl implements ShopServiceDao{
    private ShopDao shopDao = new ShopDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    @Override
    public Shop findShopByUid(Integer uid) {
        //这里可以返回null
        //shopDao.findShopByUid(uid);
        Shop shop = null;
        try {
            shop = shopDao.findShopByUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    @Override  //创建店铺
    public void createShop(Shop shop, Integer uid) throws CreateShopException {
        try {
            //先查找用户是否存在
            User user = userDao.findUserByUid(uid);
            if(user == null){
               throw new CreateShopException("用户不存在");
            }
            //再查看该用户的店铺是否已经存在
            if(shopDao.findShopByUid(uid) != null){
                throw new CreateShopException("店铺已经存在");

            }
            shopDao.addShop(shop,uid);
        }catch (SQLException e){
            e.printStackTrace();
            throw new CreateShopException("未知错误 稍后再试！");
        }
    }
}
