<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
	<meta charset="UTF-8">
	<title>oneStore</title>
	<!--

      引入js文件

    -->
	<script src="jslib/jquery-1.9.0.min.js" type="text/javascript"></script>
	<script src="jslib/unslider.min.js" type="text/javascript"></script>
	<script src="jslib/unsilder-main.js" type="text/javascript"></script>
	<link rel="stylesheet" href="css/main.css">
</head>
<body>
    <!--

      导航条

    -->
	<!-- 隐藏一个uid，用来判断是否登陆 -->
	<input type="hidden" name="uid" value="${user.uid}">
	<input type="hidden" name="imgurl" value="${user.imgurl}">
	<nav class="clearfix">
	  <!-- 整体布局 -->
	  <div class="container clearfix">
        <!-- 左边登录和注册 -->

	  	<ul class="nav-left clearfix">
	  		<li>当前用户：
				<c:if test="${user.nickname == null}">
					请<a href="${pageContext.request.contextPath}/user/login.jsp" class="h">登录</a>
					<a href="${pageContext.request.contextPath}/user/register.jsp">免费注册</a>
				</c:if>
				<c:if test="${user.nickname != null}">
					${user.nickname}
					<a href="${pageContext.request.contextPath}/user?method=logout" class="h">退出</a>
				</c:if>
			</li>
	  		<%--<li></li>--%>
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
	  <div class="left-sidebar">
	    <!-- logo图标 宽度202px，高度150px-->
	  	<img src="img/logo.png" alt="">
	  	<!-- 物品栏 -->
	  	<ul id="categories">
	  	  <li><a href="#">世界图书</a></li>
	  	  <li><a href="#">男装女装</a></li>
	      <li><a href="#">名牌包包</a></li>
	      <li><a href="#">男鞋女鞋</a></li>
	  	  <li><a href="#">随身饰品</a></li>
	      <li><a href="#">流行数码</a></li>
	  	</ul>
	  </div>

      <!--

        正文内容

      -->
	  <div class="main">
        <!-- 搜索框 -->
	  	<div class="search">
			<input type="text" placeholder="搜索">
			<button><i class="icon-search"></i></button>
	  	</div>


		  <!-- 小型导航条 -->
	  	<ul class="main-nav clearfix" id="minidirect">
	  		<li><a href="${pageContext.request.contextPath}/user?method=findAll&uid=${user.uid}">个人信息</a></li>
	  		<li><a href="${pageContext.request.contextPath}/shop?method=findShop&uid=${user.uid}">我的店铺</a></li>
	  		<li><a href="${pageContext.request.contextPath}/shopcart?method=findMyShopcartByUid&uid=${user.uid}">我的购物车</a></li>
	  		<li><a href="${pageContext.request.contextPath}/orders?method=findMyOrdersByUid&uid=${user.uid}">我的订单</a></li>
	  		<li><a href="http://www.jd.com" target="_blank">去往更多</a></li>
	  	</ul>

        <!-- 广告部分,左右滑动 ，图片宽度760px，高度300px-->

        <div class="banner clearfix">
        	<ul>
        		<li><img src="img/1.jpg" alt=""></li>
                <li><img src="img/2.jpg" alt=""></li>
          		<li><img src="img/3.jpg" alt=""></li>
         		<li><img src="img/4.jpg" alt=""></li>
         		<li><img src="img/5.jpg" alt=""></li>
         		<li><img src="img/6.jpg" alt=""></li>
         		<li><img src="img/7.jpg" alt=""></li>
         		<li><img src="img/8.jpg" alt=""></li>
        	</ul>

        	<!-- 左右按钮，修改时，classname和id跟原来保持一样 -->
        	<a href="javascript:void(0);" class="unslider-arrow prev"><img class="arrow" id="al" src="img/arrowl.png" alt="prev" width="20" height="35"></a>
       		<a href="javascript:void(0);" class="unslider-arrow next"><img class="arrow" id="ar" src="img/arrowr.png" alt="next" width="20" height="37"></a>

        </div>

        <!--

          商品信息

        -->
        <div class="items clearfix" id="items-clearfix">
        	<ul class="clearfix">
        		<li>
        			<a href="">
        			  <img src="img/logo.png" alt="">
        			  <p>iphone7 plus现在超级便宜，不要错过</p>
        			</a>
        			<!-- 显示钱图片和价格 -->
        			<i class="icon-money"></i><span>2000</span>
        		</li>
        		<li>
        			<a href="">
        			  <img src="img/logo.png" alt="">
        			  <p>iphone7 plus现在超级便宜，不要错过</p>
        			</a>
        			<i class="icon-money"></i><span>2000</span>
        		</li>
        		<li>
        			<a href="">
        			  <img src="img/logo.png" alt="">
        			  <p>iphone7 plus现在超级便宜，不要错过</p>
        			</a>
        			<i class="icon-money"></i><span>2000</span>
        		</li>
        		<li>
        			<a href="">
        			  <img src="img/logo.png" alt="">
        			  <p>iphone7 plus现在超级便宜，不要错过</p>
        			</a>
        			<i class="icon-money"></i><span>2000</span>
        		</li>
        	</ul>
        </div>

		  <!--分页信息div-->
		<div class="limitdiv"></div>

	  </div>
      <!--

        右边栏

      -->
	  <div class="right-sidebar">

        <!--
          个人信息和登录和注册
        -->
	  	<div class="user">
	  	  <img src="img/init.gif" alt="没有上传头像">
	  	  <div class="user-a">
	  	    <a href="${pageContext.request.contextPath}/user/login.jsp">登录</a>
	  	    <a href="${pageContext.request.contextPath}/user/register.jsp">注册</a>
	  	  </div>
	  	</div>

		<!--
		  滚动信息，内容如果太多，就会显示慢，因为长度变大了
		-->
        <div class="info">
          <marquee behavior="scrool" direction="up" scrollamount="2">
            <p>空山不见人, 但闻人语响as</p>
            <p>返景入深林, 复照青苔上sa</p>
            <p>独坐幽篁里, 弹琴复长啸sa</p>
            <p>深林人不知, 明月来相照sa</p>
            <p>山中相送罢, 日暮掩柴扉</p>
            <p>春草明年绿, 王孙归不归</p>
            <p>红豆生南国, 春来发几枝ad</p>
            <p>愿君多采撷, 此物最相思</p>
			  <p>美人卷珠帘, 深坐蹙蛾眉</p>
            <p>但见泪痕湿, 不知心恨谁sa</p>

            <p>功盖三分国, 名成八阵图</p>
            <p>江流石不转, 遗恨失吞吴</p>
            <p>白日依山尽, 黄河入海流</p>
            <p>欲穷千里目, 更上一层楼</p>
            <p>苍苍竹林寺, 杳杳钟声晚</p>
            <p>荷笠带斜阳, 青山独归远</p>
            <p>千山鸟飞绝, 万径人踪灭</p>
            <p>孤舟蓑笠翁, 独钓寒江雪</p>
            <p>松下问童子, 言师采药去</p>
            <p>只在此山中, 云深不知处</p>
          </marquee>
        </div>
        <!--

          广告区域，未添加，可以随时修改，只是占个坑

        -->
        <div class="ad">广告位--火热招商</div>
        <div class="ad">广告位--火热招商</div>
        <div class="ad">广告位--火热招商</div>
		<div class="ad">广告位--火热招商</div>
		<div class="ad">广告位--火热招商</div>

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
        <li>2017OneStore商城 · 每天来逛逛</li>
    	<li>design by · mym&YangT</li>
      </ul>
	</footer>



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