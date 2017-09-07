package cn.onestore.web.servlet;

import cn.onestore.domain.Scp;
import cn.onestore.domain.Shopcart;
import cn.onestore.exception.AddShopcartException;
import cn.onestore.exception.FindException;
import cn.onestore.exception.UpdateException;
import cn.onestore.service.ShopcartService;
import cn.onestore.service.ShopcartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 明柯 on 2017/4/26.
 * 用于处理购物车相关业务
 */
public class ShopcartServlet extends HttpServlet{
    private ShopcartService shopcartService = new ShopcartServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("addShopcart".equals(method)){
            addShopcart(req,resp);//添加商品到购物车
        }else if("findMyShopcartByUid".equals(method)){
            findMyShopcartByUid(req,resp);//把该用户的购物车中的商品信息查出来
        }else if("deleteProductByUidAndPid".equals(method)){
            deleteProductByUidAndPid(req,resp);//根据商品pid和uid删除从购物车中删除商品
        }
    }

    //根据商品pid和uid删除从购物车中删除商品
    private void deleteProductByUidAndPid(HttpServletRequest req, HttpServletResponse resp) {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        Integer pid = Integer.valueOf(req.getParameter("pid"));

        //1.调用service方法进行从购物车中删除
        try {
            shopcartService.deleteProductByUidAndPid(uid,pid);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }

    }

    //把该用户的购物车中的商品信息查出来
    private void findMyShopcartByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //需要查出的信息：所有商品、商品数量等，封装到Shopcart对象中

        Shopcart shopcart =null;
        //1.获得uid
        Integer uid = Integer.valueOf(req.getParameter("uid"));

        //2.根据uid调用service方法进行查询
        try {
            shopcart = shopcartService.findMyShopcartByUid(uid);
        } catch (FindException e) {
            System.out.println(e.getMessage());
        }

        //把内容放到request域中
        req.setAttribute("shopcart",shopcart);
        req.getRequestDispatcher("/shopcart/MyShopcart.jsp").forward(req,resp);



    }

    //添加商品到购物车
    private void addShopcart(HttpServletRequest req, HttpServletResponse resp) {
        //1.获得所有参数值 封装到第三张表对象中

        Scp scp = new Scp();
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        scp.setCount(Integer.valueOf(req.getParameter("count")));
        scp.setPid(Integer.valueOf(req.getParameter("pid")));

        //2.调用service方法进行添加
        try {
            shopcartService.addShopcart(uid,scp);
        } catch (AddShopcartException e) {
            System.out.println(e.getMessage());//这里打印出错误信息
            //暂时不对前台进行传送错误信息，因为使用的是ajax提交，所以执行失败有错误，ajax可以执行对应方法
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
