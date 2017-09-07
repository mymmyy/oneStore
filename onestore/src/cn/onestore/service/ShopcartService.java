package cn.onestore.service;

import cn.onestore.domain.Scp;
import cn.onestore.domain.Shopcart;
import cn.onestore.exception.AddShopcartException;
import cn.onestore.exception.FindException;
import cn.onestore.exception.UpdateException;

/**
 * Created by 明柯 on 2017/4/26.
 * shopcart的业务逻辑接口
 */
public interface ShopcartService {

    void addShopcart(Integer uid, Scp scp) throws AddShopcartException;//根据uid添加商品信息到购物车

    Shopcart findMyShopcartByUid(Integer uid) throws FindException;//把该用户的购物车中的商品信息查出来

    void deleteProductByUidAndPid(Integer uid, Integer pid) throws UpdateException;//根据商品pid和uid删除从购物车中删除商品
}
