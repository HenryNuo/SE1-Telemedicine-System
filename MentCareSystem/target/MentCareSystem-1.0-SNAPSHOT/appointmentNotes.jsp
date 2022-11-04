<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: Sully
  Date: 5/4/2021
  Time: 1:43 PM
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

<%--    Prescription table
prescription id
patient id
doctor id
name
dosage


--%>

</head>
<body>
<div class="topnav">
    <a class="active" href="doctorPage.jsp">Home</a>
    <a class="active" href="<%=request.getContextPath()%>/viewAppointment.jsp">View Appointments</a>
    <form class="form" method="post" action="signDoctorOut">
        <input type="submit" value="Sign Out" class="signout">
    </form>
</div>
<div class="row">
    <div class="container">
        <h3 class="text-center">Appointment Notes and Prescriptions</h3>
        <hr>
        <div class="container text-center">

        </div>
        <form method = "post" action="post-notes">
            Symptoms
            <br>
            <textarea rows = "1" cols = "60" name = "symptoms"></textarea>
            <br>

            <br>

            Tests

            <br>
            <textarea rows = "1" cols = "30" name = "tests">Tests</textarea>

            <span>
                <input type="button" value="+" onclick="createNewTest();"/>
            </span>

            <br>

            <span id="newTestId"></span>

            <br>

            Prescription and Dosage
            <br>
            <textarea rows = "1" cols = "30" name = "prescriptions">Prescription</textarea>
            <textarea rows = "1" cols = "28" name = "dosage">Dosage</textarea>

            <span>
                <input type="button" value="+" onclick="createNewPrescriptionDose();"/>
            </span>

            <br>

            <span id="newPrescriptionDosageId"></span>

            <br>
            Final Notes about this visit
            <br>
            <textarea rows = "5" cols = "60" name = "summary">Enter details here...</textarea>
            <br>
            <input type="hidden" value="${doctor_id}" name="doctor_id">
            <input type="hidden" value="${patient_id}" name="patient_id">
            <input type="hidden" value="${visit_id}" name="visit_id">

            <input type = "submit" value = "submit" />
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Patient ID</th>
                <th>Visit Date</th>
                <th>Visit Time</th>
                <th>Symptoms</th>
                <th>Summary</th>
                <th>Prescriptions</th>
                <th>Lab Tests</th>

            </tr>
            </thead>

            <tbody id="visitTable">
            <c:forEach var="visit" items="${listVisit}">

                <tr>
                    <td class="tablePatientID"><c:out value="${visit.patient_id}"/></td>
                    <td class="tableDate"><c:out value="${visit.visitDate}"/></td>
                    <td class="tableTime"><c:out value="${visit.visitTime}"/></td>
                    <td class="tableSymptoms"><c:out value="${visit.symptomNotes}"/></td>
                    <td class="tableSummary"><c:out value="${visit.doctorSummary}"/></td>
                    <td class="tablePrescription"><c:out value="${visit.prescriptionList}"/></td>
                    <td class="tableLabTest"><c:out value="${visit.testList}"/></td>

                </tr>

            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
<script>
    var itemIndex = 0;
    function createNewPrescriptionDose() {

        //Adding more prescription boxes
        // First create a DIV element.
        var newPrescriptionBox = document.createElement('span');

        itemIndex++;

        var itemID = "prescription" + itemIndex;
        var newContent = "<input style=\"max-width: 45%\" placeholder='Prescription' type='text' id='" + itemID + "' name='" + itemID + "'>";

        // Then add the content (a new input box) of the element.
        newPrescriptionBox.innerHTML = newContent;

        // Finally put it where it is supposed to appear.
        document.getElementById("newPrescriptionDosageId").appendChild(newPrescriptionBox);

        //Adding more dosage boxes
        // First create a DIV element.
        var newDosageBox = document.createElement('span');

        var itemID = "dosage" + itemIndex;
        var newContent = "<input style=\"max-width: 45%\" placeholder='Dosage' type='text' id='" + itemID + "' name='" + itemID + "'> <br>";

        // Then add the content (a new input box) of the element.
        newDosageBox.innerHTML = newContent;

        // Finally put it where it is supposed to appear.
        document.getElementById("newPrescriptionDosageId").appendChild(newDosageBox);

    }

    var testIndex = 0;
    function createNewTest() {

        //Adding more prescription boxes
        // First create a DIV element.
        var newTestBox = document.createElement('span');

        testIndex++;

        var itemID = "test" + testIndex;
        var newContent = "<input style=\"max-width: 45%\" placeholder='Test' type='text' id='" + itemID + "' name='" + itemID + "'> <br>";

        // Then add the content (a new input box) of the element.
        newTestBox.innerHTML = newContent;

        // Finally put it where it is supposed to appear.
        document.getElementById("newTestId").appendChild(newTestBox);

    }

</script>
</body>
</html>
