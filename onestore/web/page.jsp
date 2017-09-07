<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/10
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>商品主页面</title>
    <script type="text/javascript" src="jslib/jquery.js"></script>
    <link type="text/css" href="css/page.css" rel="stylesheet"/>
    <script type="text/javascript" language="JavaScript">
        //控制导航栏链接是否有效果
        $(document).ready(function () {
            $("#myinfo").click(function () {
                return check();
            })

            $("#myshop").click(function () {
                return check();
            })

        });

        //验证是否登录
        function check() {
            if("${user.uid}" != "" && "${user.username}" != null){
                return true;
            }
            alert("请先登录!");
            return false;
        }

    </script>
</head>
<body>
<!--这是主界面顶部内容显示 当前用户：___ 注销    背景音乐播放条（5条）-->
   <div id="topMenu">
       <div id="topMenu01">oneStore</div>
        <div style="color: darkmagenta;margin-left: 100px;float: left">
            <span id="showLoginInfo">当前用户：
                <c:if test="${user.nickname == null}">
                    <a href="${pageContext.request.contextPath}/login.jsp">请登录/注册</a>
                </c:if>
                <c:if test="${user.nickname != null}">
                    ${user.nickname}
                    <a href="${pageContext.request.contextPath}/user?method=logout">退出</a>
                </c:if>
            </span>
        </div>
       <div id="music">
           这里显示背景音乐
       </div>
   </div>

<!--这是首页菜单主要导航条 我的信息、我的店铺、我的购物车、我的订单、商品查询-->
    <div id="majorMenu">
        <div>
            <a id="myinfo" class="classspan" href="${pageContext.request.contextPath}/user?method=findAll&uid=${user.uid}">个人信息</a>
        </div>

        <div >
            <a id="myshop" class="classspan" href="${pageContext.request.contextPath}/shop?method=findShop&uid=${user.uid}">我的店铺</a>
        </div>

        <div id="mycart">
            <a class="classspan">我的购物车</a>
        </div>

        <div id="myorder">
            <a class="classspan">我的订单</a>
        </div>

    </div>

<!--下面界面一分为三：分类10 商品列表60 广告位30-->
<iframe id="category" frameborder="2" src="category.jsp" align="left">
    123
</iframe>

<iframe id="productList" frameborder="2" src="product/productList.jsp" align="left">
    456
</iframe>

<iframe id="ad" frameborder="2" src="Ad.jsp" align="left">
    789
</iframe>
    <%--<div id="category">

    </div>
    <div id="productList">

    </div>
    <div id="ad">

    </div>--%>
</body>
</html>
