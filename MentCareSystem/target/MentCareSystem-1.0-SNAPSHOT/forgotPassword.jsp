<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<style><jsp:directive.include file="CSS/LoginReg.css" /></style>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <!-- Tabs Titles -->
        <div id="formHeaderForgotPassword">
            <h2 class="active underlineHover"> Forgot Password? </h2>
        </div>
        <label>If you have an account with us we will email you a reset password link</label><br>
        <!-- username Form -->
        <form id="form" action="enterEmail">
            <input type="email" id="email" class="fadeIn third" name="email" placeholder="Email" <c:if test="${user.email!=null}"><c:out value= 'value=${user.email}'/></c:if>>
            <input type="submit" class="fadeIn fourth" value="Email Link">
        </form>

        <!-- Remind Passowrd -->
        <div id="formFooter">
            <a class="underlineHover" href="loginPage.jsp">Sign In</a>
        </div>

    </div>
</div>
</body>
</html>