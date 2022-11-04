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
    <a class="active" href="patientPage.jsp">Home</a>
    <a class="active" href="<%=request.getContextPath()%>/scheduleAppointment.jsp">Schedule Appointment</a>
    <a class="active" href="<%=request.getContextPath()%>/patientLabTests.jsp">Lab Tests</a>
    <form class="form" method="post" action="signPatientOut">
        <input type="submit" value="Sign Out" class="signout">
    </form>
</div>
<div class="row">

    <div class="container">
        <h3 class="text-center">Availble Doctors</h3>
        <hr>
        <div class="container text-center">

        </div>

        <form class ="form" method = "post" action="show-community-doctors">
            <input type="submit" value="Show Available Community Doctors">
        </form>
        <form class ="form" method = "post" action="show-clinic-doctors">
            <input type="submit" value="Show Available Clinic Doctors">
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Doctor Name</th>
                <th>Doctor ID</th>
                <th>Visit Date</th>
                <th>Visit Time</th>
                <th>Schedule Appointment</th>


            </tr>
            </thead>

            <tbody id="doctorTable">
            <c:forEach var="doctor" items="${listDoctor}">
                <tr>
                    <td class="tableName"><c:out value="${doctor.name}"/></td>
                    <td class="tableID"><c:out value="${doctor.doctor_ID}" /></td>
                    <form method = "post" action ="schedule-visit">
                        <td><input type="datetime-local" name = "date" placeholder="yyyy-mm-dd"></td>
                        <td><div id="demo"></div></td>
                        <td><input type="text" name = "time" placeholder="HH:MM:SS"></td>
                        <input type = "hidden" name = "doctor_id" <c:if test="${doctor.doctor_ID!=null}"><c:out value= 'value=${doctor.doctor_ID}'/></c:if>>
                        <td><input type="submit" value="schedule"></td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
