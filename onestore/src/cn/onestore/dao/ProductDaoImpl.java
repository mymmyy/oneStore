package cn.onestore.dao;

import cn.onestore.domain.*;
import cn.onestore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/19.
 * 商品的dao层接口的实现类
 */
public class ProductDaoImpl implements ProductDao{

    @Override  //查找所有商品根据商家id
    public List<Product> findAllProductByShopid(Integer shopid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product where shopid=? order by pupdatetime desc";
        //3.执行查找
        List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class),shopid);
        return list;
    }

    @Override //添加商品
    public void addProduct(Product product, Integer shopid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "insert into product values(null,?,?,?,?,?,?,?,null,0)";
        //3.执行插入
        queryRunner.update(sql,product.getPname(),product.getPrice(),product.getCategory(),product.getPnum(),
                product.getImgurl(),product.getDescription(),shopid);
    }

    @Override//根据pid查找
    public Product findProductByPid(Integer pid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product where pid=?";
        return queryRunner.query(sql,new BeanHandler<Product>(Product.class),pid);
    }

    @Override  //查找所有类别
    public Category findAllCategory() throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from category";
        return queryRunner.query(sql,new BeanHandler<Category>(Category.class));
    }

    @Override //根据pid修改商品信息
    public void updateProductByPid(Product product) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "update product set pname=?,price=?,pnum=?,description=? where pid=?";
        //3.执行更新
        queryRunner.update(sql,product.getPname(),product.getPrice(),product.getPnum(),product.getDescription(),product.getPid());
    }

    @Override //根据pname模糊查找
    public List<Product> findProductByPname(String pname) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product where pname like '%"+pname+"%' order by pupdatetime desc";
        List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
        return list;
    }

    @Override
    public List<Product> findProductByPname(String pname, Integer currentPage) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product where pname like '%"+pname+"%' order by pupdatetime desc limit ?,4";
        List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class),(currentPage-1)*4);
        return list;
    }

    @Override//根据分类查找商品
    public List<Product> findProductByCategory(String category, Integer currentPage) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product where category=? order by pupdatetime desc limit ?,4";
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class),category,(currentPage-1)*4);
    }

    @Override //查询所有pid
    public List<Object[]> findAllPid() throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select pid from product";

        return queryRunner.query(sql,new ArrayListHandler());
    }

    @Override //查找所有的product
    public List<Product> findAllProduct() throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product order by pupdatetime desc";
        //3.执行查找
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findProductByPidArray(Integer[] nums) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select * from product where pid in (?,?,?,?,?,?,?,?)";
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class),
                nums[0],nums[1],nums[2],nums[3],nums[4],nums[5],nums[6],nums[7]);
    }

    @Override  //根据pid更新adstate
    public void updateAdstate(Integer pid, Integer adstate) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "update product set adstate=? where pid=?";
        queryRunner.update(sql,adstate,pid);
    }

    @Override //删除商品根据pid
    public void deleteproductByPid(Integer pid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "delete from product where pid=?";

        queryRunner.update(sql,pid);
    }

    @Override //获取所有adstate为1文本信息
    public List<Map<String, Object>> findAllTextAd(Integer adstate) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select pid,description from product where adstate=? limit 10";
        return queryRunner.query(sql,new MapListHandler(),adstate);
    }

    @Override
    public List<Map<String, Object>> findAllPictureAd(Integer adstate) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "select pid,imgurl from product where adstate=? limit 5";
        return queryRunner.query(sql,new MapListHandler(),adstate);
    }

    @Override  //根据分类查到该分类的总数
    public Map<String, Object> findProductCountByCategory(String category) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "SELECT COUNT(*) AS count FROM product WHERE category=?";
        return queryRunner.query(sql,new MapHandler(),category);
    }

    @Override  //根据部分名称查到总数
    public Map<String, Object> findProductCountByPname(String pname) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "SELECT COUNT(*) AS count FROM product WHERE pname=?";
        return queryRunner.query(sql,new MapHandler(),pname);
    }

    @Override  //根据pid查找shopid
    public Integer findShopidBypid(Integer pid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句
        String sql = "SELECT shopid FROM product WHERE pid=?";
        return (Integer) queryRunner.query(sql, new ScalarHandler(),pid);
    }

    @Override //根据一组pid查询对应商品
    public List<Product> findProductByPids(List<Scp> scps) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句  这里采用拼接字符串的方式得到sql
        //2.1首先判断参数是否有值
        if(scps.size() <= 0){
            return null;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM product WHERE pid in (");

        for (Scp scp:scps) {
            sql.append(scp.getPid()).append(",");
        }

        //最后把多出来的 , 替换成 )
        sql.replace(sql.length()-1,sql.length(),")");
        //把StringBuffer转换成String
        //System.out.println(sql.toString());

        return queryRunner.query(sql.toString(),new BeanListHandler<Product>(Product.class));
    }

    @Override //根据pid和count把商品进行更新数量
    public void updateProductByPid(WrapOrders wrapOrders) throws SQLException {
        //1.把pid和数量count放到一个二元数组中作为参数
        List<WrapOrders_product> wrapOrders_productList = wrapOrders.getOrders_productList();
        Object[][] params = new Object[wrapOrders_productList.size()][2];

        WrapOrders_product wrapOrders_product = new WrapOrders_product();
        for(int i = 0;i<wrapOrders_productList.size();i++){
            wrapOrders_product = wrapOrders_productList.get(i);

            params[i][0] = wrapOrders_product.getCount();
            params[i][1] = wrapOrders_product.getPid();
        }

        //2.创建sql语句，并且根据QueryRunner无参构造方法创建对象
        String sql = "update product set pnum=pnum-? where pid=?";
        QueryRunner queryRunner = new QueryRunner();

        //3.调用方法进行更新
        queryRunner.batch(DataSourceUtils.getConnection(),sql,params);

    }

    @Override //根据一组pid在pruduct中查找对应商品 --随机查
    public List<Product> findProductByRandomPids(List<Object[]> pidLists) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.创建sql语句  这里采用拼接字符串的方式得到sql
        //2.1首先判断参数是否有值，否则这样拼接会出错
        if(pidLists.size() <= 0){
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM product WHERE pid in (");

        for (Object[] obj:pidLists) {
            sql.append((int)obj[0]).append(",");
        }

        //最后把多出来的 , 替换成 )
        sql.replace(sql.length()-1,sql.length(),")");
        //把StringBuffer转换成String
        //System.out.println(sql.toString());

        return queryRunner.query(sql.toString(),new BeanListHandler<Product>(Product.class));
    }


}
