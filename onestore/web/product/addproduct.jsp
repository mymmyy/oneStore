
<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/20
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加商品</title>
    <script type="text/javascript" src="../jslib/jquery.js"></script>
    <link rel="stylesheet" href="../css/addproduct.css" type="text/css">
    <script type="text/javascript" src="../jslib/addproduct.js"></script>
    <script language="JavaScript">


    </script>
</head>
<body background="${pageContext.request.contextPath}/img/background/1.jpg">
    <div class="top">
        <a href="${pageContext.request.contextPath}/shop?method=findShop&uid=${user.uid}">我的店铺</a>
        <a href="${pageContext.request.contextPath}/">首页</a>
    </div>
    <div class="a">
        <form id="form1" action="${pageContext.request.contextPath}/addproduct"
              method="post" enctype="multipart/form-data">
            <input name="shopid" value="${userShop.shopid}" type="hidden">
            <table border="1" width="100%">
                <caption>
                    <b>添加商品</b>
                </caption>

                <tr>
                    <td>商品名称</td>
                    <td>
                        <input type="text" name="pname" maxlength="15" placeholder="0-8个中文">
                        <span style="color: red"></span>
                    </td>
                </tr>

                <tr>
                    <td>商品价格</td>
                    <td><input type="text" name="price"><span style="color: red"></span></td>
                </tr>

                <tr>
                    <td>商品类别</td>
                    <td><select name="category">
                        <option>--请选择类别--</option>
                        <option value="世界图书">世界图书</option>
                        <option value="男装女装">男装女装</option>
                        <option value="名牌包包">名牌包包</option>
                        <option value="男鞋女鞋">男鞋女鞋</option>
                        <option value="随身饰品">随身饰品</option>
                        <option value="流行数码">流行数码</option>
                    </select>
                    </td>
                </tr>

                <tr>
                    <td>商品数量</td>
                    <td><input type="text" name="pnum"><span style="color: red"></span></td>
                </tr>

                <tr>
                    <td>商品图片</td>
                    <td><input type="file" name="f"><span style="color: red"></span></td>
                </tr>

                <tr>
                    <td>商品描述</td>
                    <td><textarea name="description" rows="10" cols="40"></textarea>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="添加商品"> <input type="reset" value="取消"></td>
                </tr>
            </table>
        </form>
        <c:if test="${fail.message != null}">
            ${fail.message}
        </c:if>
    </div>
</body>
</html>
