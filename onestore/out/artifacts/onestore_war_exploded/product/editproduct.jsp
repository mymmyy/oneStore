<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/21
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>编辑商品</title>
    <script type="text/javascript" src="../jslib/jquery.js"></script>
    <link rel="stylesheet" href="../css/addproduct.css" type="text/css">
    <script language="JavaScript">
        $(document).ready(function () {
            //先判断有没有success信息
            if("${success}" != null && "${success}" !=""){
                alert("${success}");
            }else if("${update.message}" != null && "${update.message}" !=""){
                alert("${update.message}");
            }else{
                alert("若要修改图片或类别，请至 我的店铺 删除商品并重新添加商品");
            }


            $("input[type=submit]").click(function (e) {
                $("input").each(function() {
                    if(this.value == null || this.value == ""){
                        e.preventDefault();
                        $(this).next("span").html("请输入");
                    }
                });
                if($("select").val() == "--请选择类别--"){
                    alert("请选择");
                    e.preventDefault();
                }
            });

            $("input").blur(function () {
                $(this).next("span").html("");
                if(this.value == null || this.value == ""){
                    $(this).next("span").html("请输入");
                }
            });

            $("select").blur(function () {
                if($("select").val() == "--请选择类别--"){
                    alert("请选择")
                }
            })

            $("img").click(function () {
                alert("若要修改图片或类别，请至 我的店铺 删除商品并重新添加商品");
            })
        })

    </script>
</head>
<body background="${pageContext.request.contextPath}/img/background/1.jpg">
<div class="top">
    <a href="${pageContext.request.contextPath}/shop?method=findShop&uid=${user.uid}">我的店铺</a>
    <a href="${pageContext.request.contextPath}/">首页</a>
</div>
<div class="a" width="80%">
    <form id="form1" action="${pageContext.request.contextPath}/product" method="post">
        <input name="method" value="editproduct" type="hidden">
        <input name="pid" value="${product.pid}" type="hidden">
        <table border="1" width="100%">
            <caption>
                <b>修改商品--（若要修改图片或类别，请删除商品重新添加）</b>
            </caption>

            <tr>
                <td>商品名称</td>
                <td>
                    <input type="text" name="pname" maxlength="15" placeholder="0-8个中文" value="${product.pname}">
                    <span style="color: red"></span>
                </td>
            </tr>

            <tr>
                <td>商品价格</td>
                <td><input type="text" name="price" value="${product.price}"><span style="color: red"></span></td>
            </tr>

            <tr>
                <td>商品类别</td>
                <td>${product.category}
                    <%--<select name="category">
                    <option>--请选择类别--</option>
                    <c:forEach var="cate" items="${category}">
                        <c:if test="${cate.name != product.category}">
                            <option value="${cate.name}">${cate.name}</option>
                        </c:if>
                        <c:if test="${cate.name == product.category}">
                            <option value="${cate.name}" selected>${cate.name}</option>
                        </c:if>
                    </c:forEach>
                </select>--%>
                </td>
            </tr>

            <tr>
                <td>商品数量</td>
                <td><input type="text" name="pnum" value="${product.pnum}"><span style="color: red"></span></td>
            </tr>

            <tr>
                <td>商品图片</td>
                <td><img src="${product.imgurl}" width="180px" height="210px"><span style="color: red"></span></td>
            </tr>

            <tr>
                <td>商品描述</td>
                <td><textarea name="description" rows="10" cols="40">${product.description}</textarea>
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="确认修改"> <input type="reset" value="取消"></td>
            </tr>
        </table>
    </form>
    <%--<span style="color: red">
        <c:if test="${requestScope['update.message'] != null}">
            ${requestScope["update.message"]}
        </c:if>
    </span>--%>
</div>
</body>
</html>
