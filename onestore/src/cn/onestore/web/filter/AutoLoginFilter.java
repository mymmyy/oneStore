package cn.onestore.web.filter;

import cn.onestore.domain.User;
import cn.onestore.domain.WrapUser;
import cn.onestore.exception.LoginException;
import cn.onestore.service.UserServiceDao;
import cn.onestore.service.UserServiceDaoImpl;
import cn.onestore.utils.CookieUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by 明柯 on 2017/4/17.
 */
public class AutoLoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //先转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //排除几个地址不过滤
        //取得地址 排除register.jsp    login.jsp
        //此请求访问地址
        String uri = request.getRequestURI();
        //全路径 ${servletContext.request.contextPath}
        String contextPath = request.getContextPath();
        //取得实际有用的访问地址  如 /register.jsp  /login.jsp
        String realUri = uri.substring(contextPath.length());

        //先确定当前是否有用户已经登陆
        if(request.getSession().getAttribute("user") == null){
            //排除不需要自动登陆的地址
            if(!("/register.jsp".equals(realUri) || "/login.jsp".equals(realUri))){
                //通过工具类中的方法取得用户名和密码  （自定义工具类）
                Cookie cookie =
                        CookieUtils.getCookieByName(request.getCookies(),"autoLogin");
                //先判断cookie中是否有值
                if(cookie != null){
                    //1.取得用户名和密码
                    //根据之前设置的标志位拆分cookie值，从而取得username  && password
                    String[] string = cookie.getValue().split("%ming%");
                    //需要对用户名进行转码
                    String username = URLDecoder.decode(string[0],"utf-8");
                    String password = string[1];
                    WrapUser user = new WrapUser();
                    user.setUsername(username);
                    user.setPassword(password);

                    //2.调用service中的方法进行登陆
                    UserServiceDao userServiceDao = new UserServiceDaoImpl();

                    try {
                        User existUser = userServiceDao.login(user);
                        //判断是否存在此用户，存在则设置session
                        if(existUser != null){
                            //存在则进行登陆操作
                            request.getSession().setAttribute("user",existUser);
                        }
                    } catch (LoginException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
