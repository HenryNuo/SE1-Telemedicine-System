
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <style><jsp:directive.include file="CSS/LoginReg.css" /></style>
    <script>
        //@author: gromey
        //on form submission jquery
        $("#form").submit(function(submitEvent){
            //prevent the default submit action
            submitEvent.preventDefault();
            let isValid = true;
            //retrieve fields from form
            let name = $("#name").text();
            let birthday = $("#birthday").text();
            let address = $("#address").text();
            let ssn = $("#SSN").text();
            let famId = $("#FamID").text();
            let prevFamId = $("#PrevFamID").text();
            let comId = $("#comID").text();
            let email = $("#email").text();

            //validation checks
            if(name === "" || name === null){
                alert("Please enter name.")
                isValid = false;
            }
            if(birthday === "" || birthday === null){
                alert("Please enter birth date.")
                isValid = false;
            }
            if(address === "" || address === null){
                alert("Please enter a valid address.")
                isValid = false;
            }
            if(ssn === "" || ssn === null){
                alert("Please enter social security number.")
                isValid = false;
            }
            if(famId === "" || famId === null){
                alert("Please enter family ID.")
                isValid = false;
            }
            if(prevFamId === "" || prevFamId === null){
                alert("Please enter previous family ID.")
                isValid = false;
            }
            if(comId === "" || comId === null){
                alert("Please enter community ID.")
                isValid = false;
            }
            if(email === "" || email === null){
                alert("Please enter a valid email address.")
                isValid = false;
            }
            //submit the valid form
            if(isValid){
                $("#form").submit();
            }
        });

        function formReveal() {
            if (document.getElementById('patient').checked){

                //Setting patient specific fields to be visible
                document.getElementById('divPatient').style.display = 'inline-block';
                //Enabling patient specific fields
                if(document.getElementById('yes_Family').checked){
                    document.getElementById('familyName').style.display = 'inline-block';
                    document.getElementById('familyID').style.display = 'none';
                    document.getElementById('FamName').disabled = false;
                    document.getElementById('FamID').disabled = true;

                }
                else {
                    document.getElementById('familyName').style.display = 'none';
                    document.getElementById('familyID').style.display = 'inline-block';
                    document.getElementById('FamName').disabled = true;
                    document.getElementById('FamID').disabled = false;

                }

                document.getElementById('PrevFamID').disabled = false;
                document.getElementById('P_comID').disabled = false;
                document.getElementById('P_clinic_ID').disabled = false;
                document.getElementById('form').action = "insertPatient";
            }
            else {

                //Setting patient specific fields to be invisible
                document.getElementById('divPatient').style.display = 'none';
                //Disabling patient specific fields
                document.getElementById('FamName').disabled = true;
                document.getElementById('FamID').disabled = true;
                document.getElementById('PrevFamID').disabled = true;
                document.getElementById('P_comID').disabled = true;
                document.getElementById('P_clinic_ID').disabled = true;
            }

            if (document.getElementById('doctor').checked){

                //Setting Doctor specific fields to be visible
                document.getElementById('divDoctor').style.display = 'inline-block';
                //Enabling Doctor specific fields
                document.getElementById('D_comID').disabled = false;
                document.getElementById('D_clinic_ID').disabled = false;

                document.getElementById('form').action = "insertDoctor";
            }
            else {

                //Setting Doctor specific fields to be invisible
                document.getElementById('divDoctor').style.display = 'none';
                //Disabling Doctor specific fields
                document.getElementById('D_comID').disabled = true;
                document.getElementById('D_clinic_ID').disabled = true;
            }


            if (document.getElementById('community_owner').checked){

                //Setting community owner specific fields to be visible
                document.getElementById('divCO').style.display = 'inline-block';
                //Enabling community owner specific fields
                document.getElementById('CO_comID').disabled = false;

                document.getElementById('form').action = "insertCommunityOwner";
            }
            else {

                //Setting community owner specific fields to be invisible
                document.getElementById('divCO').style.display = 'none';
                //Disabling community owner specific fields
                document.getElementById('CO_comID').disabled = true;
            }

            if (document.getElementById('medical_owner').checked){

                //Setting medical owner specific fields to be visible
                document.getElementById('divMO').style.display = 'inline-block';
                //Enabling medical owner specific fields
                document.getElementById('MO_clinic_ID').disabled = false;

                document.getElementById('form').action = "insertMedicalOwner";
            }
            else {

                //Setting medical owner specific fields to be invisible
                document.getElementById('divMO').style.display = 'none';
                //Disabling medical owner specific fields
                document.getElementById('MO_clinic_ID').disabled = true;
            }
        }

    </script>
