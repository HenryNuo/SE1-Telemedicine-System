<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 5/6/21
  Time: 12:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <title>Patient</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style><jsp:directive.include file="/CSS/UserPages.css" /></style>
</head>
<body>
<div class="topnav">
    <a class="active" href="patientPage.jsp">Home</a>
    <a class="active" href="<%=request.getContextPath()%>/scheduleAppointment.jsp">Schedule Appointment</a>
    <a class="active" href="<%=request.getContextPath()%>/patientLabTests.jsp">Lab Tests</a>
    <form class="form" method="post" action="signPatientOut">
        <input type="submit" value="Sign Out" class="signout">
    </form>
</div>
<div class="row">

    <div class="container">
        <h3 class="text-center">List of Prescribed Lab Tests</h3>
        <hr>


        <form class ="form" method = "post" action="listLabTests">
            <input type="submit" value="List Tests">
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Lab Name</th>
            </tr>
            </thead>

            <tbody id="labsPrescribed">
            <c:forEach var="test" items="${labtests}">
                <tr>
                    <td class="tableName"><c:out value="${test}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <form class ="form" method = "post" action="listPrescriptions">
            <input type="submit" value="See Prescriptions">
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Drug Name</th>
                <th>Dosage</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="prescription" items="${prescriptions}">
                <tr>
                    <td class="tableName"><c:out value="${prescription.medication}"/></td>
                    <td class="tableName"><c:out value="${prescription.dose}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>



    </div>
</div>
</body>
</html>
