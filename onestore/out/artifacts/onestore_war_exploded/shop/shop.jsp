<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/19
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的店铺</title>
    <script type="text/javascript" src="jslib/jquery.js"></script>
    <link rel="stylesheet" href="css/shop.css" type="text/css">
    <script language="JavaScript">
        var pname;
        $(document).ready(function () {
            //这里使用ajax异步把数据放到product div中
            //getinfo(null);//不传内容就是查找全部

            //给搜索图标添加点击事件
            $("#search").click(function () {
                pname = $("input[name = 'pname']").val();
                getInfo(pname);
            });

            //给动态append的节点绑定事件的方法
            //var adspan_a = $(".adspan").find("a");
            $(document).on("click", ".adspan > a", function() {
                //var url = this.href;
                //alert(url);
                getAdInfo(this);
                return false;//不让它提交，通过ajax提交
            })

            //给动态添加的 删除商品 节点绑定事件
            $(document).on("click",".proitem > a",function () {
               //alert("顶级");
                //1.获得它的父元素 proitem
                var thisProitem = $(this).parent();

                //2.获得它的url
                var thisUrl = this.href;

                //3.使用异步提交
                $.ajax({
                    url:thisUrl,
                    success:function () {
                        //4.删除此项商品节点
                        thisProitem.remove();
                        alert("删除成功！");
                    },
                    error:function () {
                        alert("删除失败！");
                    }
                });
                return false;//本链接的url提交失效
            });
        })

        //ajax异步提交
        function getInfo(pname) {
            var url;
            if(pname != null){
                url="${pageContext.request.contextPath}/product?method=findProductByPname&pname="+pname;
            }else {
                url="${pageContext.request.contextPath}/product?method=findAllProduct&shopid=${usershop.shopid}"
            }

            $.get(url,function(data){
                //判断是否有错误信息sql.message
                if("${sql.message}" != null && "${sql.message}" !=""){
                    alert("${sql.message}");
                }

                var json = eval(data);
                var listdiv = $(".product");//显示商品列表的div
                //var newload= listdiv.load("../product/productList1.jsp");//这里引入进来了就直接显示
                if(json != null){
                    listdiv.html("");//清空商品列表的div
                    alert("查到"+json.length+"个");
                    for(var i = 0;i<json.length;i++){
                        /*
                        <div class="proitem">
                         <div class="proitem-img">
                            <img src="\upload\3\2\9ad43532-affc-4c17-bcdf-4b3fb94abbbd.jpg">
                         </div>
                         <span>世界简史</span>
                         <br/>
                         <span>
                            ￥100.0<a href='/product?method=findproduct&pid=9'>编辑</a>
                         </span>
                         <br/>
                         <增加广告的链接>
                        </div>
                         */
                        //创建product中的每个单独div 一个单元
                        var itemdiv = $("<div class='proitem'></div>");
                        //创建放img的div
                        var imgdiv = $("<div class='proitem-img'></div>").appendTo(itemdiv);
                        // 创建img
                        $("<img src='"+json[i]["imgurl"]+"'>").appendTo(imgdiv);
                        //创建<span>世界简史</span> 放到listdiv中
                        $("<span>"+json[i]["pname"]+"</span>").appendTo(itemdiv);
                        //创建<br/>
                        $("<br/>").appendTo(itemdiv);
                        //创建<span>￥100.0<a href='/product?method=findproduct&pid=9'>编辑</a></span>
                        var priceSpan = $("<span>￥"+json[i]["price"]+"</span>").appendTo(itemdiv);
                        $("<a href='/product?method=findproduct&pid='"+json[i]["pid"]+">编辑</a>").appendTo(priceSpan);
                        //通过函数生成申请 广告 span
                        //创建<br/>
                        $("<br/>").appendTo(itemdiv);
                        if(json[i]["adstate"] == 0){//说明没有申请广告
                            //创建显示广告申请 文字广告 图片广告
                            var adSpan=$("<span class='adspan'>申请广告：</span>").appendTo(itemdiv);
                            /*$("<span>申请广告：</span>").appendTo(adSpan);*/
                            $("<a href='/product?method=updatead&adstate=1&pid="+json[i]["pid"]+"'>文字&nbsp;</a>").appendTo(adSpan);
                            $("<a href='/product?method=updatead&adstate=2&pid="+json[i]["pid"]+"'>图片</a>").appendTo(adSpan);
                        }else if(json[i]["adstate"] == 1){
                            //显示已有**广告 删除，更改
                            var adSpan=$("<span class='adspan'>已有文字广告</span>").appendTo(itemdiv);
                            /*$("<span>已有文字广告</span>").appendTo(adSpan);*/
                            $("<a href='/product?method=updatead&adstate=2&pid="+json[i]["pid"]+"'>更换&nbsp;</a>").appendTo(adSpan);
                            $("<a href='/product?method=updatead&adstate=0&pid="+json[i]["pid"]+"'>撤除</a>").appendTo(adSpan);
                        }else {
                            var adSpan=$("<span class='adspan'>已有图片广告</span>").appendTo(itemdiv);
                            /*$("<span>已有图片广告</span>").appendTo(adSpan);*/
                            $("<a href='/product?method=updatead&adstate=1&pid="+json[i]["pid"]+"'>更换&nbsp;</a>").appendTo(adSpan);
                            $("<a href='/product?method=updatead&adstate=0&pid="+json[i]["pid"]+"'>撤除</a>").appendTo(adSpan);
                        }
                        //创建<br/>
                        $("<br/>").appendTo(itemdiv);
                        //创建 删除 链接
                        $("<a href='/product?method=deleteproduct&pid="+json[i]["pid"]+"'>删除商品</a>").appendTo(itemdiv);
                        itemdiv.appendTo(listdiv);//加到放商品列表的div中
                    }
                }else {
                    listdiv.html("没有相关商品");
                }

                //alert("请求完成");
            });
        }


        //广告的span产生
        /*function generateAdSpan(json,i) {
            //创建<br/>
            $("<br/>").appendTo(itemdiv);
            if(json[i]["adstate"] == 0){//说明没有申请广告
                //创建显示广告申请 文字广告 图片广告
                var adSpan=$("<span class='adspan'>申请广告：</span>").appendTo(itemdiv);
                $("<a href='/product?method=updatead&adstate=1&pid="+json[i]["pid"]+"'>文字</a>").appendTo(adSpan);
                $("<a href='/product?method=updatead&adstate=2&pid="+json[i]["pid"]+"'>图片</a>").appendTo(adSpan);
            }else if(json[i]["adstate"] == 1){
                //显示已有**广告 删除，更改
                var adSpan=$("<span class='adspan'>已有文字广告</span>").appendTo(itemdiv);
                $("<a href='/product?method=updatead&adstate=2&pid="+json[i]["pid"]+"'>更换</a>").appendTo(adSpan);
                $("<a href='/product?method=updatead&adstate=0&pid="+json[i]["pid"]+"'>撤除</a>").appendTo(adSpan);
            }else {
                var adSpan=$("<span class='adspan'>已有图片广告</span>").appendTo(itemdiv);
                $("<a href='/product?method=updatead&adstate=1&pid="+json[i]["pid"]+"'>更换</a>").appendTo(adSpan);
                $("<a href='/product?method=updatead&adstate=0&pid="+json[i]["pid"]+"'>撤除</a>").appendTo(adSpan);
            }
        }*/

        //更改广告申请状态
        function getAdInfo(_this) {
           // var url = "/product?method=updatead&adstate="+adstate+"&pid="+pid;
            var url = _this.href;
            $.get(url,function (data) {
                //转换json数据
                var jsonData = eval(data);
                //获得广告span
                var spanad = $(_this).parent();

                if(jsonData != null){
                    //先把内容创建好
                    spanad.html("");//清空信息
                    //创建<br/>
                    //$("<br/>").appendTo(spanad);
                    if(jsonData["adstate"] == 0){//说明没有申请广告
                        //创建显示广告申请 文字广告 图片广告
                        var adSpan= $("<span class='adspan'>申请广告：</span>").appendTo(spanad);
                        $("<a href='/product?method=updatead&adstate=1&pid="+jsonData["pid"]+"'>文字&nbsp;</a>").appendTo(adSpan);
                        $("<a href='/product?method=updatead&adstate=2&pid="+jsonData["pid"]+"'>图片</a>").appendTo(adSpan);
                    }else if(jsonData["adstate"] == 1){
                        //显示已有**广告 删除，更改
                        var adSpan= $("<span class='adspan'>已有文字广告</span>").appendTo(spanad);
                        $("<a href='/product?method=updatead&adstate=2&pid="+jsonData["pid"]+"''>更换&nbsp;</a>").appendTo(adSpan);
                        $("<a href='/product?method=updatead&adstate=0&pid="+jsonData["pid"]+"''>撤除</a>").appendTo(adSpan);
                    }else {
                        var adSpan= $("<span class='adspan'>已有图片广告</span>").appendTo(spanad);
                        $("<a href='/product?method=updatead&adstate=1&pid="+jsonData["pid"]+"''>更换&nbsp;</a>").appendTo(adSpan);
                        $("<a href='/product?method=updatead&adstate=0&pid="+jsonData["pid"]+"''>撤除</a>").appendTo(adSpan);
                    }

                }
            });
        }

    </script>
</head>
<body background="${pageContext.request.contextPath}/img/background/1.jpg">
    <div id="topinfo" class="topinfo">
        <span>${user.nickname}的oneStore小店</span>
    </div>
    <div class="search">
        <input type="text" name="pname" placeholder="根据名称查找">
        <a><img id="search" src="${pageContext.request.contextPath}/img/2.png"></a>
    </div>

    <a href="${pageContext.request.contextPath}/product/addproduct.jsp" id="addproduct" class="addproduct">添加商品</a>
    <a href="${pageContext.request.contextPath}/index.jsp"  class="toindex">去首页</a>

    <div class="product">
        <jsp:include page="../product/productList.jsp"/>
        <!--分页信息div-->
        <%--<div class="limitdiv"></div>--%>
    </div>
</body>
</html>
