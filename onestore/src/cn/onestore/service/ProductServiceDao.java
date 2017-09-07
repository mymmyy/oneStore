package cn.onestore.service;

import cn.onestore.domain.Category;
import cn.onestore.domain.Product;
import cn.onestore.exception.FindException;
import cn.onestore.exception.UpdateException;
import cn.onestore.exception.UploadException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/19.
 * 商品的service层接口
 */
public interface ProductServiceDao {

    List<Product> findAllProductByShopid(Integer shopid) throws FindException;

    void addProduct(Product product, Integer shopid) throws UploadException;

    Product findProductByPid(Integer pid) throws FindException;

    Category findAllCategory() throws FindException;

    void updateProductByPid(Product product) throws UpdateException;

    List<Product> findProductByPname(String pname) throws FindException;

    List<Product> findProductByPname(String pname,Integer currentPage) throws FindException;

    List<Product> findProductByCategory(String category, Integer currentPage) throws FindException;

    List<Product> findProductRandom();

    void updateAdstate(Integer pid, Integer adstate) throws UpdateException;

    void deleteproductByPid(Integer pid) throws UpdateException;

    List<Map<String, Object>> findAllTextAd(Integer adstate);

    List<Map<String,Object>> findAllPictureAd(Integer adstate);

    Map<String,Object> findProductCountByCategory(String category);

    Map<String,Object> findProductCountByPname(String pname);

    Integer findShopidBypid(Integer pid) throws FindException;

    List<Object[]> findAllPid() throws FindException;//查到所有的pid

    List<Product> findProductByRandomPids(List<Object[]> pidLists) throws SQLException;//根据pids list查询对应得商品
}
