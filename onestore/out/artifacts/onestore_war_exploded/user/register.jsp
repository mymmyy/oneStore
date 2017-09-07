<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/11
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>注册页面</title>
    <script type="text/javascript" src="../jslib/jquery.js"></script>

    <script src="../jslib/register.js" type="text/javascript"></script>

</head>
<body background="${pageContext.request.contextPath}/img/background/1.jpg">
    <div id="register" style="margin-left: 200;margin-top: 150">
        <form id="registerForm" action="${pageContext.request.contextPath}/user" method="post">
            <input type="hidden" name="method" value="register">
            <table border="2" style="width:600" id="registerTable">
                <!--用户名-->
                <tr>
                    <td>用户名</td>
                    <td><input name="username"><span style="color: red;font-size: small"></span></td>
                </tr>
                <!--昵称-->
                <tr>
                    <td>昵&nbsp;&nbsp;&nbsp;称</td>
                    <td><input name="nickname"><span style="color: red;font-size: small"></span></td>
                </tr>
                <!--密码-->
                <tr>
                    <td>密&nbsp;&nbsp;&nbsp;码</td>
                    <td><input type="password" name="password" id="password"><span style="color: red;font-size: small"></span></td>
                </tr>
                <!--确认密码-->
                <tr>
                    <td>确认密码</td>
                    <td><input type="password" name="password1"><span style="color: red;font-size: small"></span></td>
                </tr>
                <!--邮箱-->
                <tr>
                    <td>邮&nbsp;&nbsp;&nbsp;箱</td>
                    <td><input name="email"><span style="color: red;font-size: small"></span></td>
                </tr>
                <!--验证码-->
                <tr>
                    <td>验证&nbsp;&nbsp;码</td>
                    <td><input name="affirmCode" style="float: left">
                        <a href="#">
                            <img id="im" src="${pageContext.request.contextPath}/checkCode?method=getCheckCode">
                            看不清换一张
                        </a>
                        <span style="color: red;font-size: small" id="imgspan"></span>

                    </td>
                </tr>
                <!--确认提交-->
                <tr>
                    <td colspan="2">
                        <input type="submit" onclick="return postTo()" value="进行注册">
                    </td>
                </tr>
            </table>
        </form>
        <c:if test="${requestScope['register.message'] != null}">
            <span style="color: red">${requestScope["register.message"]}</span>
        </c:if>
    </div>

</body>
</html>
