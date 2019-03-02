<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 05.02.2019
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>
<head>
    <title><fmt:message key="users"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://epicblog.net/download/datepicker.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script>var check = function () {
        if (document.getElementById('newPass').value ==
            document.getElementById('confirmPass').value) {
            document.getElementsByClassName('message').innerHTML = '\u{1F44D}';
        } else {
            document.getElementsByClassName('message').innerHTML = '\u{1F44E}';
        }
    }</script>
    <script>
        $(function () {
            $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            var error = '${error}';
            if (error !== null && error !== '') {
                $('#errorModal').modal('show')
            }
        });
    </script>
</head>
<body>
<jsp:include page="adminNavBar.jsp"/>
<p>
    <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
            data-target="#buttoncollapse-add"
            aria-expanded="false" aria-controls="Collapse">
        <fmt:message key="add"/>
    </button>
</p>
<div class="collapse" id="buttoncollapse-add">
    <form action="${movieServlet}/addUser">
        <input type="hidden" name="userId" value="">
        <div class="form-group">
            <table>
                <tr>
                    <td>
                        <fmt:message key="email"/>
                    </td>
                    <td>
                        <input type="email" class="form-control" name="email"
                               value=""
                               required>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="login"/>
                    </td>
                    <td>
                        <input type="text" class="form-control" name="login"
                               value="" required>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="newPassword"/>
                    </td>
                    <td>
                        <input name="pass" type="password" class="form-control" onkeyup='check();' required/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="confirmPassword"/>
                    </td>
                    <td>
                        <input type="password" class="form-control" name="confirmPass" onkeyup='check();'
                               required/><span class='message'></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="birthDate"/>
                    </td>
                    <td>
                        <input type="text" name="birthD" class="datepicker form-control" required/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="role"/>
                    </td>
                    <td>
                        <div class="form-check-inline">
                            <label class="form-check-label"><input
                                    class="form-check-input" id="radio1" name="roleId" type="radio"
                                    value="1"><fmt:message key="user"/></label>

                        </div>
                        <div class="form-check-inline">
                            <label class="form-check-label"> <input
                                    class="form-check-input" id="radio2" name="roleId" type="radio"
                                    value="2"><fmt:message
                                    key="admin"/></label>
                        </div>
                    </td>
                </tr>
            </table>
            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
        </div>
    </form>
</div>
<div class="row">
    <div class="col"></div>
    <div class="col-8">
        <table class="table table-hover">
            <thead>
            <tr>
                <th><fmt:message key="login"/></th>
                <th><fmt:message key="email"/></th>
                <th><fmt:message key="birthDate"/></th>
                <th><fmt:message key="role"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.email}</td>
                    <td>${user.birthDate}</td>
                    <td><fmt:message key="role${user.roleId}"/></td>
                    <td>
                        <a href="${movieServlet}/deleteUser?userId=${user.id}" class="btn btn-danger"
                           roleId="button"><fmt:message
                                key="delete"/></a>
                    </td>
                    <td>
                        <p>
                            <button class="btn btn-primary" type="button" data-toggle="collapse"
                                    data-target="#buttoncollapse${user.id}"
                                    aria-expanded="false" aria-controls="Collapse">
                                <fmt:message key="update"/>
                            </button>
                        </p>
                        <div class="collapse" id="buttoncollapse${user.id}">
                            <form action="${movieServlet}/updateUser" name="userUpdate">
                                <input type="hidden" name="userId" value="${user.id}">
                                <div class="form-group">
                                    <table>
                                        <tr>
                                            <td>
                                                <fmt:message key="email"/>
                                            </td>
                                            <td>
                                                <input type="email" class="form-control" name="email"
                                                       value="${user.email}"
                                                       required>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <fmt:message key="login"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="login"
                                                       value="${user.login}" required>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <fmt:message key="newPassword"/>
                                            </td>
                                            <td>
                                                <input name="pass" type="password" onkeyup='check();' required/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <fmt:message key="confirmPassword"/>
                                            </td>
                                            <td>
                                                <input type="password" name="confirmPass" onkeyup='check();'
                                                       required/><span class='message'></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <fmt:message key="birthDate"/>
                                            </td>
                                            <td>
                                                <input type="text" name="birthD" class="datepicker" required/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <fmt:message key="role"/>
                                            </td>
                                            <td>
                                                <div class="form-check-inline">
                                                    <label class="form-check-label"><input
                                                            class="form-check-input" id="radio1" name="roleId"
                                                            type="radio"
                                                            value="1"><fmt:message key="user"/></label>

                                                </div>
                                                <div class="form-check-inline">
                                                    <label class="form-check-label"> <input
                                                            class="form-check-input" id="radio2" name="roleId"
                                                            type="radio"
                                                            value="2"><fmt:message
                                                            key="admin"/></label>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                                </div>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
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
