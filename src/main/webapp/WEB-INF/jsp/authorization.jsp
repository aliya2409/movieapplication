<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 25.02.2019
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://epicblog.net/download/datepicker.js"></script>
<script>
    $(function () {
        $("#datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    });
</script>
<script>var check = function () {
    if (document.getElementById('newPass').value ==
        document.getElementById('confirmPass').value) {
        document.getElementById('message').innerHTML = '\u{1F44D}';
    } else {
        document.getElementById('message').innerHTML = '\u{1F44E}';
    }
}</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title><fmt:message key="authorization"/></title>
    <script type="text/javascript">
        $(document).ready(function (){
            var error = '${error}';
            if (error !== null && error !== '') {
                $('#errorModal').modal('show')
            }
        });
    </script>
</head>
<body>
<jsp:include page="${movieapp}/WEB-INF/jsp/clientNavBar.jsp"/>
<div class="row">
<div class="col"></div>
<div class="col-8">
<c:choose>
<c:when test="${empty sessionScope.user}">
    <br>
    <div>
        <p>
            <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                    data-target="#buttoncollapse-login"
                    aria-expanded="false" aria-controls="Collapse">
                <fmt:message key="logInAction"/>
            </button>
        </p>
        <div class="collapse" id="buttoncollapse-login">
            <div class="form-group">
                <form action="${movieServlet}/login" method="post">
                    <div class="form-group">
                        <label for="email"><fmt:message key="email"/>:</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                        <label for="pwd"><fmt:message key="password"/>:</label>
                        <input type="password" class="form-control" id="pwd" name="pass" required>
                    </div>
                    <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                </form>
            </div>
        </div>
    </div>
    <div>
        <p>
            <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                    data-target="#buttoncollapse-register"
                    aria-expanded="false" aria-controls="Collapse">
                <fmt:message key="register"/>
            </button>
        </p>
        <div class="collapse" id="buttoncollapse-register">
            <div class="form-group">
                <form action="${movieServlet}/register" method="post">
                    <table>
                        <tr>
                            <td>
                                <label for="emailR"><fmt:message key="email"/>:</label>
                            </td>
                            <td>
                                <input type="email" id="emailR" class="form-control" name="email" required>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="loginR"><fmt:message key="login"/>:</label>
                            </td>
                            <td>
                                <input type="text" class="form-control" id="loginR" name="login" required>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="newPass"><fmt:message key="newPassword"/></label>
                            </td>
                            <td>
                                <input name="pass" class="form-control" id="newPass" type="password" onkeyup='check();' required/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="confirmPass"><fmt:message key="confirmPassword"/></label>
                            </td>
                            <td>
                                <input type="password" class="form-control" name="confirmPass" id="confirmPass" onkeyup='check();' required/>

                            </td>
                            <td>
                                <span id='message'></span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="datepicker"><fmt:message key="birthDate"/></label>
                            </td>
                            <td>
                                <input type="text" class="form-control" name="birthD" id="datepicker" required/>
                            </td>
                        </tr>
                    </table>
                    <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                </form>
            </div>
        </div>
    </div>
</c:when>
</c:choose>
</div>
<div class="col"></div>
</div>
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle"><fmt:message key="error"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <fmt:message key="${error}"/>
                </div>
                <div class="modal-footer">
                </div>
            </div>
        </div>
    </div>
</body>
</html>
