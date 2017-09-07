package cn.onestore.dao;

import cn.onestore.domain.User;
import cn.onestore.domain.WrapUser;
import cn.onestore.utils.DataSourceUtils;
import cn.onestore.utils.MD5Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/12.
 * 使用DBUtiles框架对持久层进行操作
 *
 */
public class UserDaoImpl implements UserDao{

    @Override
    public void addUser(User user) throws SQLException {
        //1.创建QueryRunner  需要一个数据源（通过工具类获得）
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行添加  //最后两条 目前没有做处理：头像地址、电话
        String sql = "insert into user values(null,?,?,?,?,'general','general','0',?,null,null,null)";
        queryRunner.update(sql,user.getUsername(), MD5Utils.md5(user.getPassword()),user.getNickname(),
                user.getEmail(),user.getActivecode());

    }

    @Override  //根据激活码查找用户  返回uid
    public User findUserByActivecode(String activecode) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行查找
        String sql = "select * from user where activecode = ?";
        queryRunner.query(sql,new BeanHandler<User>(User.class),activecode);
        return queryRunner.query(sql,new BeanHandler<User>(User.class),activecode);
    }

    @Override //根据激活码修改状态
    public void activeUserByActivecode(String activecode) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行修改
        String sql = "update user set state=1 where activecode=?";
        queryRunner.update(sql,activecode);
    }

    @Override  //验证登陆
    public WrapUser findUserByUsernameAndPassword(WrapUser u) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行查找
        String sql = "select * from user where username = ? and password=?";
        //先把密码通过加密进行比对，因为数据库中存放的是加密的密码字符串
        return queryRunner.query(sql,new BeanHandler<WrapUser>(WrapUser.class),u.getUsername(),MD5Utils.md5(u.getPassword()));
    }

    @Override //根据id查找
    public WrapUser findUserByUid(Integer uid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行查找
        String sql = "select * from user where uid=?";
        //先把密码通过加密进行比对，因为数据库中存放的是加密的密码字符串
        return queryRunner.query(sql,new BeanHandler<WrapUser>(WrapUser.class),uid);
    }

    @Override//根据用户名和密码更新
    public void updateUserByPassword(WrapUser u) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行查找
        String sql = "update user set nickname=?,email=?,password=? where uid=?";
        //先把密码通过加密进行比对，因为数据库中存放的是加密的密码字符串
        queryRunner.update(sql,u.getNickname(),u.getEmail(),MD5Utils.md5(u.getPassword()),u.getUid());
    }

    @Override //查询用户的所有收货地址
    public List<Map<String, Object>> findReceiverAddressByUid(Integer uid) throws SQLException {
        //1.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.执行查找
        String sql = "select receiverinfo from receiver_address where uid=?";

        return queryRunner.query(sql,new MapListHandler(),uid);
    }
}
