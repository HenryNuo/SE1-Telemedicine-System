<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 3/28/21
  Time: 9:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title> Login Succesful</title>
</head>
<body>
<h1>
<c:out value='${user.name}'/> you have successfully logged in.</h1>
</body>
</html>
