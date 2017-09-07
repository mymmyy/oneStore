package cn.onestore.service;

import cn.onestore.dao.ProductDao;
import cn.onestore.dao.ProductDaoImpl;
import cn.onestore.domain.Category;
import cn.onestore.domain.Product;
import cn.onestore.exception.FindException;
import cn.onestore.exception.UpdateException;
import cn.onestore.exception.UploadException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 明柯 on 2017/4/19.
 * 商品的service层接口的实现类
 */
public class ProductServiceDaoImpl implements ProductServiceDao{
    private ProductDao productDao = new ProductDaoImpl();

    @Override //查找所有商品
    public List<Product> findAllProductByShopid(Integer shopid) throws FindException {
        List<Product> list = null;
        try {
            list = productDao.findAllProductByShopid(shopid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("未知错误 请返回主页稍后再试");
        }
        return list;
    }

    @Override //添加商品
    public void addProduct(Product product, Integer shopid) throws UploadException {
        try {
            productDao.addProduct(product,shopid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UploadException("未知错误 上传失败 请稍后再试");
        }
    }

    @Override  //根据pid查找
    public Product findProductByPid(Integer pid) throws FindException {
        Product product = null;
        try {
            product = productDao.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("未知错误 请稍后再试");
        }
        return product;
    }

    @Override //查出所有的category
    public Category findAllCategory() throws FindException {
        Category category = null;
        try {
            category = productDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("未知错误 请稍后再试");
        }
        return category;
    }

    @Override  //根据pid修改商品信息
    public void updateProductByPid(Product product) throws UpdateException {
        try {
            productDao.updateProductByPid(product);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UpdateException("未知错误 更新失败 请稍后再试");
        }
    }

    @Override //根据pname 模糊查找
    public List<Product> findProductByPname(String pname) throws FindException {
        List<Product> list = null;
        try {
            list = productDao.findProductByPname(pname);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new FindException("未知错误 请稍后再试");
        }
        return list;
    }

    @Override
    public List<Product> findProductByPname(String pname, Integer currentPage) throws FindException {
        List<Product> list = null;
        try {
            list = productDao.findProductByPname(pname,currentPage);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new FindException("未知错误 请稍后再试");
        }
        return list;
    }

    @Override//根据分类查找商品
    public List<Product> findProductByCategory(String category, Integer currentPage) throws FindException {
        List<Product> list = null;
        try {
            list = productDao.findProductByCategory(category,currentPage);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new FindException("未知错误 请稍后再试");
        }
        return list;
    }

    @Override //随机查询出最多八个
    public List<Product> findProductRandom() {
        //要做的事：
        //1.获得product表的所有id号存入一个数组
        //2.判断数组长度是否大于8，大于8则产生八个随机数挑出数组中的不重复的八个值；不大于则全部取出
        //3.大于则根据取出的八个id查询数据库把值挑出
        //3.不大于，直接全部取出
        List<Object[]> ids = null;
        List<Product> list = null;
        try {
            ids = productDao.findAllPid();
            if(ids.size() <= 8){
                //说明可以全部取出
                list = productDao.findAllProduct();
            }else {
                //说明大于8个，随机挑出8个
                //得出8个随机不同得数方法很多
                Integer[] nums = new Integer[8];
                Random random = new Random();
                int ran;
                for(int i = 0;i< 8;i++){
                    ran = random.nextInt(ids.size());
                    nums[i] = (Integer) ids.get(ran)[0];
                    ids.remove(ran);
                }

                //System.out.println(nums);
                list = productDao.findProductByPidArray(nums);
                //return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;//出了错返回null
        }
        return list;
    }

    @Override //根据pid更新adstate
    public void updateAdstate(Integer pid, Integer adstate) throws UpdateException {
        try {
            productDao.updateAdstate(pid,adstate);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UpdateException("更新广告请求失败 稍后再试");
        }
    }

    @Override //删除商品根据pid
    public void deleteproductByPid(Integer pid) throws UpdateException {
        try {
            productDao.deleteproductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UpdateException("删除失败 稍后再试");
        }
    }

    @Override //获取所有adstate为1文本信息
    public List<Map<String, Object>> findAllTextAd(Integer adstate) {
        //进行数据处理，把List<Map<String,Object>>放到
        List<Map<String,Object>> list = null;
        try {
            list =productDao.findAllTextAd(adstate);
        } catch (SQLException e) {
            e.printStackTrace();
            //有异常打印以便处理
        }

        return list;
    }

    @Override //获取所有adstate为2图片信息
    public List<Map<String, Object>> findAllPictureAd(Integer adstate) {
        List<Map<String,Object>> list = null;
        try {
            list =productDao.findAllPictureAd(adstate);
        } catch (SQLException e) {
            e.printStackTrace();
            //有异常打印以便处理
        }

        return list;
    }

    @Override  //根据分类查到该分类的总数
    public Map<String, Object> findProductCountByCategory(String category) {
        Map<String, Object> map = null;
        try {
            map = productDao.findProductCountByCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override  //根据部分名称查到总数
    public Map<String, Object> findProductCountByPname(String pname) {
        Map<String, Object> map = null;
        try {
            map = productDao.findProductCountByPname(pname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override  //根据pid查找shopid
    public Integer findShopidBypid(Integer pid) throws FindException {
        Integer shopid = null;
        try {
            shopid = productDao.findShopidBypid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("未知错误 稍后再试");
        }
        return shopid;
    }

    @Override  //查到所有的pid
    public List<Object[]> findAllPid() throws FindException {
        List<Object[]> pidList = null;
        try {
            pidList = productDao.findAllPid();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("未知错误 稍后再试");
        }
        return pidList;
    }

    @Override
    public List<Product> findProductByRandomPids(List<Object[]> pidLists) throws SQLException {
        List<Product> productList = null;
        productList = productDao.findProductByRandomPids(pidLists);//错误让servlet处理


        return productList;
    }

}
