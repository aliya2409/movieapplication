<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 07.02.2019
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>
<head>
    <title><fmt:message key="languages"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
<c:remove var="jspName" scope="request"/>
<c:set var="jspName" value="/listLanguage" scope="request"/>
<jsp:include page="adminNavBar.jsp"/>
<div class="row">
    <div class="col"></div>
    <div class="col-8">
        <p>
            <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                    data-target="#buttoncollapseAdd"
                    aria-expanded="false" aria-controls="Collapse">
                <fmt:message key="add"/>
            </button>
        </p>
        <div class="collapse" id="buttoncollapseAdd">
            <form action="${movieServlet}/addLanguage" class="form-group">
                <div class="form-group">
                    <label for="nameAdd"><fmt:message key="name"/></label>
                    <input type="text" class="form-control" id="nameAdd" name="name" value="" required>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
            </form>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th><fmt:message key="name"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.languages}" var="language">
                <tr>
                    <td>${language.name}</td>
                    <td>
                        <p>
                            <button class="btn btn-primary" type="button" data-toggle="collapse"
                                    data-target="#buttoncollapse${language.id}"
                                    aria-expanded="false" aria-controls="Collapse">
                                <fmt:message key="update"/>
                            </button>
                        </p>
                        <div class="collapse" id="buttoncollapse${language.id}">
                            <div class="form-group">
                                <form action="${movieServlet}/updateLanguage" class="form-group">
                                    <input type="hidden" class="form-control" name="languageId" value="${language.id}">
                                    <label for="name"><fmt:message key="name"/></label>
                                    <input type="text" class="form-control" id="name" name="name"
                                           value="${language.name}" required>
                                    <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                                </form>
                            </div>
                        </div>
                    </td>
                    <td>
                        <a href="${movieServlet}/deleteLanguage?languageId=${language.id}" class="btn btn-danger"
                           roleId="button"><fmt:message
                                key="delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td>
                    <p>
                        <button class="btn btn-primary" type="button" data-toggle="collapse"
                                data-target="#buttoncollapse-update-movie"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="updateMovieInfo"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapse-update-movie">
                        <form action="${movieServlet}/updateMovieLocal" class="form-group">
                            <label for="oTitle"><fmt:message key="originalTitle"/></label>
                            <input class="form-control" type="text" id="oTitle" name="oTitle" value="" required>
                            <br>
                            <c:forEach items="${requestScope.languages}" var="language">
                                <label for="${language.name}">${language.name}</label>
                                <input id="${language.name}" name="languageId" type="radio" value="${language.id}">
                            </c:forEach>
                            <br>
                            <label for="translatedTitle"><fmt:message key="translatedTitle"/></label>
                            <input class="form-control" type="text" id="translatedTitle" name="tTitle"
                                   value="" required>
                            <br>
                            <label for="description"><fmt:message key="description"/></label>
                            <input class="form-control" type="text" id="description" name="description" value=""
                                   required>
                            <br>
                            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                        </form>
                    </div>
                </td>
                <td>
                    <p>
                        <button class="btn btn-primary" type="button" data-toggle="collapse"
                                data-target="#buttoncollapse-add-movie"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="addMovieInfo"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapse-add-movie">
                        <form action="${movieServlet}/addMovieLocal" class="form-group">
                            <label for="oTitle"><fmt:message key="originalTitle"/></label>
                            <input class="form-control" type="text" id="oTitle" name="oTitle" value="" required>
                            <br>
                            <c:forEach items="${requestScope.languages}" var="language">
                                <label for="${language.name}">${language.name}</label>
                                <input id="${language.name}" name="languageId" type="radio" value="${language.id}">
                            </c:forEach>
                            <br>
                            <label for="translatedTitle"><fmt:message key="translatedTitle"/></label>
                            <input class="form-control" type="text" id="translatedTitle" name="tTitle"
                                   value="" required>
                            <br>
                            <label for="description"><fmt:message key="description"/></label>
                            <input class="form-control" type="text" id="description" name="description" value=""
                                   required>
                            <br>
                            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <p>
                        <button class="btn btn-primary" type="button" data-toggle="collapse"
                                data-target="#buttoncollapse-update-person"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="updatePersonInfo"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapse-update-person">
                        <form action="${movieServlet}/updatePersonLocal" class="form-group">
                            <label for="oName"><fmt:message key="originalName"/></label>
                            <input class="form-control" type="text" id="oName" name="oName" value="" required>
                            <br>
                            <label for="oSurname"><fmt:message key="originalSurname"/></label>
                            <input class="form-control" type="text" id="oSurname" name="oSurname" value="" required>
                            <br>
                            <c:forEach items="${requestScope.languages}" var="language">
                                <label for="${language.name}">${language.name}</label>
                                <input id="${language.name}" name="languageId" type="radio" value="${language.id}">
                            </c:forEach>
                            <br>
                            <label for="translatedName"><fmt:message key="translatedName"/></label>
                            <input class="form-control" type="text" id="translatedName" name="tName"
                                   value="" required>
                            <br>
                            <label for="tSurname"><fmt:message key="translatedSurname"/></label>
                            <input class="form-control" type="text" id="tSurname" name="tSurname" value=""
                                   required>
                            <br>
                            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                        </form>
                    </div>
                </td>
                <td>
                    <p>
                        <button class="btn btn-primary" type="button" data-toggle="collapse"
                                data-target="#buttoncollapse-add-person"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="addPersonInfo"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapse-add-person">
                        <form class="form-group" action="${movieServlet}/addPersonLocal">
                            <label for="oName"><fmt:message key="originalName"/></label>
                            <input class="form-control" type="text" id="oName" name="oName" value="" required>
                            <br>
                            <label for="oSurname"><fmt:message key="originalSurname"/></label>
                            <input class="form-control" type="text" id="oSurname" name="oSurname" value="" required>
                            <br>
                            <c:forEach items="${requestScope.languages}" var="language">
                                <label for="${language.name}">${language.name}</label>
                                <input id="${language.name}" name="languageId" type="radio" value="${language.id}">
                            </c:forEach>
                            <br>
                            <label for="translatedName"><fmt:message key="translatedName"/></label>
                            <input class="form-control" type="text" id="translatedName" name="tName"
                                   value="" required>
                            <br>
                            <label for="tSurname"><fmt:message key="translatedSurname"/></label>
                            <input class="form-control" type="text" id="tSurname" name="tSurname" value=""
                                   required>
                            <br>
                            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                        </form>
                    </div>
                </td>
            </tr>
        </table>
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
