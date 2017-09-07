package cn.onestore.web.servlet;

import cn.onestore.domain.Category;
import cn.onestore.domain.Product;
import cn.onestore.exception.FindException;
import cn.onestore.exception.UpdateException;
import cn.onestore.service.ProductServiceDao;
import cn.onestore.service.ProductServiceDaoImpl;
import cn.onestore.service.ShopServiceDao;
import cn.onestore.service.ShopServiceDaoImpl;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 明柯 on 2017/4/21.
 * 商品的web
 *
 */
public class ProductServlet extends HttpServlet{
    private ProductServiceDao productServiceDao = new ProductServiceDaoImpl();
    private ShopServiceDao shopServiceDao = new ShopServiceDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        if("findproduct".equals(method)){
            findProductById(req,resp);
        }else if("editproduct".equals(method)){
            editProductById(req,resp);
        }else if("findProductByPname".equals(method)){
            findProductByPname(req,resp);
        }else if("findProductByCategory".equals(method)){
            findProductByCategory(req,resp);
        }else if("findProductRandom".equals(method)){
            findProductRandom(req,resp);
        }else if("findAllProduct".equals(method)){
            findAllProduct(req,resp);
        }else if("updatead".equals(method)){
            updatead(req,resp);
        }else if("deleteproduct".equals(method)){
            deleteproduct(req,resp);
        }else if("findAllAd".equals(method)){
            findAllAd(req,resp);
        }else if("findProductCountByCategory".equals(method)){
            findProductCountByCategory(req,resp);
        }else if("findProductByPname_Limit".equals(method)){
            findProductByPname_Limit(req,resp);
        }else if("findProductCountByPname".equals(method)){
            findProductCountByPname(req,resp);
        }else if("findProductByPid_1".equals(method)){
            findProductByPid_1(req,resp);
        }


    }

    private void findProductByPid_1(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //1.把pid得到
        Integer pid = Integer.valueOf(req.getParameter("pid"));
        //2.查到为pid的商品信息
        Product product = null;
        Integer shopid  = null;
        try {
            product = productServiceDao.findProductByPid(pid);

            //这里同样需要该商品所属店铺id，先不把shopid封装到product对象中，这里直接在查询商品的所属店铺，根据pid
            shopid = productServiceDao.findShopidBypid(pid);
        } catch (FindException e) {
            resp.getWriter().write("<script>alert('"+e.getMessage()+"');</script>");
            resp.getWriter().write("<script>location.href='index.jsp';</script>");
        }

        req.setAttribute("product",product);
        req.setAttribute("product_shopid",shopid);
        req.getRequestDispatcher(req.getContextPath()+"/shopcart/addShopcart.jsp").forward(req,resp);
    }

    //根据pname查出总记录数
    private void findProductCountByPname(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //0.声明以json数据返回
        resp.setContentType("application/json; charset=utf-8");
        //1.得到分类
        String pname = req.getParameter("pname");
        //1. 首先获得ObjectMapper对象  和map对象
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = null;

        //2.调用service方法执行查询计数
        map=productServiceDao.findProductCountByPname(pname);

        //3.转换成json数据
        String json = mapper.writeValueAsString(map);

        //4。回应数据
        resp.getWriter().write(json);
    }

    //根据pname 执行分页查找
    private void findProductByPname_Limit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //0.声明以json数据返回
        resp.setContentType("application/json; charset=utf-8");
        //1.得到参数
        String pname = req.getParameter("pname");
        Integer currentPage = Integer.valueOf(req.getParameter("currentPage"));
        List<Product> list = null;

        //2.得到json对象
        ObjectMapper mapper = new ObjectMapper();//本备好jaskson对象
        try {
            //3.调用service查找  多态方法
            list = productServiceDao.findProductByPname(pname,currentPage);
        } catch (FindException e) {
            System.out.println(e.getMessage());
        }

        String json = mapper.writeValueAsString(list);
        //System.out.println(json);
        resp.getWriter().write(json);

    }

    //根据分类查到该分类的总数
    private void findProductCountByCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //0.声明以json数据返回
        resp.setContentType("application/json; charset=utf-8");
        //1.得到分类
        String category = req.getParameter("category");
        //1. 首先获得ObjectMapper对象  和map对象
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = null;

        //2.调用service方法执行查询计数
        map=productServiceDao.findProductCountByCategory(category);

        //3.转换成json数据
        String json = mapper.writeValueAsString(map);

        //4。回应数据
        resp.getWriter().write(json);
    }

    //获取所有广告信息，根据adstate参数值的不同，执行不同的业务逻辑
    private void findAllAd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer adstate = Integer.valueOf(req.getParameter("adstate"));
        //0.声明以json数据返回
        resp.setContentType("application/json; charset=utf-8");

        //1. 首先获得ObjectMapper对象 和list对象
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> adMap =null;

        //2.调用service方法进行查找   adstate=1 调用findAllTextAd();adstate=2 调用findAllPictureAd();
        if(adstate == 1){
            adMap= productServiceDao.findAllTextAd(adstate);
        }else if(adstate == 2){
            adMap= productServiceDao.findAllPictureAd(adstate);
        }


        //3.调用jackson对象的方法把内容转换为String的json数据
       String textString = mapper.writeValueAsString(adMap);
        System.out.println(textString);
        resp.getWriter().write(textString);

    }

    //删除商品根据pid
    private void deleteproduct(HttpServletRequest req, HttpServletResponse resp) {
        //1.获得商品的pid
        Integer pid = Integer.valueOf(req.getParameter("pid"));

        //2.调用service方法删除商品
        try {
            productServiceDao.deleteproductByPid(pid);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }


    }

    //更新广告状态 通过pid
    private void updatead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //要做的事：获取adstate、pid
        Integer pid = Integer.valueOf(req.getParameter("pid"));
        Integer adstate = Integer.valueOf(req.getParameter("adstate"));
        //0.声明以json数据返回
        resp.setContentType("application/json; charset=utf-8");

        //1. 首先获得ObjectMapper对象
        ObjectMapper mapper = new ObjectMapper();

        try {
            //2.调用service方法返回
            productServiceDao.updateAdstate(pid,adstate);

            //更新没问题的话就根据pid查询得到数据
            Product product= productServiceDao.findProductByPid(pid);
            String json = mapper.writeValueAsString(product);
            resp.getWriter().write(json);
        } catch (FindException e) {
            System.out.println(e.getMessage());//异常暂不往前台放
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }
    }

    //随机查询--显示主页面的商品
    private void findProductRandom(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //要做的事：
        // 1.设置以json数据返回
        //2.调用service方法查询（service中做随机查询，随机查出8个，这里从session存放的剩余pid中获取随机8个pid）
        //3.把数据以json方式返回

        // 1.设置以json数据返回
        resp.setContentType("application/json; charset=utf-8");
        //2.调用service方法查询（service中做随机查询，随机查出8个）
        //1.获得session对象设置的pidList属性值中的所有的pid

        //查询过的id把它从pidList中移出

        List<Object[]> pidList = (List<Object[]>) req.getSession().getAttribute("pidList");
        List<Product> list = null;
        List<Object[]> nums  = null;
        int len = 8;//规定每次最高查出得记录条数
        try {
            //说明大于8个，随机挑出8个
            //得出8个随机不同得数方法很多
            nums  = new ArrayList<Object[]>();
            Random random = new Random();
            int ran;
            for(int i = 0;i< len && pidList.size() > 0;i++){
                ran = random.nextInt(pidList.size());
                nums.add(pidList.get(ran));
                pidList.remove(ran);
            }

            //不为空才去查找，否则直接返回空
            if(nums.size() > 0){
                list = productServiceDao.findProductByRandomPids(nums);
                req.getSession().removeAttribute("pidList");//先删除再添加
                req.getSession().setAttribute("pidList",pidList);

                //3.把数据以json方式返回
                //3.1 首先获得ObjectMapper对象
                ObjectMapper mapper = new ObjectMapper();
                //3.2 把得到的list集合转换成String类型的序列
                String json = mapper.writeValueAsString(list);
                //3.3 把json数据格式的数据返回
                resp.getWriter().write(json);
            }else {
                resp.getWriter().write("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("");
            //出了错返回null
        }

    }

    //根据分类查找商品
    private void findProductByCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //要做的事：1.获得分类
        //2.调用service方法查询
        //3.把数据以json方式返回

        // 0.设置以json数据返回
        resp.setContentType("application/json; charset=utf-8");

        //1.获得分类 和当前页
        String category = req.getParameter("category");
        Integer currentPage = Integer.valueOf(req.getParameter("currentPage"));

        //2.调用service方法查询
        List<Product> list = null;

        try {
            list = productServiceDao.findProductByCategory(category,currentPage);
        } catch (FindException e) {
            //e.printStackTrace();
        }

        //3.把数据以json方式返回
        //3.1 首先获得ObjectMapper对象
        ObjectMapper mapper = new ObjectMapper();
        //3.2 把得到的list集合转换成String类型的序列
        String json = mapper.writeValueAsString(list);
        //3.3 把json数据格式的数据返回
        resp.getWriter().write(json);

    }

    //根据pname模糊查找
    private void findProductByPname(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //要做的事：首先获取pname值，调用service方法模糊查找,查到的值放到request域中
        String pname = req.getParameter("pname");
        List<Product> list = null;
        //0.设置返回数据读取方式
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();//本备好jaskson对象
        try {
            list = productServiceDao.findProductByPname(pname);
        } catch (FindException e) {
            System.out.println(e.getMessage());
        }

        String json = mapper.writeValueAsString(list);
        //System.out.println(json);
         resp.getWriter().write(json);


    }

    //根据pid修改商品内容
    private void editProductById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //要做的事：首先获取商品的pid、pname、price、pnum、description
        //调用service的方法进行修改
        //修改完成返回我的店铺
        Product product = new Product();
        product.setPid(Integer.valueOf(req.getParameter("pid")));
        product.setPname(req.getParameter("pname"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setPnum(Integer.valueOf(req.getParameter("pnum")));
        product.setDescription(req.getParameter("description"));


        try {
            productServiceDao.updateProductByPid(product);
            //更新完把内容再查出来
            product = productServiceDao.findProductByPid(product.getPid());
        } catch (UpdateException e) {
            req.setAttribute("product",product);
            req.setAttribute("update.message",e.getMessage());
            req.getRequestDispatcher(req.getContextPath()+"/product/editproduct.jsp").forward(req,resp);
        } catch (FindException e) {
            resp.getWriter().write("<script>alert('"+e.getMessage()+"');</script>");
            resp.getWriter().write("<script>location.href='product/editproduct.jsp';</script>");
        }
        req.setAttribute("product",product);
        req.setAttribute("success","修改成功");
        req.getRequestDispatcher("/product/editproduct.jsp").forward(req,resp);


    }

    //根据id查找商品
    private void findProductById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //要做的事：
        //0.设置返回数据读取方式response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/json; charset=utf-8");
        //1.把pid得到
        //2.查到为pid的商品信息
        //3.把商品信息放到json输出流中
        //4.resp跳转页面到修改页面
        //PrintWriter out = resp.getWriter();

        //注：这里由于逻辑简单就不用json数据   需要查出类别字典中的所有类别

        //0.设置返回数据读取方式
        //resp.setContentType("application/json; charset=utf-8");

        //1.把pid得到
        Integer pid = Integer.valueOf(req.getParameter("pid"));
        //2.查到为pid的商品信息
        Product product = null;
        try {
            product = productServiceDao.findProductByPid(pid);
        } catch (FindException e) {
            resp.getWriter().write("<script>alert('"+e.getMessage()+"');</script>");
            resp.getWriter().write("<script>location.href='shop/shop.jsp';</script>");
        }
        //3.把商品信息放到json输出流中
        /**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        //ObjectMapper mapper = new ObjectMapper();
       // mapper.writeValue(out,product);
       // out.println();
        //resp.sendRedirect("/product/editproduct.jsp");
        req.setAttribute("product",product);
        req.getRequestDispatcher("/product/editproduct.jsp").forward(req,resp);
    }

    //进入我的店铺页面的 信息获取
    private void findAllProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //需要获取的信息：店铺名、所有商品   需要的参数（进行查询）：店铺id（商品的外键）
        Integer shopid = Integer.valueOf(req.getParameter("shopid"));
        //0.设置返回数据读取方式
        //resp.setContentType("application/json; charset=utf-8");

        //这里只需要查找所有商品
        List<Product> list = null;
        try {
            list = productServiceDao.findAllProductByShopid(shopid);
        } catch (FindException e) {
            resp.getWriter().write(e.getMessage()+"<br/><a href='http://127.0.0.1:8080/index.jsp'>回首页</a>");
        }
        if(list==null || list.isEmpty()){
            req.setAttribute("addtip","你还没有上架任何商品");//没有商品信息
            req.getRequestDispatcher("/shop/shop.jsp").forward(req,resp);
        }else{
            //把取得的商品放到request域中
            req.setAttribute("productlist",list);
            req.getRequestDispatcher("/shop/shop.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
