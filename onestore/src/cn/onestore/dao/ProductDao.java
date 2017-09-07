package cn.onestore.dao;

import cn.onestore.domain.Category;
import cn.onestore.domain.Product;
import cn.onestore.domain.Scp;
import cn.onestore.domain.WrapOrders;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/19.
 * 商品的dao层接口
 */
public interface ProductDao {

    List<Product> findAllProductByShopid(Integer shopid) throws SQLException;

    void addProduct(Product product, Integer shopid) throws SQLException;

    Product findProductByPid(Integer pid) throws SQLException;

    Category findAllCategory() throws SQLException;

    void updateProductByPid(Product product) throws SQLException;

    List<Product> findProductByPname(String pname) throws SQLException;

    List<Product> findProductByPname(String pname,Integer currentPage) throws SQLException;

    List<Product> findProductByCategory(String category, Integer currentPage) throws SQLException;

    List<Object[]> findAllPid() throws SQLException;

    List<Product> findAllProduct() throws SQLException;

    List<Product> findProductByPidArray(Integer[] nums) throws SQLException;

    void updateAdstate(Integer pid, Integer adstate) throws SQLException;

    void deleteproductByPid(Integer pid) throws SQLException;

    List<Map<String, Object>> findAllTextAd(Integer adstate) throws SQLException;

    List<Map<String,Object>> findAllPictureAd(Integer adstate) throws SQLException;

    Map<String,Object> findProductCountByCategory(String category) throws SQLException;

    Map<String,Object> findProductCountByPname(String pname) throws SQLException;

    Integer findShopidBypid(Integer pid) throws SQLException;

    List<Product> findProductByPids(List<Scp> scps) throws SQLException;//根据一组pid查询对应商品

    void updateProductByPid(WrapOrders wrapOrders) throws SQLException;//根据pid和count把商品进行更新数量

    List<Product> findProductByRandomPids(List<Object[]> pidLists) throws SQLException;//根据一组pid在pruduct中查找对应商品
}
