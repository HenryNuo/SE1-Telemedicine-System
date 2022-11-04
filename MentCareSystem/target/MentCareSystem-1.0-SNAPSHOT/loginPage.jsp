<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <!--<link rel="stylesheet" href="/CSS/LoginReg.css" type="text/css">-->
    <style><jsp:directive.include file="/CSS/LoginReg.css" /></style>
    <script>
        function userSelect() {
            if (document.getElementById('patient').checked) {
                document.getElementById('form').action = "patientSignIn";
                // document.getElementById('form').action = "PatientServlet";
            }
            if (document.getElementById('doctor').checked){
                document.getElementById('form').action = "doctorSignIn";
            }
            if (document.getElementById('community_owner').checked){
                document.getElementById('form').action = "COSignIn";
            }
            if (document.getElementById('medical_owner').checked){
                document.getElementById('form').action = "MOSignIn";
            }
        }
    </script>
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <!-- Tabs Titles -->
        <div id="formHeaderSignIn">
        <h2 class="active underlineHover"> Sign In </h2>
        </div>
        <!-- Role Selection -->
        <label>What kind of user are you?</label><br>
        <input type="radio" id="patient" name="role" value="patient" onclick="userSelect();">
        <label for="patient">Patient</label>
        <input type="radio" id="doctor" name="role" value="doctor" onclick="userSelect()">
        <label for="doctor">Doctor</label>
        <input type="radio" id="community_owner" name="role" value="community_owner" onclick="userSelect()">
        <label for="community_owner">Community Owner</label>
        <input type="radio" id="medical_owner" name="role" value="medical_owner" onclick="userSelect()">
        <label for="medical_owner">Medical Owner</label>

        <!-- username Form -->
        <form id="form" method="post">
            <input type="text" id="email" class="fadeIn second" name="email" placeholder="email">
            <input type="password" id="password" class="fadeIn third" name="password" placeholder="password">
            <input type="submit" class="fadeIn fourth" value="Log In">
        </form>
        <c:if test="${notValid != null}"><p> Invalid Account</p></c:if>
        <c:if test="${notApproved != null}"><p> Your Account has not been approved yet.</p></c:if>

        <!-- Remind Passowrd -->
        <div id="formFooterForgotPassword">
            <a class="underlineHover"  href="<%=request.getContextPath()%>/forgotPassword.jsp">Forgot Password?</a>
        </div>
        <div id="formFooterSignUp">
            <a class="underlineHover" href="<%=request.getContextPath()%>/RegPage.jsp">Sign Up</a>
        </div>

    </div>
</div>
</body>
</html>
