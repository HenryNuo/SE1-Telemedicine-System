<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: matthew
  Date: 4/4/2021
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Admin</title>
    <style><jsp:directive.include file="/CSS/LoginReg.css" /></style>
</head>
<body>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <div id="formHeaderSignUp">
            <h2 class="active underlineHover"> Create Test </h2>
        </div>

        <form id="form" method="post" action="insertTest">
            <input type="text" id="testName" class="fadeIn second" name="testName" placeholder="Test Name">
            <input type="text" id="upperLimit" class="fadeIn third" name="upperLimit" placeholder="Upper Limit" pattern="[0-9]+" required>
            <input type="text" id="lowerLimit" class="fadeIn third" name="lowerLimit" placeholder="Lower Limit" pattern="[0-9]+" required>
            <input type="text" id="unitOfMeasure" class="fadeIn third" name="unitOfMeasure" placeholder="Unit Of Measure" pattern="[A-Za-z]+" required>
            <input type="submit" class="fadeIn fourth" value="Create Test">
        </form>

    </div>
</div>

</body>
</html>
