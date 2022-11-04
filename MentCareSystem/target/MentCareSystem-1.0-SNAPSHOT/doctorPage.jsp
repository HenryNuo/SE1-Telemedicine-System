<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--
  Created by IntelliJ IDEA.
  User: matthew and sully
  Date: 4/4/2021
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="https://cdn.zingchart.com/zingchart.min.js"></script>

    <title>Doctor</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style><jsp:directive.include file="/CSS/UserPages.css" /></style>
    <%--Alert-script--%>
    <script>
        let diabArr = [], diabTime = []; //arrays for test data/timestamps, prolly better way to do this but cant be bothered atm
        let systArr = [], systTime = [];
        let diasArr = [], diasTime = [];
        let bodyArr = [], bodyTime = [];
        let tempArr = [], tempTime = [];
        let cholArr = [], cholTime = [];
        let bloodArr = [], bloodTime = [];

        function chartFunc(id) { //this function should grab data based on which button is pressed and display the appropriate chart
            let dataArr = [], timeArr = []; //dataArr is used to store the values of tests, timeArr stores dates of those values
            let title = "", dataPoints = "", dates = ""; //title changes based on button, datapoints and dates are just comma-separated strings made from the appropriate array
            if(id === "diabBut"){
                console.log("Pressed the diabetes button!");
                dataArr = [...diabArr];
                timeArr = [...diabTime];
                title = "Diabetes";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }
            if(id === "systBut"){
                console.log("Pressed the syst button!");
                dataArr = [...systArr];
                timeArr = [...systTime];
                title = "Systolic Blood Pressure";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }
            if(id === "diasBut"){
                console.log("Pressed the diastotic button!");
                dataArr = [...diasArr];
                timeArr = [...diasTime];
                title = "Diastolic Blood Pressure";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }
            if(id === "bodyBut"){
                console.log("Pressed the body button!");
                dataArr = [...bodyArr];
                timeArr = [...bodyTime];
                title = "Body Weight";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }
            if(id === "tempBut"){
                console.log("Pressed the temp button!");
                dataArr = [...tempArr];
                timeArr = [...tempTime];
                title = "Temperature";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }
            if(id === "cholBut"){
                console.log("Pressed the cholesterol button!");
                dataArr = [...cholArr];
                timeArr = [...cholTime];
                title = "Cholesterol";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }
            if(id === "bloodBut"){
                console.log("Pressed the blood button!");
                dataArr = [...bloodArr];
                timeArr = [...bloodTime];
                title = "Blood Sugar";
                dataPoints = dataArr.join(", ");
                dates = timeArr.join(", ");
            }

            zingchart.render({ //renders chart, idk if dependency working rn or not
                id: "myChart",
                width: "100%",
                height: 500,
                data: {
                    "type": "line",
                    "title": {
                        "text": title
                    },
                    "scale-x": {
                        "labels": timeArr
                    },
                    "plot": {
                        "line-width": 1
                    },
                    "series": [{
                        "values": dataArr
                    }]

                }
            });
        };

         function loadDataForPatient(){
             diabArr.length=0; diabTime.length=0; // clear all the old data
             systArr.length=0; systTime.length=0;
             bodyArr.length=0; bodyTime.length=0;
             tempArr.length=0; tempTime.length=0;
             cholArr.length=0; cholTime.length=0;
             bloodArr.length=0; bloodTime.length=0;
             $("#historyTable > tr").each(function(){
                 $(this).show();
             });
            var selected =  document.getElementById("patients");
            let realID = selected.options[selected.selectedIndex].value;
            $("#historyTable > tr").each(function(){
                let tableID = $(this).find(".tableID").text();
                let tableValue = parseFloat($(this).find(".tableValue").text());
                let tableType = $(this).find(".tableType").text();
                let tableTime = $(this).find(".tableTime").text();
                console.log(tableID);
                console.log(tableValue);
                console.log(tableType);
                console.log("")
                //console.log("Diabetes: " + diabArr.toString());
                //console.log("Syst: " + systTime.toString());
                //console.log("Dias: " + diasTime.toString());
                //console.log("Body: " + bodyTime.toString());
                //console.log("Temp: " + tempTime.toString());
                //console.log("Chol: " + cholTime.toString());
                //console.log("Blood: " + bloodTime.toString());
                if(tableID === realID){
                    if (tableType === "Diabetes") {
                        diabArr.push(tableValue);
                        diabTime.push(tableTime);
                        if (tableValue > 130 || tableValue < 70) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                    if (tableType === "Systolic") {
                        systArr.push(tableValue);
                        systTime.push(tableTime);
                        if (tableValue > 180 || tableValue < 0) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                    if (tableType === "Diastolic") {
                        diasArr.push(tableValue);
                        diasTime.push(tableTime);
                        if (tableValue > 120 || tableValue < 0) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                    if (tableType === "Body") {
                        bodyArr.push(tableValue);
                        bodyTime.push(tableTime);
                        if (tableValue > 300 || tableValue < 70) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                    if (tableType === "Temperature") {
                        tempArr.push(tableValue);
                        tempTime.push(tableTime);
                        if (tableValue > 101 || tableValue < 95) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                    if (tableType === "Cholesterol") {
                        cholArr.push(tableValue);
                        cholTime.push(tableTime);
                        if (tableValue > 240 || tableValue < 40) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                    if (tableType === "Blood") {
                        bloodArr.push(tableValue);
                        bloodTime.push(tableTime);
                        if (tableValue > 500 || tableValue < 100) {
                            alert("Your " + tableType + " value of " + tableValue + " is abnormal. Your doctors will be contacted.");
                        }
                    }
                }else {
                    $(this).hide();
                }
            });
        };
    </script>

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
        <h3 class="text-center">Patient Data</h3>
        <hr>
        <div class="container text-center">

        </div>

        <form class ="form" method = "post" action="viewTestHistoryDoctor">
            <input type="submit" value="View Test History By Community ID">
        </form>

        <form class ="form" method = "post" action="viewTestHistoryDoctorClinic">
            <input type="submit" value="View Test History By Clinic ID">
        </form>

        <form>
            <label class="selectLabel"> Patient Id</label>
            <select id="patients" name="selectedPatient" onchange="loadDataForPatient()">
                <option> None Selected</option>
                <c:forEach var="id" items="${patientData}">
                    <option value =<c:out value="${id}"/>><c:out value="${id}" /></option>
                </c:forEach>
            </select>
        </form>

        <button id ="diabBut" class="medButton" onclick="chartFunc(this.id);">Diabetes</button>
        <button id ="systBut" class="medButton" onclick="chartFunc(this.id);">Systolic</button>
        <button id ="diasBut" class="medButton" onclick="chartFunc(this.id);">Diastolic</button>
        <button id ="bodyBut" class="medButton" onclick="chartFunc(this.id);">Body Weight</button>
        <button id ="tempBut" class="medButton" onclick="chartFunc(this.id);">Temperature</button>
        <button id ="cholBut" class="medButton" onclick="chartFunc(this.id);">Cholesterol</button>
        <button id ="bloodBut" class="medButton" onclick="chartFunc(this.id);">Blood Sugar</button>
        <div id ="myChart"></div>
        <div id="patientID">${User.patientID}</div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Patient ID</th>
                <th>Test Name</th>
                <th>Level</th>
                <th>Unit</th>
                <th>Time Entered</th>
                <th>Alert Patient</th>

            </tr>
            </thead>

            <tbody id="historyTable">
            <c:forEach var="test" items="${testData}">
                <tr>
                    <td class="tableID"><c:out value="${test.patient_ID}" /></td>
                    <td class="tableType"><c:out value="${test.type}" /></td>
                    <td class="tableValue"><c:out value="${test.stats}" /></td>
                    <td class="tableUnit"><c:out value="${test.unitOfMeasure}" /></td>
                    <td class="tableTime"><c:out value="${test.time}" /></td>
                    <td>
                    <form class="form" method="post" action="sendAlert">
                        <input type = "hidden" name = "testDataID" value="${test.test_data_ID}"/>
                        <input type="text" name = "alertMessage">
                        <input type="submit">
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
