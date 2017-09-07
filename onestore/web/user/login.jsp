<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/11
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>登陆</title>
    <script type="text/javascript" src="../jslib/jquery.js"></script>
    <link type="text/css" href="../css/login.css" rel="stylesheet"/>
    <script language="JavaScript">
        //对表单做判断，这里只做长度判断
        var username = /^[A-Za-z0-9_-]{3,16}$/;//不区分大小写、数字、下划线，^表示以[]中的开始，3至16位
        var password = /^[A-Za-z0-9_-]{6,18}$/;//不区分大小写、数字、下划线，^表示以[]中的开始，6至18位
        function check() {
            if($("input[name='username']").val().match(username)){
                if($("input[name='password']").val().match(password)){
                    return true;
                }else{
                    $("input[name='password']").next().html("请输入6至18位的密码，不区分大小写");
                }
            }else {
                $("input[name='username']").next().html("请输入3至16位的用户名，不区分大小写");
            }
            return false;
        }


        //对cookies中用户名的显示
        $(document).ready(function () {//注意把js的引入代码放在head中比较好

            //el表达式的内容在js中要以""包起来才能读出
            //从cookie中获取saveusername值,得到的是utf-8码
            var usernamevalue = "${cookie.saveusername.value}";
            //alert(usernamevalue);
            //usernamevalue = window.decodeURIComponent(usernamevalue,"utf-8");
            //alert(usernamevalue);
            //通过decodeURIComponent这个函数完成utf-8解码操作.
            $("#username").attr("value",window.decodeURIComponent(usernamevalue,"utf-8"));
        });

    </script>
</head>
<body background="${pageContext.request.contextPath}/img/background/1.jpg" style="width: 100%;height: 100%">
    <div id="loginForm">
        <form id="form1" method="post" action="${pageContext.request.contextPath}/user" onsubmit="return check()">
            <input type="hidden" name="method" value="login"/>
            <!--显示错误信息-->
            <span style="color: red;font-size: small">${requestScope["login.message"]}</span>
            <table border="2">
                <!--用户名-->
                <tr>
                    <td>用户名：</td>
                    <td><input name="username" id="username">
                        <span style="color: red;font-size: small"></span>
                    </td>
                </tr>
                <!--密码-->
                <tr>
                    <td>密&nbsp;&nbsp;码：</td>
                    <td><input name="password" id="password">
                        <span style="color: red;font-size: small"></span>
                    </td>
                </tr>
                <!--记住用户名和自动登陆 'on' 表示勾选后的值-->
                <tr>
                    <td>
                        <input type="checkbox" name="remeber" value="on" checked>记住用户名
                    </td>
                    <td><input type="checkbox" name="autoLogin" value="on">下次自动登录
                    </td>
                </tr>
                <!--登陆或注册按钮-->
                <tr>
                    <td colspan="2">
                        <input type="submit" value="登陆">
                        <a href="${pageContext.request.contextPath}/user/register.jsp">注册</a>
                    </td>

                </tr>
            </table>
        </form>
    </div>
</body>
</html>
