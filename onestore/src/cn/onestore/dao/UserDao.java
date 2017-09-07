package cn.onestore.dao;

import cn.onestore.domain.User;
import cn.onestore.domain.WrapUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/12.
 */
public interface UserDao {
    //添加用户
    void addUser(User user) throws SQLException;

    User findUserByActivecode(String activecode) throws SQLException;

    void activeUserByActivecode(String activecode) throws SQLException;

    WrapUser findUserByUsernameAndPassword(WrapUser u) throws SQLException;

    WrapUser findUserByUid(Integer uid) throws SQLException;

    void updateUserByPassword(WrapUser u) throws SQLException;

    List<Map<String, Object>> findReceiverAddressByUid(Integer uid) throws SQLException;//查询用户的所有收货地址
}
