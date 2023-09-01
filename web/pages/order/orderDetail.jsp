<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 71420
  Date: 2023/8/31
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif" >
    <span class="wel_word">图书管理系统</span>

    <%-- 静态包含 manager管理模块的菜单  --%>
    <%@include file="/pages/common/manager_menu.jsp"%>
    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>

</div>

<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>数量</td>
            <td>价格</td>
            <td>总价</td>
            <td>订单号</td>
        </tr>

        <c:forEach items="${requestScope.page.items}" var="orderItem">
            <tr>
                <td>${orderItem.name}</td>
                <td>${orderItem.count}</td>
                <td>${orderItem.price}</td>
                <td>${orderItem.totalPrice}</td>
                <td>${orderItem.orderId}</td>
            </tr>
        </c:forEach>

    </table>

    <%--静态包含分页条--%>
    <%@include file="/pages/common/page_nav.jsp"%>
</div>


<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>
</body>
</html>
