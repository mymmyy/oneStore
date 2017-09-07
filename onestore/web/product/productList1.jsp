<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/10
  Time: 23:36
  To change this template use File | Settings | File Templates.
  作为一个代码块，需要被引用
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <div class="proitem" id="proitem">
            <div class="proitem-img">
                <img>
            </div>
            <span></span><br/>
            <span></span>
            <span><a href='${pageContext.request.contextPath}/product?method=findproduct&pid=${product.pid}'>编辑</a></span>
        </div>

        <div id="nolist" class="nolist">
            <span></span>
        </div>
