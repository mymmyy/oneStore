package cn.onestore.service;

import cn.onestore.dao.ProductDao;
import cn.onestore.dao.ProductDaoImpl;
import cn.onestore.dao.ShopcartDao;
import cn.onestore.dao.ShopcartDaoImpl;
import cn.onestore.domain.Product;
import cn.onestore.domain.Scp;
import cn.onestore.domain.Shopcart;
import cn.onestore.exception.AddShopcartException;
import cn.onestore.exception.FindException;
import cn.onestore.exception.UpdateException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/26.
 * shopcart的业务逻辑接口的实现类
 */
public class ShopcartServiceImpl implements ShopcartService{

    private ShopcartDao shopcartDao = new ShopcartDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    //根据uid添加商品信息到购物车
    @Override
    public void addShopcart(Integer uid, Scp scp) throws AddShopcartException {
        //调用方法进行添加。这里为了防止重复插入，即如果购物车内（第三张表内该用户已经存在该id的商品，就不用再插入新数据，而是更新数量）

        //1.先查看第三张表中该用户是否有该商品,有则把数量和shopcartid查出来
        Map<String,Object> map = null;
        try {
            map = shopcartDao.findShopcartidFromScpByUid(uid,scp.getPid());

            if(map != null){
                //说明该商品存在,直接更新数量即可
                scp.setCount(scp.getCount()+(Integer)map.get("count"));
                shopcartDao.updateCountByShopcartidAndPid((Integer) map.get("shopcartid"),scp);

            }else{
                shopcartDao.addShopcart(uid,scp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new AddShopcartException("添加购物车失败");
        }
    }

    @Override //把该用户的购物车中的商品信息查出来
    public Shopcart findMyShopcartByUid(Integer uid) throws FindException {
        //逻辑：需要查到所有商品（先查到第三张表所有信息，然后根据pid集合查）、第三张表所有信息（根据uid查）
        //1.查得第三张表所有信息
        List<Scp> scps = null;
        List<Product> products = null;
        Shopcart shopcart = new Shopcart();
        try {
            scps = shopcartDao.findAllScpByUid(uid);

            //2.根据scps中得所有pid查到所有商品
            products = productDao.findProductByPids(scps);

            //最后把相应项目放到需要返回的对象中

            shopcart.setScps(scps);
            shopcart.setProducts(products);
            shopcart.setUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("没有更多商品");
        }

        return shopcart;
    }

    @Override //根据商品pid和uid删除从购物车中删除商品
    public void deleteProductByUidAndPid(Integer uid, Integer pid) throws UpdateException {
        try {
            shopcartDao.deleteProductByUidAndPid(uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UpdateException("未知错误 删除购物车商品失败");
        }
    }

}
