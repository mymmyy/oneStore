<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>我的购物车</title>
	<script src="../jslib/jquery-1.9.0.min.js" type="text/javascript"></script>
	<script src="../jslib/unslider.min.js" type="text/javascript"></script>

	<script src="../jslib/myshopcart.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../css/main.css" type="text/css">
	<link rel="stylesheet" href="../css/myshopcart.css" type="text/css">
</head>
<body>
    <!--

      导航条

    -->
	<!--隐藏商品的id、用户id、该商品所属店铺id、库存-->
	<input type="hidden" name="uid" value="${user.uid}">
	<%--

	<input type="hidden" name="shopid" value="${product_shopid}">
	<input type="hidden" name="pnum" value="${product.pnum}">--%>

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
		  <div class="main-logo">
			  oneStore 购物车
		  </div>


		  <ul>
			  <c:forEach var="product" items="${shopcart.products}">
				  <li class="my-li">

					  <!--隐藏一个pid-->
					  <input type="hidden" name="pid" value="${product.pid}">

					  <div class="my-checkbox">
						  <input type="checkbox">
					  </div>

					  <div class="my-pname">
						  ${product.pname}
					  </div>

					  <div class="my-img">
					  	<img src="${product.imgurl}">
					  </div>

					  <div class="my-price p-price">
						  ￥<span>${product.price}</span>
					  </div>

					  <div class="edit">
						  <div class="edit-num">
							  <c:forEach var="scp" items="${shopcart.scps}">
								  <c:if test="${scp.pid == product.pid}">
									  <input value="${scp.count}">
								  </c:if>
							  </c:forEach>
							  <!--输入框后跟着+和-号-->
							  <div class="edit-num-button">
								  <button>+</button>
								  <button>-</button>
							  </div>
						  </div>

						  <div class="edit-sec">
							  <a href="#">确认修改</a>
						  </div>
					  </div>

					  <a href="#">删除</a>
				  </li>
			  </c:forEach>
		  </ul>

		  <div class="allprice">
			  总价：￥<span>0</span>
			  <br/>
			  <button>提交</button>
			  <a href="${pageContext.request.contextPath}/orders?method=findMyOrdersByUid&uid=${user.uid}">我的订单</a>
		  </div>

		  <div class="order-items">
		 	 	<ul>
			  <!--这里提交订单的div-->

		  	 	</ul>

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