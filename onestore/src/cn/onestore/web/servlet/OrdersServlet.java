package cn.onestore.web.servlet;

import cn.onestore.domain.WrapOrders;
import cn.onestore.domain.WrapOrders_product;
import cn.onestore.exception.UpdateException;
import cn.onestore.service.OrdersService;
import cn.onestore.service.OrdersServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by 明柯 on 2017/4/28.
 * 订单的控制Servlet
 */
public class OrdersServlet extends HttpServlet{
    private OrdersService ordersService = new OrdersServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("createOrders".equals(method)){
            createOrders(req,resp);//创建订单
        }else if("findMyOrdersByUid".equals(method)){
            findMyOrdersByUid(req,resp);//根据用户查询订单
        }else if("payOrdersByUidAndOrdersid".equals(method)){
            payOrdersByUidAndOrdersid(req,resp);//根据用户id和订单id进行支付
        }else if("deleteUserOrdersByOid".equals(method)){
            deleteUserOrdersByOid(req,resp);//根据用户id和oid删除订单
        }
    }

    //根据用户id和oid删除订单
    private void deleteUserOrdersByOid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //0.得到参数值
        WrapOrders wrapOrders = new WrapOrders();
        wrapOrders.setUid(Integer.valueOf(req.getParameter("uid")));
        wrapOrders.setOid(req.getParameter("oid"));

        //1.调用service方法进行删除
        ordersService.deleteUserOrdersByOid(wrapOrders);

        resp.getWriter().write("1");//表示删除成功
    }

    //根据用户id和订单id进行支付
    private void payOrdersByUidAndOrdersid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //0.得到参数值
        WrapOrders wrapOrders = new WrapOrders();
        wrapOrders.setUid(Integer.valueOf(req.getParameter("uid")));
        wrapOrders.setOrdermoney(Float.parseFloat(req.getParameter("ordermoney")));
        wrapOrders.setOid(req.getParameter("oid"));


        //1.根据用户id和订单号进行模拟支付 修改支付状态
        ordersService.payOrdersByOrdersInfo(wrapOrders);

        resp.getWriter().write("1");//表示支付成功

    }

    //根据用户查询订单
    private void findMyOrdersByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        List<WrapOrders> wrapOrdersList = null;

        //1.调用方法查询
        wrapOrdersList = ordersService.findMyOrdersByUid(uid);

        //2.把查询到的值放到request域中
        req.setAttribute("wrapOrdersList",wrapOrdersList);
        req.getRequestDispatcher("/orders/MyOrders.jsp").forward(req,resp);


    }

    //创建订单
    private void createOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<WrapOrders_product> list_op = new ArrayList<WrapOrders_product>();
        WrapOrders wrapOrders = new WrapOrders();

        //1.获得参数数据，并进行封装
        String pidsJson = req.getParameter("pids");
        String countsJson = req.getParameter("counts");
        String pnamesJson = req.getParameter("pnames");
        String imgurlsJson = req.getParameter("imgurls");
        String pricesJson = req.getParameter("prices");
        wrapOrders.setUid(Integer.valueOf(req.getParameter("uid")));
        wrapOrders.setOrdermoney(Double.parseDouble(req.getParameter("ordermoney")));
        wrapOrders.setReceiverinfo(req.getParameter("receiverinfo"));
        wrapOrders.setRemark(req.getParameter("remark"));

        ObjectMapper mapper = new ObjectMapper();
        Integer[] pids = mapper.readValue(pidsJson,Integer[].class);
        Integer[] counts = mapper.readValue(countsJson,Integer[].class);
        String[] pnames = mapper.readValue(pnamesJson,String[].class);
        String[] imgurls = mapper.readValue(imgurlsJson,String[].class);
        Float[] prices = mapper.readValue(pricesJson,Float[].class);

        //System.out.println(pids.toString());


        //2.生成一个订单号
        wrapOrders.setOid(UUID.randomUUID().toString());

        //3.把pid和count放到WrapOrders_product类对象中

        for(int i = 0;i<pids.length;i++){
            WrapOrders_product wrapOrders_product = new WrapOrders_product();
            wrapOrders_product.setOid(wrapOrders.getOid());
            wrapOrders_product.setPid(Integer.valueOf(pids[i]));
            wrapOrders_product.setCount(Integer.valueOf(counts[i]));
            wrapOrders_product.setPname(pnames[i]);
            wrapOrders_product.setImgurl(imgurls[i]);
            wrapOrders_product.setPrice(prices[i]);
            list_op.add(wrapOrders_product);
        }

        wrapOrders.setOrders_productList(list_op);

        //4.调用OrderService中方法，创建订单

        ordersService.createOrders(wrapOrders);

        resp.getWriter().write("1");//回应客户端一条数据，若客户端接收到了数据，说明操作结束并成功

    }
}
