<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>我的购物车</title>
    <script src="../jslib/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script src="../jslib/unslider.min.js" type="text/javascript"></script>

    <script src="../jslib/myOrders.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../css/main.css" type="text/css">
    <link rel="stylesheet" href="../css/myOrders.css" type="text/css">
</head>
<body>
<!--

  导航条

-->
<!--隐藏商品的id、用户id、该商品所属店铺id、库存-->
<input type="hidden" name="uid" value="${user.uid}">

<nav class="clearfix">
    <!-- 整体布局 -->
    <div class="container clearfix">
        <!-- 左边登录和注册 -->

        <ul class="nav-left clearfix">
            <li>当前用户：
                ${user.nickname}
                <a href="${pageContext.request.contextPath}/user?method=logout" class="h">退出</a>
            </li>
            <li>
                <a href="/index.jsp">回首页</a>
            </li>
        </ul>

        <!-- 右边按钮选项 -->
        <ul class="nav-right clearfix">
            <li><a href="">更多</a></li>
            <li><a href="">更多</a></li>
            <li><a href="">更多</a></li>
            <li><a href="">更多</a></li>
            <li><a href="">更多</a></li>
        </ul>

    </div>
</nav>

<!--

  正文部分

-->
<div class="content clearfix container">
    <!-- 左侧边栏 -->
    <div class="left-sidebar" style="background-color: darkcyan">
        <!--用于扩展广告-->
        左侧边栏
    </div>

    <!--

      正文内容

    -->
    <div class="main">
        <div class="main-logo">
            oneStore 我的订单
        </div>

        <!--第一层循环：循环订单-->
        <c:forEach items="${wrapOrdersList}" var="wrapOrders">
            <div class="orders">
                <div class="othersinfo">
                    <span>${wrapOrders.ordertime}</span>
                    <span>订单号：${wrapOrders.oid}</span>
                    <span id="thisPrice">总额：${wrapOrders.ordermoney}</span>
                </div>
                <div class="orders-left">
                <c:forEach var="orders_product" items="${wrapOrders.orders_productList}">
                    <div class="orders-product">

                        <!--隐藏一个pid-->
                        <input type="hidden" name="pid" value="${orders_product.pid}">
                        <div class="product-imgAndPname">
                            <a href="/product?method=findProductByPid_1&pid=${orders_product.pid}">
                                <img src="${orders_product.imgurl}">
                                <span>${orders_product.pname}</span>
                            </a>
                        </div>

                        <div class="product-count">
                            x${orders_product.count}

                        </div>

                        <div class="product-price">
                            总额<br/>
                             ${orders_product.price}
                        </div>

                    </div>
                </c:forEach>
                </div>

                <!--右边订单信息及操作-->
                <div class="orders-right">
                    <!--支付状态div-->
                    <div class="state">
                        <c:choose>
                            <c:when test="${wrapOrders.paystate != 0}">
                                已完成
                            </c:when>
                            <c:otherwise>
                                <a href="#">待支付</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <!--订单操作-->
                    <div class="order-operate">
                        <a href="#">删除订单</a>
                        <a href="#">订单详情</a>
                    </div>
                </div>

                <!--订单详细信息：收货地址、备注-->
                <div class="order-detail">
                    <p>
                        订单备注：${wrapOrders.remark}
                    </p>
                    <p>
                        收货地址：${wrapOrders.receiverinfo}
                    </p>
                </div>

            </div>
        </c:forEach>

    </div>
    <!--

      右边栏

    -->
    <div class="right-sidebar" style="background-color: darkgoldenrod">
        右边栏
        <!--
          扩展广告信息
        -->

    </div>

</div>
<!--

  工具栏信息，保持在浏览器固定位置上

-->
<div class="toolbar">
    <ul>
        <li><a href=""><i class="icon-user"></i></a></li>
        <li><a href=""><i class="icon-cart"></i></a></li>
        <li><a href=""><i class="icon-order"></i></a></li>
        <li><a href=""><i class="icon-top"></i></a></li>
    </ul>
</div>

<!--

  底部信息

-->
<footer class="clearfix container">
    <ul>
        <li>2017商城 · 每天来逛逛</li>
        <li>design by · mym&YangT</li>
    </ul>
</footer>


<!--

  引入js文件

-->

<!-- <script type="text/javascript">
      !function(){
          console.log($(".items li").length);
          var len = $(".items li").length;
          for (var i = 3; i < len ; i+=4) {
              $(".items li:eq("+i+")").css("border-right","none");
          }
          for (var i = len-1; i < len ; i--) {
              $(".items li:eq("+i+")").css("border-right","none");
          }
      }();
</script> -->
</body>
</html>