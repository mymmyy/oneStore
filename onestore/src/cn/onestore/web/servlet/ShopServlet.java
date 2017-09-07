package cn.onestore.web.servlet;

import cn.onestore.domain.Product;
import cn.onestore.domain.Shop;
import cn.onestore.domain.WrapUser;
import cn.onestore.exception.CreateShopException;
import cn.onestore.exception.FindException;
import cn.onestore.service.ProductServiceDao;
import cn.onestore.service.ProductServiceDaoImpl;
import cn.onestore.service.ShopServiceDao;
import cn.onestore.service.ShopServiceDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 明柯 on 2017/4/17.
 */
public class ShopServlet extends HttpServlet{
    private ShopServiceDao shopServiceDao = new ShopServiceDaoImpl();
    private ProductServiceDao productServiceDao = new ProductServiceDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("createShop".equals(method)){
            createShop(req,resp);
        }else if("findShop".equals(method)){
            findShop(req,resp);
        }
    }
    //查找我的店铺
    private void findShop(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //功能说明：先判断该用户是否店铺，若有
        // 根据uid查得该用户的店铺信息，以及所有的商品，并跳转到他的shop.jsp中
        //若没有，给出提示去个人信息页面申请店铺

        //获取uid
        Integer uid = Integer.valueOf(req.getParameter("uid"));

        //查找该用户是否有店铺
        Shop shop = shopServiceDao.findShopByUid(uid);
        if(shop ==null){
            resp.getWriter().write("<script>alert('你还没有店铺，点击个人信息去申请一个吧.');</script>");
            resp.getWriter().write("<script>location.href='index.jsp';</script>");
        }
        //有店铺则根据店铺id查找商品
        //这里只需要查找所有商品
        List<Product> list = null;
        try {
            list = productServiceDao.findAllProductByShopid(shop.getShopid());
        } catch (FindException e) {
            resp.getWriter().write(e.getMessage()+"<br/><a href='http://127.0.0.1:8080/index.jsp'>回首页</a>");
        }

        //把取得的商品放到request域中
        req.setAttribute("productlist",list);

        //把店铺信息放到session中
        req.getSession().setAttribute("userShop",shop);
        req.getRequestDispatcher("/shop/shop.jsp").forward(req,resp);
    }



    //创建店铺
    private void createShop(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        String shopname = req.getParameter("shopname");
        Shop shop = new Shop();
        WrapUser user = new WrapUser();

        shop.setShopname(shopname);
        try{
            //shopServiceDao.createShop(shop,uid);
            //把创建好的店铺信息取得 并放到扩展User类对象中
            shopServiceDao.createShop(shop,uid);
            user.setShop(shopServiceDao.findShopByUid(uid));
        }catch (CreateShopException e) {
            //e.printStackTrace();
            if("店铺已经存在".equals(e.getMessage())){
                user.setShop(shopServiceDao.findShopByUid(uid));
                req.getSession().setAttribute("userShop",user.getShop());
            }
            req.setAttribute("find1.message",e.getMessage());
            req.getRequestDispatcher("/user/edit.jsp").forward(req,resp);
        }
        //把创建好的店铺信息放到session中，方便页面读取
        req.getSession().setAttribute("userShop",user.getShop());
        resp.sendRedirect(req.getContextPath()+"/user/edit.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