</head>
<body onload="formReveal()">
<div class="wrapper fadeInDown">
    <div id="formContent">
        <!-- Tabs Titles -->
        <div id="formHeaderSignUp">
            <h2 class="active underlineHover"> Sign Up </h2>
        </div>

        <label>What kind of user are you?</label><br>
        <input type="radio" id="patient" name="role" value="patient" <c:if test="${patient!=null}">checked="checked" </c:if> onclick="formReveal();">
        <label for="patient">Patient</label>
        <input type="radio" id="doctor" name="role" value="doctor"  <c:if test="${doctor!=null}">checked="checked" </c:if>  onclick="formReveal()">
        <label for="doctor">Doctor</label>
        <input type="radio" id="community_owner" name="role" value="community_owner" <c:if test="${community_owner!=null}">checked="checked" </c:if> onclick="formReveal()">
        <label for="community_owner">Community Owner</label>
        <input type="radio" id="medical_owner" name="role" value="medical_owner" <c:if test="${medical_owner!=null}">checked="checked" </c:if> onclick="formReveal()">
        <label for="medical_owner">Medical Owner</label>
        <!-- username Form -->
        <form id="form" method="post" action="insertUser">
            <input type="email" id="email" class="fadeIn third" name="email" placeholder="Email" <c:if test="${user.email!=null}"><c:out value= 'value=${user.email}'/></c:if>>
            <c:if test="${notUniqueEmail != null}"><p> An account with that email already exists.</p></c:if>
            <input type="password" id="password" class="fadeIn second" name="password" placeholder="Password" pattern="[A-Za-z0-9]{8,15}" required <c:if test="${user.password!=null}"><c:out value= 'value=${user.password}'/></c:if>>
            <input type="text" id="name" class="fadeIn second" name="name" placeholder="Name" pattern="[A-Za-z]+" required <c:if test="${user.name!=null}"><c:out value= 'value=${user.name}'/></c:if>>
            <input type="text" id="birthdate" class="fadeIn third" name="birthdate" placeholder="Birthdate: yyyy-mm-dd" pattern="\d{4}-?\d{2}-?\d{2}" required <c:if test="${user.birthdate!=null}"><c:out value= 'value=${user.birthdate}'/></c:if>>
            <input type="text" id="address" class="fadeIn third" name="address" placeholder="Address" <c:if test="${user.address!=null}"><c:out value= 'value=${user.address}'/></c:if>>
            <input type="text" id="SSN" class="fadeIn third" name="SSN" placeholder="SSN" pattern="\d{3}-?\d{2}-?\d{4}" required <c:if test="${user.socialSecurityNumber!=null}"><c:out value= 'value=${user.socialSecurityNumber}'/></c:if>>
            <div id="divPatient" style="display: none">
                <label>Do you want to create a new family?</label><br>
                <input type="radio" id="yes_Family" name="family" value="yes_Family" onclick="formReveal()">
                <label for="yes_Family">Yes</label>
                <input type="radio" id="no_family" name="family" value="no_family" onclick="formReveal()">
                <label for="no_family">No</label><br>
                <div id="familyID" style="display: none">
                <input type="text" id="FamID" class="require-if-active" name="FamID" placeholder="Family ID" pattern="[0-9]+" required <c:if test="${patient.familyID!=null}"><c:out value= 'value=${patient.familyID}'/></c:if>>
                    <c:if test="${notValidFamily != null}"><p> That is not a valid family.</p></c:if>
                </div>
                <div id="familyName" style="display: none">
                <input type="text" id="FamName" class="require-if-active" name="FamName" placeholder="Family Name" pattern="[A-Za-z]+" required <c:if test="${family.familyName!=null}"><c:out value= 'value=${family.familyName}'/></c:if>>

                </div>
                <input type="text" id="PrevFamID" class="require-if-active" name="PrevFamID" placeholder="Previous Family ID" pattern="[0-9]+" required <c:if test="${patient.previousFamilyID!=null}"><c:out value= 'value=${patient.previousFamilyID}'/></c:if>>
                <input type="text" id="P_comID" class="require-if-active" name="pcomID" placeholder="Community ID" pattern="[0-9]+" required <c:if test="${patient.communityID!=null}"><c:out value= 'value=${patient.communityID}'/></c:if>>
                <c:if test="${notValidPCommunity != null}"><p> That is not a valid community.</p></c:if>
                <input type="text" id="P_clinic_ID" class="require-if-active" name="pclinic_ID" placeholder="Clinic ID" pattern="[0-9]+" required <c:if test="${patient.clinicID!=null}"><c:out value= 'value=${patient.clinicID}'/></c:if>>
                <c:if test="${notValidDClinic != null}"><p> That is not a valid clinic.</p></c:if>
            </div>
            <div id="divDoctor" style="display: none">
                <input type="text" id="D_comID" class="require-if-active" name="dcomID" placeholder="Community ID" pattern="[0-9]+" required <c:if test="${doctor.communityID!=null}"><c:out value= 'value=${doctor.communityID}'/></c:if>>
                <c:if test="${notValidDCommunity != null}"><p> That is not a valid community.</p></c:if>
                <input type="text" id="D_clinic_ID" class="require-if-active" name="dclinic_ID" placeholder="Clinic ID" pattern="[0-9]+" required <c:if test="${doctor.clinic_ID!=null}"><c:out value= 'value=${doctor.clinic_ID}'/></c:if>>
                <c:if test="${notValidDClinic != null}"><p> That is not a valid clinic.</p></c:if>
            </div>
            <div id="divCO" style="display: none">
                <input type="text" id="CO_comID" class="require-if-active" name="ccomID" placeholder="Community ID" pattern="[0-9]+" required <c:if test="${community_owner.communityID!=null}"><c:out value= 'value=${community_owner.communityID}'/></c:if>>
                 <c:if test="${notValidCOCommunity != null}"><p> That is not a valid community.</p></c:if>
             </div>

            <div id="divMO" style="display: none">
                <input type="text" id="MO_clinic_ID" class="require-if-active" name="mclinic_ID" placeholder="Clinic ID" pattern="[0-9]+" required <c:if test="${medical_owner.clinicID!=null}"><c:out value= 'value=${medical_owner.clinicID}'/></c:if>>
                <c:if test="${notValidMOClinic != null}"><p> That is not a valid clinic.</p></c:if>
            </div>
            <input type="submit" class="fadeIn fourth" value="Sign Up">
        </form>

        <!-- Remind Passowrd -->
        <div id="formFooter">
            <a class="underlineHover" href="loginPage.jsp">Sign In</a>
        </div>

    </div>
