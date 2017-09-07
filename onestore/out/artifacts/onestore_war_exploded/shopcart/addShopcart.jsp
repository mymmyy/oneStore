<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>商品详情</title>
	<script src="../jslib/jquery-1.9.0.min.js" type="text/javascript"></script>
	<script src="../jslib/unslider.min.js" type="text/javascript"></script>

	<script src="../jslib/shopcart.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../css/main.css" type="text/css">
	<link rel="stylesheet" href="../css/shopcart.css" type="text/css">
</head>
<body>
    <!--

      导航条

    -->
	<!--隐藏商品的id、用户id、该商品所属店铺id、库存-->
	<input type="hidden" name="pid" value="${product.pid}">
	<input type="hidden" name="uid" value="${user.uid}">
	<input type="hidden" name="shopid" value="${product_shopid}">
	<input type="hidden" name="pnum" value="${product.pnum}">

	<nav class="clearfix">
	  <!-- 整体布局 -->
	  <div class="container clearfix">
        <!-- 左边登录和注册 -->

	  	<ul class="nav-left clearfix">
			<li>当前用户：
				<c:choose>
					<c:when test="${user.nickname == null}">
						请<a href="${pageContext.request.contextPath}/user/login.jsp" class="h">登录</a>
						<a href="${pageContext.request.contextPath}/user/register.jsp">免费注册</a>
					</c:when>
					<c:otherwise>
						${user.nickname}
						<a href="${pageContext.request.contextPath}/user?method=logout" class="h">退出</a>
					</c:otherwise>
				</c:choose>
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

		  <!--左边div 用于放图片等其他信息-->
		<div class="main-left">
			<!--用于放图片-->
			<div class="main-left-imgdiv">
				<img src="${product.imgurl}"/>
			</div>
			<!--可能会放其他东西 便于扩展-->

		</div>

		<!--右边div 用于放商品信息-->
		<div class="main-right">
			<b class="main-right-font">商品分类:${product.category} </b>
			<div class="main-right-pname main-right-font">
				商品名称：
				${product.pname}
			</div>
			<div class="dd main-right-font">
				描述：
				${product.description}
			</div>

			<div class="p-price main-right-font">
				￥${product.price}
			</div>
			<div class="main-right-font">
				库存：
				${product.pnum}
			</div>

			<!--加入购物车按钮 进入商家店铺：shopid  进入我的购物车-->
			<div class="buy-button">
				<div class="buy-button-num">
					<input value="1">
					<!--输入框后跟着+和-号-->
					<div class="buy-button-button">
						<button>+</button>
						<button>-</button>
					</div>
				</div>

				<div class="buy-button-addcart">
					<a href="#">加入购物车</a>
				</div>
			</div>

			<ul>
				<li><a href="#">进入店铺</a></li>
				<li><a href="${pageContext.request.contextPath}/shopcart?method=findMyShopcartByUid&uid=${user.uid}">我的购物车</a></li>
			</ul>


			<!--作为后期商品 更多详情的展示-->
			<div class="main-bottom">

			</div>

		</div>

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

</body>
</html>