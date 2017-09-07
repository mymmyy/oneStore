package cn.onestore.web.servlet;

import cn.onestore.domain.ReceiverAddress;
import cn.onestore.domain.Shop;
import cn.onestore.domain.User;
import cn.onestore.domain.WrapUser;
import cn.onestore.exception.ActiveUserException;
import cn.onestore.exception.FindException;
import cn.onestore.exception.LoginException;
import cn.onestore.exception.RegisterException;
import cn.onestore.service.ShopServiceDao;
import cn.onestore.service.ShopServiceDaoImpl;
import cn.onestore.service.UserServiceDao;
import cn.onestore.service.UserServiceDaoImpl;
import javafx.fxml.LoadException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 明柯 on 2017/4/12.
 * web层主要做的是，数据验证，提取，和存放到域中
 */
public class UserServlet extends HttpServlet{
    private UserServiceDao userServiceDao = new UserServiceDaoImpl();
    private ShopServiceDao shopServiceDao = new ShopServiceDaoImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //通过method值判断执行函数
        String method = req.getParameter("method");
        if("register".equals(method)){
            register(req,resp);
        }else if("activeuser".equals(method)){
            activeuser(req,resp);
        }else if("login".equals(method)){
            login(req,resp);
        }else if("logout".equals(method)){
            logout(req,resp);
        } else if("findAll".equals(method)){
            findAll(req,resp);
        }else if("edit".equals(method)){
            edit(req,resp);
        }else if("getReceiverAddressByUid".equals(method)){//查询用户的所有收货地址
            getReceiverAddressByUid(req,resp);
        }
    }

    //查询用户的所有收货地址
    private void getReceiverAddressByUid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //0.声明以json数据返回
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();

        //1.获得uid
        Integer uid = Integer.valueOf(req.getParameter("uid"));

        //2.调用service方法
        List<Map<String, Object>> receiverAddresses = null;
        receiverAddresses = userServiceDao.findReceiverAddressByUid(uid);

        //3.调用jackson对象的方法把list集合穿换成json数据
        String json = mapper.writeValueAsString(receiverAddresses);
        resp.getWriter().write(json);

    }

    //修改用户信息
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取各个参数值：密码、原密码、新密码、用户名、email、昵称
        String password = req.getParameter("password");
        String forwardPassword = req.getParameter("forwardpassword");
        String newPassword = req.getParameter("newpassword");
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        String username = req.getParameter("username");
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        WrapUser u = new WrapUser();//用来封装用户信息

        //密码中有值，则说明不修改密码,否则修改密码，业务逻辑层进行判断，这里把原密码
        //这里把id传进来,不同情况传不同的密码值，然后比较密码是否正确

        u.setUid(uid);
        u.setEmail(email);
        u.setNickname(nickname);
        u.setUsername(username);

        WrapUser user = null;
        Shop userShop = null;

        //进行修改
        try{
            if(password != null){//说明不修改密码，那么就把  密码、新密码 传进去
                u.setPassword(password);
                userServiceDao.updateUserByPassword(u,newPassword);
            }else {//说明修改密码， 就把原密码、新密码 传进去
                u.setPassword(forwardPassword);
                userServiceDao.updateUserByPassword(u,newPassword);
            }
            //修改完了，再把信息找到：该用户信息，shop信息
            //调用userServiceDao中的根据id方法查询
            user= userServiceDao.findUserByUid(uid);
            //把用户的商店也查出来
            userShop = shopServiceDao.findShopByUid(uid);

        }catch (FindException e){
            //e.printStackTrace();
            req.setAttribute("find.message",e.getMessage());
            req.getRequestDispatcher(req.getContextPath()+"/user/edit.jsp").forward(req,resp);
        }
        req.getSession().setAttribute("user",user);
        req.getSession().setAttribute("userShop",userShop);
        resp.sendRedirect(req.getContextPath()+"/user/edit.jsp");
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先获得用户信息
        WrapUser user = null;
        Shop userShop = null;
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        try{
            //调用userServiceDao中的根据id方法查询
            user= userServiceDao.findUserByUid(uid);
            //把用户的商店也查出来
            userShop = shopServiceDao.findShopByUid(uid);
        } catch (FindException e) {
            req.setAttribute("find.message",e.getMessage());//这里要在主页面显示出来，最好有一个弹出窗
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        }
        user.setShop(userShop);

        req.getSession().setAttribute("user",user);
        req.getSession().setAttribute("userShop",user.getShop());
        resp.sendRedirect(req.getContextPath()+"/user/edit.jsp");
    }

    //注销
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //注销即把用户的session销毁
        req.getSession().invalidate();
        //因为之后会设置自动登陆，所以要把自动登陆中的cookies保存时间修改为0，否则退出注销后，又会重新登陆
        Cookie cookie = new Cookie("autoLogin","");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        resp.addCookie(cookie);
        //这里注销后直接再次转到主页面
        resp.sendRedirect(req.getContextPath()+"/index.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //注册
    protected void register(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        WrapUser user = new WrapUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setNickname(nickname);
        //手动封装一个激活码，通过UUID
        user.setActivecode(UUID.randomUUID().toString());
        //System.out.println(user.getUsername()+":"+user.getEmail()+":"+user.getNickname());

        //调用service操作
        try{
            userServiceDao.register(user);
            //这里要做异常捕获
            resp.getWriter().write("注册成功，激活后请<a href='"+req.getContextPath()+
                    "/user/login.jsp' >登录</a>");
        }catch (RegisterException e){
            //有异常就把自己设置的异常信息放在域中让前台获取以便作出处理
            //e.printStackTrace();
            req.setAttribute("register.message",e.getMessage());
            //这里使用request转发，因为要把异常信息放在此次请求中，让客户端读到
            req.getRequestDispatcher("/user/register.jsp").forward(req,resp);
        }
    }

    protected void activeuser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        //获得激活码
        String activecode = req.getParameter("activecode");
        //调用service方法
        //这里需要对查询进行异常内容捕获和显示
        try{
            userServiceDao.activeuser(activecode);
        }catch (ActiveUserException e){
            e.printStackTrace();
            resp.getWriter().write(e.getMessage()+
                    "激活失败，请重新<a href='"+req.getContextPath()+"/user/register'>注册</a>");
            return;
        }catch (RegisterException e){
            e.printStackTrace();
            resp.getWriter().write(e.getMessage()+
                    "激活失败，请重新<a href='"+req.getContextPath()+"/user/register'>注册</a>");
            return;
        }
        resp.getWriter().write(
                "用户激活成功,请<a href='" + req.getContextPath()
                        + "/index.jsp'>回首页</a>重新登录");

    }
    //登陆
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        WrapUser u = new WrapUser();
        u.setUsername(username);
        u.setPassword(password);

        //调用service方法验证
        try{
            WrapUser user = userServiceDao.login(u);
            //接下来是验证是否勾选自动登陆，和记住用户名
            //是否勾选记住用户名
            if("on".equals(req.getParameter("remeber"))){
                //表示勾选了记住用户名，进行存cookies操作
                Cookie cookie = new Cookie("saveusername",
                        URLEncoder.encode(username, "utf-8")); // 存储utf-8码
                //保存七天
                cookie.setMaxAge(7*24*60*60);
                cookie.setPath("/");//作用地址范围
                resp.addCookie(cookie);
            }else {
                //没有勾选就不保存cookies
                Cookie cookie = new Cookie("saveusername",
                        URLEncoder.encode(username, "utf-8")); // 存储utf-8码
                //保存七天
                cookie.setMaxAge(0);
                cookie.setPath("/");//作用地址范围
                resp.addCookie(cookie);
            }

            //是否勾选了自动登录
            if("on".equals(req.getParameter("autoLogin"))){
                //勾选了，就保存用户名和密码到cookis中,把用户名然后编码和密码组合放在一个cookie对象中
                Cookie cookie = new Cookie("autoLogin",
                        URLEncoder.encode(username,"utf-8")+"%ming%"+password);
                //中间放置标志位隔开
                //设置最大时间
                cookie.setMaxAge(7*24*60*60);
                cookie.setPath("/");//作用地址范围
                resp.addCookie(cookie);
            }else {
                //没有勾选，就不保存
                Cookie cookie = new Cookie("autoLogin",
                        URLEncoder.encode(username,"utf-8")+"%ming%"+password);
                //中间放置标志位隔开
                //设置最大时间
                cookie.setMaxAge(0);
                cookie.setPath("/");//作用地址范围
                resp.addCookie(cookie);
            }


            //登陆成功后，把登录信息存到session中
            //先把session销毁，然后再创建
            req.getSession().invalidate();
            req.getSession().setAttribute("user",user);
            resp.sendRedirect(req.getContextPath()+"/index.jsp");

        }catch (LoginException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            req.setAttribute("login.message", e.getMessage());//获得异常信息，存放到request域中
            req.getRequestDispatcher("/user/login.jsp").forward(req,resp);
        }


    }


}
