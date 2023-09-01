<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="miles.pojo.User" %>
<%@ page import="miles.jsptest.JspDap" %>
<%@ page import="miles.jsptest.JspDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="miles.jsptest.JspDaoImplTest" %>
<%--
  Created by IntelliJ IDEA.
  User: 71420
  Date: 2023/8/22
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error500.jsp" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>


<html>
<head>
    <title>Title</title>
</head>
<style>
    table{
        width: 750px;
    }
</style>
<body>
<h1>scope.jsp页面</h1>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");

%>

<%

    pageContext.setAttribute("key", "pageContext");
    request.setAttribute("key", "request");
    session.setAttribute("key", "session");
    application.setAttribute("key", "application");

%>
<%--pageContext域是否有值：<%=pageContext.getAttribute("key")%> <br>--%>
pageContext域是否有值：<%=pageContext%> <br>
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
</table <!-- hello-->
    <%
}
%>

<table>
    <% for (int i = 1; i <= 9; i++) { %>
    <tr>
        <%for (int j = 1; j <= i; j++) {%>
        <td><%= j + "*" + i + "=" + j * i %>
        </td>
        <% } %>
    </tr>
    <% } %>
</table>


<table>
    <tr>
        <td>id</td>
        <td>username</td>
        <td>password</td>
        <td>email</td>
    </tr>
    <tr>
    <%  int i;
        for (i = 0; i < userList.size(); i++) {
    %>

        <td><%=   userList.get(i).getId() %></td>
        <td><%= userList.get(i).getUsername() %></td>
        <td><%= userList.get(i).getPassword() %></td>
        <td><%= userList.get(i).getEmail() %></td>
    </tr>
       <%  } %>

<%--    <c:forEach  items="" var="${requestScope.userlist}">--%>
<%--        <td><%=   userList.get(i).getId() %></td>--%>
<%--        <td><%= userList.get(i).getUsername() %></td>--%>
<%--        <td><%= userList.get(i).getPassword() %></td>--%>
<%--        <td><%= userList.get(i).getEmail() %></td>--%>
<%--    </c:forEach>--%>
</table>

<%--EL 表达式11个隐含大对象--%>
<%--<% --%>
1${pageContext}<br>
2${pageScope}<br>
3${requestScope}<br>
4${sessionScope}<br>
5${applicationScope}<br>
6${param}<br>
7${paramValues}<br>
8${header}<br>
9${headerValues}<br>
10${cookie}<br>
11${initParam}<br>
保存前${requestScope.hello}<br>
<c:set scope="request" var="hello" value="Miles"/><br>
保存后${requestScope.hello}<br>
<%--%>--%>

</body>
</html>

