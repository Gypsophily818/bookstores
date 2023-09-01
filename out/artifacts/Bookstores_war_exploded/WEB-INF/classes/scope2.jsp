<%--
  Created by IntelliJ IDEA.
  User: 71420
  Date: 2023/8/22
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>scope2.jsp页面</h1>
pageContext域是否有值：<%=pageContext.getAttribute("key")%> <br>
request域是否有值：<%=request.getAttribute("key")%> <br>
session域是否有值：<%=session.getAttribute("key")%> <br>
application域是否有值：<%=application.getAttribute("key")%> <br>

<!-- hello-->
<%-- --%>
<%!
    double map = 99.99;
    //单行注释
    /*
    多行注释
     */
%>
<%=1 %>
<%=map%>
<br>
<% for (int i = 0; i < 10; i++) { %>
<table>
    <tr>第<%=i%>行</tr>
</table <!-- hello-->>
    <%
}
%>



<%--}--%>
<%--%>--%>
</body>
</html>

