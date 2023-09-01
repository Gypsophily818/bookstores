<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 71420
  Date: 2023/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${1==1}">
    <h1>这是true</h1>
</c:if>

<%
    request.setAttribute("val", 9);
%>
<c:choose>
    <c:when test="${requestScope.val<10}">
        这个值小于10
    </c:when>
    <c:when test="${requestScope.val>10}">
        这个值大于10
    </c:when>
</c:choose>

<c:forEach begin="1" end="10" var="index">
    ${index}
</c:forEach>
</body>
</html>
