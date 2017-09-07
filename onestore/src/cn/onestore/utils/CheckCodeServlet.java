package cn.onestore.utils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by 明柯 on 2017/4/11.
 * 产生验证码
 */
public class CheckCodeServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if("getCheckCode".equals(method)){
            getCheckCode(req,resp);
        }else if("check".equals(method)){
            check(req,resp);
        }






    }

    //校验验证码
    private void check(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获得客户端输入的验证码
        String inputCode = req.getParameter("inputCode");
        //获得session属性中的验证码值
        String valcode = req.getSession().getAttribute("valcode").toString();

        if(inputCode.equals(valcode)){
            //说明校验成功，返回"1"

            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }


    }

    //获得验证码
    private void getCheckCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //首先0.1 告知浏览器当作图片处理返回内容
        resp.setContentType("image/jpeg");

        //0.2 告诉浏览器不缓存
        resp.setHeader("parama","no-cache");
        //resp.setHeader("cache-control","no-cache");
        // resp.setHeader("expires","0");
        resp.addHeader("Cache-Control", "no-store, must-revalidate");
        resp.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");

        //0.3 确立产生由4位数字构成的验证码
        int length = 4;
        String valCode = "";//准备存放验证码数字值的引用
        Random rd = new Random();//准备一个构造随机数字的对象
        //0.4 得到随机的四位数
        for (int i = 0;i<length;i++){
            valCode += rd.nextInt(10);
        }


        //产生图片
        //1 得到一个图片缓冲区
        int width = 120;//可以考虑作为宏定义
        int height = 30;
        BufferedImage img = new BufferedImage(width,height,
                BufferedImage.TYPE_INT_RGB);

        //2.获取一个Graphics，画笔
        Graphics grap = img.getGraphics();
        //转换成2d图
        Graphics2D graphics = (Graphics2D) grap;

        //3.1 填充背景色
        graphics.setColor(Color.white);
        //3.2 画出一个有填充色的四边形
        graphics.fillRect(0,0,width,height);

        //3.3 填充干扰线50
        for(int i=0; i<50; i++){
            //设置颜色类型，颜色随机
            graphics.setColor(new Color(rd.nextInt(100)+155,rd.nextInt(100)+155,rd.nextInt(100)+155));
            //划出线条
            graphics.drawLine(rd.nextInt(width), rd.nextInt(height),rd.nextInt(width), rd.nextInt(height));
        }
        //3.4 绘制边框
        graphics.setColor(Color.gray);
        graphics.drawRect(0,0,width,height);

        //4. 绘制验证码
        //4.1 设置字体
        Font[] fonts = {
                new Font("隶书",Font.BOLD,18),
                new Font("楷体",Font.BOLD,18),
                new Font("宋体",Font.BOLD,18),
                new Font("幼圆",Font.BOLD,18)
        };
        //4.2 每个字符使用不同的样式
        for (int i = 0;i<length;i++){
            //4.2.1 设置颜色,自定义的随机颜色(RGB)
            graphics.setColor(new Color(rd.nextInt(150),rd.nextInt(150),rd.nextInt(150)));
            //4.2.2 设置字体，随机设置四个字符中的一个
            graphics.setFont(fonts[rd.nextInt(fonts.length)]);
            //4.2.3 设置旋转度数 -30 to +30
            //换算成弧度
            double theta = (rd.nextInt(30)-15)*Math.PI/180;
            //设置旋转 width/2 和 height/2    即（x，y），表示以x，y坐标为轴心点旋转
            graphics.rotate(theta,width/2,height/2);
            //4.2.3 画出字符 空格隔开 byte,x坐标，y坐标
            //x坐标：4个字符位置1，2，3，4  然后＋2 隔开
            graphics.drawString(valCode.charAt(i)+"",width/valCode.length()*i+2,20);

        }

        HttpSession session = req.getSession();

        session.removeAttribute("valcode");
        session.setAttribute("valcode",valCode);
        System.out.println("验证码："+session.getAttribute("valcode"));
        //5.画完了释放资源
        graphics.dispose();
        //6.输出图像
        //使用图片IO进行输出
        ImageIO.write(img,"jpg",resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
