package cn.onestore.service;


import cn.onestore.dao.ShopcartDao;
import cn.onestore.dao.ShopcartDaoImpl;
import cn.onestore.dao.UserDao;
import cn.onestore.dao.UserDaoImpl;
import cn.onestore.domain.ReceiverAddress;
import cn.onestore.domain.User;
import cn.onestore.domain.WrapUser;
import cn.onestore.exception.ActiveUserException;
import cn.onestore.exception.FindException;
import cn.onestore.exception.LoginException;
import cn.onestore.exception.RegisterException;
import cn.onestore.utils.MailUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 明柯 on 2017/4/12.
 * 用户业务逻辑接口的实现类
 */
public class UserServiceDaoImpl implements UserServiceDao{
    private UserDao userdao = new UserDaoImpl();
    @Override
    public void register(User user) throws RegisterException {
        //注册


        //添加完后，发送邮件进行激活
        // 发送邮件操作
        //1.编辑邮件信息
        String emailMsg = "注册成功，请在12小时内<a href='http://localhost:8080/user?method=activeuser&activecode="
                + user.getActivecode()
                + "'>激活</a>，激活码是"
                + user.getActivecode();
        try {
            //调用dao方法进行添加
            userdao.addUser(user);
            MailUtils.sendMail(user.getEmail(), emailMsg);
        } catch (Exception e) {
            throw new RegisterException("邮件发送失败 请重新注册");
        }

    }

    @Override
    public void activeuser(String activecode) throws ActiveUserException, RegisterException {
        //根据激活码查找用户，若查到，判断是否超时，没有超时则抛出信息激活成功；若没有查到，抛出信息，根据激活码查找用户失败
        User user = null;
        try{
            user = userdao.findUserByActivecode(activecode);//没有异常说明查找成功
        }catch (SQLException e){
            e.printStackTrace();
            throw new RegisterException("根据激活码查找用户失败！");
        }
        long time = System.currentTimeMillis() - user.getUpdatetime().getTime();
        //判断是否过了激活时间 1*60*60*1000 小时*分钟*秒*毫秒   这里限定1小时
        if(time > 1*60*60*1000){
            throw new ActiveUserException("激活码过期");//超过了时间则抛出异常而不是return
        }

        //做出判断，如果已经激活，就不用再更新
        if(user.getState() == 1){
            throw new ActiveUserException("用户已经激活，请直接去首页登陆");
        }

        //没有过期则进行激活操作，把状态改成1
        try{

            userdao.activeUserByActivecode(activecode);//没有异常说明查找成功

            //激活成功后给用户创建一个购物车
            ShopcartDao shopcartDao = new ShopcartDaoImpl();
            shopcartDao.createShopcartByUid(user.getUid());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RegisterException("激活用户失败！");
        }
    }

    @Override
    public WrapUser login(WrapUser u) throws LoginException {
        WrapUser user = null;
        try{
            user = userdao.findUserByUsernameAndPassword(u);
            if(user == null){
                throw new LoginException("用户名或密码错误！");
            }
            //查到了用户，判断状态是否激活
            if(user.getState() == 0){
                throw new LoginException("用户未激活，请查看邮件进行激活");
            }


        }catch (SQLException e){
            throw new LoginException("登陆失败");
        }
        return user;
    }

    @Override //根据id查找用户
    public WrapUser findUserByUid(Integer uid) throws FindException {
        WrapUser user = null;
        try {
            user = userdao.findUserByUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException("用户没有查到！");
        }
        return user;
    }

    @Override
    public void updateUserByPassword(WrapUser u,String newPassword) throws FindException {
        //先判断密码是否有误，能否查到数据
        WrapUser user = null;
        try{
            user = userdao.findUserByUsernameAndPassword(u);
            if(user == null){
                throw new FindException("密码错误 修改失败");
            }
            if(newPassword == null){//说明不修改密码
                //找到了就可以执行更新,密码就是原密码
                userdao.updateUserByPassword(u);
            }else { //说明修改密码
                u.setPassword(newPassword);//把新密码存进去进行修改
                userdao.updateUserByPassword(u);
            }

        }catch (SQLException e){
            throw new FindException("未知错误 修改失败！");
        }
    }

    @Override //查询用户的所有收货地址
    public List<Map<String, Object>> findReceiverAddressByUid(Integer uid) {
        List<Map<String, Object>> receiverAddressesMap = null;

        try {
            receiverAddressesMap=userdao.findReceiverAddressByUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return receiverAddressesMap;
    }
}
