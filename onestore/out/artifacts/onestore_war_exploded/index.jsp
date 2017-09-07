<%--
  Created by IntelliJ IDEA.
  User: 明柯
  Date: 2017/4/10
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--直接转发到查询页面，把该显示的内容先查出来：商品列表，广告位内容，
然后通过servlet转发到另一个page.jsp页面显示出来-->

<jsp:forward page="${pageContext.request.contextPath}/index1.jsp"></jsp:forward>