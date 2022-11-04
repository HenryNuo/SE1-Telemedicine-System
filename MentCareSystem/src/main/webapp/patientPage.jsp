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
    <script type="text/javascript" src="https://cdn.zingchart.com/zingchart.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <title>Patient</title>
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

    window.onload = function(){
            $("patientID").hide();

            let realID = $("#patientID").text();
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
    <a class="active" href="patientPage.jsp">Home</a>
    <a class="active" href="<%=request.getContextPath()%>/scheduleAppointment.jsp">Schedule Appointment</a>
    <a class="active" href="<%=request.getContextPath()%>/patientLabTests.jsp">Lab Tests</a>
    <form class="form" method="post" action="signPatientOut">
        <input type="submit" value="Sign Out" class="signout">
    </form>
</div>
<div class="row">

    <div class="container">
        <h3 class="text-center">List of Tests</h3>
        <hr>
        <div class="container text-center">

        </div>

        <form class ="form" method = "post" action="listTests">
            <input type="submit" value="List Tests">
        </form>

        <form class ="form" method = "post" action="viewTestHistoryPatient">
            <input type="submit" value="View Test History">
        </form>

        <button id ="diabBut" class="medButton" onclick="chartFunc(this.id);">Diabetes</button>
        <button id ="systBut" class="medButton" onclick="chartFunc(this.id);">Systolic</button>
        <button id ="diasBut" class="medButton" onclick="chartFunc(this.id);">Diastolic</button>
        <button id ="bodyBut" class="medButton" onclick="chartFunc(this.id);">Body Weight</button>
        <button id ="tempBut" class="medButton" onclick="chartFunc(this.id);">Temperature</button>
        <button id ="cholBut" class="medButton" onclick="chartFunc(this.id);">Cholesterol</button>
        <button id ="bloodBut" class="medButton" onclick="chartFunc(this.id);">Blood Sugar</button>
        <div id ="myChart"></div>
    <div id="patientID">${user.patientID}</div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Patient Name</th>
                <th>Patient ID</th>
                <th>Test Name</th>
                <th>Level</th>
                <th>Unit</th>
                <th>Time Entered</th>
                <th>Alerts</th>


            </tr>
            </thead>

            <tbody id="historyTable">
                <c:forEach var="test" items="${testData}">
                    <tr>
                        <td class="tableName"><c:out value="${user.name}"/></td>
                        <td class="tableID"><c:out value="${test.patient_ID}" /></td>
                        <td class="tableType"><c:out value="${test.type}" /></td>
                        <td class="tableValue"><c:out value="${test.stats}" /></td>
                        <td class="tableUnit"><c:out value="${test.unitOfMeasure}" /></td>
                        <td class="tableTime"><c:out value="${test.time}" /></td>
                        <td class="alertMessage"><c:out value="${test.alertMessage}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Test Name</th>
                <th>Actions</th>
                <th>Unit Of Measure</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="test" items="${listTest}">

                <tr>
                    <td><c:out value="${test.testName}" /></td>

                    <td>

                        <form id="form" method="post" action="insertTestData">
                            <input type="text" id="inputData" name="inputData" placeholder="Input Data" pattern = "[0-9.]+" required>
                            <input type = "hidden" name = "testID" <c:if test="${test.testID!=null}"><c:out value= 'value=${test.testID}'/></c:if>>
                            <input type = "hidden" name = "testName" class="testName"<c:if test="${test.testName!=null}"><c:out value= 'value=${test.testName}'/></c:if>>
                            <input type = "hidden" name = "unitOfMeasure" <c:if test="${test.unitOfMeasure!=null}"><c:out value= 'value=${test.unitOfMeasure}'/></c:if>>
                            <input type="submit" value="Submit Test">
                        </form>

                    <td><c:out value="${test.unitOfMeasure}" /></td>

                    </td>
                </tr>
            </c:forEach>

            </tbody>

        </table>

    </div>
</div>
</body>
</html>
