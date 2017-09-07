package cn.onestore.web.filter;

import cn.onestore.exception.FindException;
import cn.onestore.service.ProductServiceDao;
import cn.onestore.service.ProductServiceDaoImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/30.
 *对主页面随机加载商品的thisIndex参数值进行过滤
 * 若为1 代表不是第一次加载商品
 * 若为0 代表是第一次加载商品
 */
public class RandomLoadProductFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //0.进行向下转型，得到HttpServletRequest对象和HttpServletResponse对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        //1.得到thisIndex参数
        String thisIndex = request.getParameter("thisIndex");

        //2.判断参数值是否为”0“,若是则把所有的pid取出放到session，然后下传；若不是直接下传
        if("0".equals(thisIndex)){
            //说明本页面是第一次加载商品

            //3.调用service方法查到所有的pid
            ProductServiceDao productServiceDao = new ProductServiceDaoImpl();
            try {
                List<Object[]> pidList = productServiceDao.findAllPid();

                //4.把得到的list集合的pid数组存放到当前用户登陆的session中
                request.getSession().removeAttribute("pidList");//先删除再添加
                request.getSession().setAttribute("pidList",pidList);

            } catch (FindException e) {
                e.printStackTrace();
                //给客户端返回null或者""表示出错  客户端统一处理为没有更多商品
                response.getWriter().write("");
            }

        }

        //无论参数值如何都要下传
        filterChain.doFilter(request,response);


    }

    @Override
    public void destroy() {

    }
}
