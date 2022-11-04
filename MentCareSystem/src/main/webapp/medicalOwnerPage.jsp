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
    <title>Medical Owner</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style><jsp:directive.include file="/CSS/UserPages.css" /></style>
</head>
<body>
<div class="topnav">
    <a class="active" href="medicalOwnerPage.jsp">Home</a>
    <form class="form" method="post" action="signMedicalOwnerOut">
        <input type="submit" value="Sign Out" class="signout">
    </form>
</div>
<div class="row">
    <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

    <div class="container">
        <h3 class="text-center">List of Un-Approved Doctors</h3>
        <hr>
        <div class="container text-center">


        </div>

        <form class ="form" method = "post" action="listDoctors">
            <input type = "hidden" name = "userEmail" <c:if test="${user.email!=null}"><c:out value= 'value=${user.email}'/></c:if>>
            <input type = "hidden" name = "userPassword" <c:if test="${user.password!=null}"><c:out value= 'value=${user.password}'/></c:if>>
            <input type="submit" value="listDoctors">
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>DoctorID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="doctor" items="${listDoctor}">

                <tr>
                    <td><c:out value="${doctor.doctor_ID}" /></td>
                    <td><c:out value="${doctor.name}" /></td>
                    <td><c:out value="${doctor.email}" /></td>
                    <td>

                        <form class ="form" method = "post" action="approve">
                            <input type = "hidden" name = "userEmail" <c:if test="${user.email!=null}"><c:out value= 'value=${user.email}'/></c:if>>
                            <input type = "hidden" name = "userPassword" <c:if test="${user.password!=null}"><c:out value= 'value=${user.password}'/></c:if>>
                            <input type = "hidden" name = "doctor_ID" <c:if test="${doctor.doctor_ID!=null}"><c:out value= 'value=${doctor.doctor_ID}'/></c:if>>
                            <input type="submit" value="approve">
                        </form>

                        <form class ="form" method = "post" action="delete">
                            <input type = "hidden" name = "userEmail" <c:if test="${user.email!=null}"><c:out value= 'value=${user.email}'/></c:if>>
                            <input type = "hidden" name = "userPassword" <c:if test="${user.password!=null}"><c:out value= 'value=${user.password}'/></c:if>>
                            <input type = "hidden" name = "doctor_ID" <c:if test="${doctor.doctor_ID!=null}"><c:out value= 'value=${doctor.doctor_ID}'/></c:if>>
                            <input type="submit" value="delete">
                        </form>

                    </td>
                </tr>
            </c:forEach>

            </tbody>

        </table>

    </div>
</div>

</body>
</html>
