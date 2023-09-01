<%--
  Created by IntelliJ IDEA.
  User: 71420
  Date: 2023/8/23
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="http://localhost:8080/bookstores/uploadServlet" method="post" enctype="multipart/form-data">
    用户名：<input type="text" name="username"/> <br>
    头像：<input type="file" name="photo"> <br>
    <input type="submit" value="上传">
</form>
<button>下载</button>
</body>
</html>
