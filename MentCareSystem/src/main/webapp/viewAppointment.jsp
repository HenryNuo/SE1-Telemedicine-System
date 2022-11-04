<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 4/4/2021
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <title>Schedule Appointment</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style><jsp:directive.include file="/CSS/UserPages.css" /></style>

</head>
<body>
<div class="topnav">
    <a class="active" href="doctorPage.jsp">Home</a>
    <form class="form" method="post" action="signDoctorOut">
        <input type="submit" value="Sign Out" class="signout">
    </form>
</div>
<div class="row">

    <div class="container">
        <h3 class="text-center">Current Appointments</h3>
        <hr>
        <div class="container text-center">

        </div>

        <form class ="form" method = "post" action="show-appointments">
            <input type="submit" value="Show Appointments">
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Patient ID</th>
                <th>Visit Date</th>
                <th>Visit Time</th>
                <th>Fulfill Appointment</th>
            </tr>
            </thead>

            <tbody id="visitTable">
            <c:forEach var="visit" items="${listVisit}">
                <tr>
                    <td class="tablePatientID"><c:out value="${visit.patient_id}"/></td>
                    <td class="tableDate"><c:out value="${visit.visitDate}"/></td>
                    <td class="tableTime"><c:out value="${visit.visitTime}"/></td>

                    <td>
                        <form method = "post" action = "visit-notes">
                            <input type = "hidden" name = "visit_id" <c:if test="${visit.visit_id!=null}"><c:out value= 'value=${visit.visit_id}'/></c:if>>
                            <input type="submit" value="Fulfill Appointment">
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