</div>

<script>

    //Password form checking
    var passInput = document.getElementById('password');
    passInput.oninvalid = function (event) {
        event.target.setCustomValidity("Password should be alphanumeric and between 8-15 characters long");
    }
    passInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //Name form checking
    var nameInput = document.getElementById('name');
    nameInput.oninvalid = function (event) {
        event.target.setCustomValidity("Name should contain letters only");
    }
    nameInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //Name form checking
    var birthdateInput = document.getElementById('birthdate');
    birthdateInput.oninvalid = function (event) {
        event.target.setCustomValidity("Birthdate should be in the format yyyy-mm-dd");
    }
    birthdateInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //SSN form checking
    //TODO: add code "pattern="\d{3}-?\d{2}-?\d{4}" required" to element id SSN above once we resolve SSN format in dao and servlet
    var ssnInput = document.getElementById('SSN');
    ssnInput.oninvalid = function (event) {
        event.target.setCustomValidity("SSN should be digits in the format XXX-XX-XXXX");
    }
    ssnInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //famID form checking
    var famIDInput = document.getElementById('FamID');
    famIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Family ID should contain numbers only");
    }
    famIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //prevFamID form checking
    var prevFamIDInput = document.getElementById('PrevFamID');
    prevFamIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Previous Family ID should contain numbers only");
    }
    prevFamIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //TODO: currently adding constraints to these fields breaks patient, as the fields still exist when hidden,
    //TODO: and thus they must still be correctly filled in even though the user will not see them.
    //Once fixed add code "pattern="[0-9]+" required" to all these fields above

    //pComID form checking
    var pComIDInput = document.getElementById('P_comID');
    pComIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Community ID should contain numbers only");
    }
    pComIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //dComID form checking
    var dComIDInput = document.getElementById('D_comID');
    dComIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Community ID should contain numbers only");
    }
    dComIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //dClinicID form checking
    var dClinicIDInput = document.getElementById('D_clinic_ID');
    dClinicIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Clinic ID should contain numbers only");
    }
    dClinicIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //coComID form checking
    var coComIDInput = document.getElementById('CO_comID');
    coComIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Community ID should contain numbers only");
    }
    coComIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //moComID form checking
    var coClinicIDInput = document.getElementById('MO_clinic_ID');
    coClinicIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Clinic ID should contain numbers only");
    }
    coClinicIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

    //pClinicID form checking
    var pClinicIDInput = document.getElementById('P_clinic_ID');
    pClinicIDInput.oninvalid = function (event) {
        event.target.setCustomValidity("Clinic ID should contain numbers only");
    }
    pClinicIDInput.oninput = function (event) {
        event.target.setCustomValidity('');
    }

</script>

</body>
</html>
