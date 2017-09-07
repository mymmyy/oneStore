<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/17
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>用户信息</title>
    <script type="text/javascript" src="../jslib/jquery.js"></script>
    <link rel="stylesheet" href="../css/edit.css" type="text/css">
    <script language="JavaScript" type="text/javascript">
        var password = /^[A-Za-z0-9_-]{6,18}$/;//不区分大小写、数字、下划线，^表示以[]中的开始，6至18位
        var email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        var isEditPassword = true;//标志位，标明是否修改密码
        //邮箱验证：以小写字母，数字，下划线，点，中杠，不限字符数 +@+ 点 + 2至6位小写字母组成
        $(document).ready(function () {
            //让申请店铺的div隐藏，显示店铺信息的div
            $("#applyshop").hide();

            //首先让密码、原密码、新密码、确认按钮、取消按钮 的tr隐藏
            $("#forwardpassword").hide();
            $("#newpassword").hide();
            $("#password").hide();
            $("#comfirebutton").hide();

            $("#xg").click(function () {
                var flag = window.confirm("要同时修改密码吗？");
                if(flag){
                    //说明同时修改密码
                    $("#forwardpassword").show();
                    $("#newpassword").show();
                    isEditPassword = true;

                }else{
                    //说明不同时修改密码
                    //密码确认，根据密码修改，密码错误则不修改
                    $("#password").show();
                    isEditPassword = false;
                }
                //创建昵称所在的td的input标签
                createInputElement($("#nickname"),"nickname");
                //创建邮箱所在的td中的input标签
                createInputElement($("#email"),"email");

                //确认按钮、取消按钮显示
                $("#comfirebutton").show();

                //让修改链接消失，防止再次点击
                $(this).hide();

                //让显示错误的span消失
                $("#errorMessage").hide();

            });



            //对于申请店铺按钮的点击事件
            $("#apply").click(function () {
                //点击按钮申请店铺 即直接进行店铺申请（在本div中）
                $("#shopinfo").hide();
                $("#applyshop").show();
            });

            //进入我的店铺按钮点击事件
            $("#myshop").click(function () {
                window.location.replace("${pageContext.request.contextPath}/product?method=findAllProduct&shopid=${userShop.shopid}");
            });

        });

        //生成input元素，需要传父节点
        function createInputElement(tdNode,name) {

            var text = tdNode.text().trim();
            //alert(text);
            //判断是否存在input标签
            tdNode.text("");
            var input = $("<input>");
            input.attr("name",name);
            input.attr("value",text);
            input.appendTo(tdNode);
            //让内容高亮选中
            var inputdom = input.get(0);
            inputdom.select();
        }

        //提交前的检查
        function postTo() {
            $("input").each(function(){

                if(this.type == "password"){//判断是否是密码、邮箱（做格式验证）
                    //根据修改的内容去除相关信息的验证
                    if(isEditPassword){//说明需要修改密码
                        //不需要验证name=password的
                        if(this.name != "password"){
                            if(!this.value.match(password)){
                                alert("请输入6-18位密码！");
                                return false;
                            }
                        }
                    }else{
                        //只需要验证 name=password的
                        if(this.name == "password"){
                            if(!this.value.match(password)){
                                alert("请输入6-18位密码！");
                                return false;
                            }
                        }
                    }
                }else if(this.name == "email"){//判断是不是email
                    if(!this.value.match(email)){
                        alert("请输入正确的邮箱！");
                        return false;
                    }
                }else if(this.value == null && this.value == ""){
                    return false;
                }
            });
            return true;
        }
        //取消修改按钮函数
        function reflash() {
            window.location.replace("${pageContext.request.contextPath}/user/edit.jsp");
        }

        //申请店铺提交申请按钮
        function chackname() {
            if($("#shopname").val()==null){
                return false;
            }
            return true;
        }

    </script>
</head>
<body background="${pageContext.request.contextPath}/img/background/1.jpg">
<div id="topinfo">
    <span>个人信息</span>
    <a href="${pageContext.request.contextPath}/index.jsp">回首页</a>
</div>
  <div id="info">
      <a id="xg" href="#">点击修改信息</a>
      <form action="${pageContext.request.contextPath}/user" method="post">
          <input name="method" value="edit" type="hidden">
          <input name="uid" value="${user.uid}" type="hidden">
          <input name="username" value="${user.username}" type="hidden">
         <table>
             <!--头像-->
             <tr>
                 <td colspan="2">
                     <a><img src="img/init.gif" alt="没有上传头像" class="imgclass"></a>
                     <a>点击添加修改图片</a>
                 </td>
             </tr>

          <!--用户名-->
            <tr>
                <td>用户名：</td>
                <td>${user.username}</td>
            </tr>
            <!--昵称-->
            <tr>
                <td>昵称：</td>
                 <td id="nickname">
                    ${user.nickname}
                 </td>
             </tr>
            <!--密码隐藏起来-->
             <tr id="password">
                 <td>密码：</td>
                 <td>
                  <input type="password" name="password">
              </td>
            </tr>
            <!--准备需要修改密码需要的内容--1-->
            <tr id="forwardpassword">
                <td>原密码：</td>
                 <td>
                   <input type="password" name="forwardpassword">
                 </td>
            </tr>
            <!--准备需要修改密码需要的内容--2-->
            <tr id="newpassword">
                 <td>新密码：</td>
                 <td>
                    <input type="password" name="newpassword">
                 </td>
             </tr>
            <!--邮箱-->
            <tr>
                  <td>e-mail：</td>
                  <td id="email">
                  ${user.email}
                 </td>
            </tr>

             <!--级别-->
            <tr>
                 <td>用户级别：</td>
                 <td id="level">${user.level}</td>
             </tr>
            <tr id="comfirebutton">
                 <td colspan="2" align="center">
                    <input type="submit" value="确认修改" onclick="return postTo()">
                    <input type="button" value="取消修改" name="ceshi" onclick="reflash()">
                 </td>
             </tr>
        </table>
      </form>
      <span id="errorMessage">${requestScope["find.message"]}</span>
  </div>

 <div id="shopinfo">
     <c:if test="${userShop != null}">
         <span style="width: 250px;margin-top: 130px;position: absolute;margin-left: auto;color: sienna;font-size: 20px;">我的店铺：${userShop.shopname}</span>
         <input style="position: absolute;margin-left: 60px;margin-top: 165px;" type="button" value="进入我的店铺" id="myshop">
     </c:if>
     <c:if test="${userShop == null}">
            <span style="margin-top: 130px;position: absolute;margin-left: 50px;color: sienna;font-size: 20px;">
                你还没有申请店铺</span><br>
            <input style="position: absolute;margin-left: 85px;margin-top: 145px;" id="apply" type="button" value="申请店铺">
     </c:if>

     <c:if test="${requestScope['find1.message'] != null}">
         <span style="margin-top: 100px;position: absolute;margin-left: 70px;color: red;">${requestScope["find1.message"]}</span>
     </c:if>
 </div>

<div id="applyshop">
    <form action="${pageContext.request.contextPath}/shop" method="post">
        <input name="method" value="createShop" type="hidden">
        <input name="uid" value="${user.uid}" type="hidden">
        <table>
            <tr>
                <td>店铺名：</td>
                <td><input name="shopname" id="shopname"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="提交申请" onclick="return chackname()">
                </td>
            </tr>
        </table>

    </form>
</div>



</body>
</html>
