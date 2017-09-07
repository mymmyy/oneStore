<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/10
  Time: 23:36
  To change this template use File | Settings | File Templates.
  作为一个代码块，需要被引用
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>


<c:if test="${productlist != null}">
    <c:forEach items="${productlist}" var="product"  varStatus="status">
        <div class="proitem">
            <div class="proitem-img">
                <img src="${product.imgurl}">
            </div>
            <span>${product.pname}</span><br/>
            <span>
                ￥${product.price }
                <a href='${pageContext.request.contextPath}/product?method=findproduct&pid=${product.pid}'>编辑</a>
            </span>
            <br/>
            <c:if test="${product.adstate == 0}">
                <span class="adspan">申请广告：
                    <a href="${pageContext.request.contextPath}/product?method=updatead&adstate=1&pid=${product.pid}">文字</a>
                    <a href="${pageContext.request.contextPath}/product?method=updatead&adstate=2&pid=${product.pid}">图片</a>
                </span>
            </c:if>
            <c:if test="${product.adstate == 1}">
                <span class="adspan">已有文字广告
                    <a href="${pageContext.request.contextPath}/product?method=updatead&adstate=2&pid=${product.pid}">更换</a>
                    <a href="${pageContext.request.contextPath}/product?method=updatead&adstate=0&pid=${product.pid}">撤除</a>
                </span>
            </c:if>
            <c:if test="${product.adstate == 2}">
                <span class="adspan">已有图片广告
                    <a href="${pageContext.request.contextPath}/product?method=updatead&adstate=1&pid=${product.pid}">更换</a>
                    <a href="${pageContext.request.contextPath}/product?method=updatead&adstate=0&pid=${product.pid}">撤除</a>
                </span>
            </c:if>
            <br/>
            <a href="${pageContext.request.contextPath}/product?method=deleteproduct&pid=${product.pid}">删除商品</a>
        </div>

        <%--<c:if test="${status.count % 4 eq 0 || status.count eq 4}"></c:if>--%>
    </c:forEach>
</c:if>
<c:if test="${productlist == null}">
    <div class="nolist">
    <span>你还没有上架任何商品</span>
    </div>
</c:if>
