<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 02.02.2019
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="movieServlet" scope="session" value="/movieapp"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://epicblog.net/download/datepicker.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>
<head>
    <title><fmt:message key="home"/></title>
    <script type="text/javascript">
        $(document).ready(function () {
            var error = '${error}';
            if (error !== null && error !== '') {
                $('#errorModal').modal('show')
            }
        });
    </script>
</head>
<body style="text-align: center">
<c:remove var="jspName" scope="request"/>
<c:set var="jspName" value="/homePage.jsp" scope="request"/>
<c:choose>
    <c:when test="${not empty user and user.roleId eq 2}">
        <jsp:include page="WEB-INF/jsp/adminNavBar.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="WEB-INF/jsp/clientNavBar.jsp"/>
    </c:otherwise>
</c:choose>
<div class="row">
    <div class="col"></div>
    <div class="col-8">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <h1 align="center"><fmt:message key="welcome"/>, ${user.login}! </h1>
                <div class="personal-data">
                    <h2><fmt:message key="personalData"/></h2>
                    <table class="table table-hover">
                        <tr>
                            <td><fmt:message key="login"/></td>
                            <td>${user.login}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="birthDate"/></td>
                            <td>${user.birthDate}</td>
                        </tr>
                    </table>
                </div>
                <div>
                    <p>
                        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                                data-target="#buttoncollapse"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="updateInfo"/>
                        </button>
                    </p>
                    <div class="collapse form-group" id="buttoncollapse">
                        <div class="form-group">
                            <form action="${movieServlet}/updateUserInfo" method="POST">
                                <input type="hidden" name="userId" value="${user.id}">
                                <div class="form-group">
                                    <label for="login"><fmt:message key="login"/></label>
                                    <input type="text" id="login" name="login" value="${user.login}" required>
                                </div>
                                <div class="form-group">
                                    <label for="datepicker"><fmt:message key="birthDate"/></label>
                                    <input type="text" name="birthD" id="datepicker"
                                           pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))" required>
                                    <fmt:message key="inputDateFormat"/>
                                </div>
                                <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                            </form>
                        </div>
                    </div>
                </div>
                <div>
                    <p>
                        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                                data-target="#buttoncollapse-password"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="updatePass"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapse-password">
                        <div class="form-group">
                            <form action="${movieServlet}/updatePass" method="post">
                                <input type="hidden" name="userId" value="${user.id}">
                                <div class="form-group">
                                    <label for="pass"><fmt:message key="password"/></label>
                                    <input name="pass" id="pass" type="password" required>
                                </div>
                                <div class="form-group">
                                    <label for="newPass"><fmt:message key="newPassword"/></label>
                                    <input name="newPass" id="newPass" type="password" onkeyup='check();' required/>
                                </div>
                                <div class="form-group">
                                    <label for="confirmPass"><fmt:message key="confirmPassword"/></label>
                                    <input type="password" name="confirmPass" id="confirmPass" onkeyup='check();'
                                           required/>
                                    <span id='message'></span>
                                </div>
                                <button type="submit" class="btn btn-primary"><fmt:message key="updatePass"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <h1 align="center"><fmt:message key="welcome"/>, <fmt:message key="guest"/>! </h1>
                <div class="container"><img src="/resources/guest.jpg" style="max-width: 100%; max-height: 100%;"></div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col"></div>
</div>
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
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
