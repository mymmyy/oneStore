package cn.onestore.service;

import cn.onestore.domain.ReceiverAddress;
import cn.onestore.domain.User;
import cn.onestore.domain.WrapUser;
import cn.onestore.exception.ActiveUserException;
import cn.onestore.exception.FindException;
import cn.onestore.exception.LoginException;
import cn.onestore.exception.RegisterException;

import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/12.
 */
public interface UserServiceDao {
    void register(User user) throws RegisterException;

    void activeuser(String activecode) throws ActiveUserException, RegisterException;

    WrapUser login(WrapUser u)throws LoginException;

    WrapUser findUserByUid(Integer uid) throws FindException;

    void updateUserByPassword(WrapUser u, String newPassword) throws FindException;

    List<Map<String, Object>> findReceiverAddressByUid(Integer uid);//查询用户的所有收货地址
}
