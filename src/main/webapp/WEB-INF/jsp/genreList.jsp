<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 05.02.2019
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>
<head>
    <title><fmt:message key="genres"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
<c:remove var="jspName" scope="request"/>
<c:set var="jspName" value="/listGenre" scope="request"/>
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
            <form action="${movieServlet}/addGenre" name="genreAdd">
                <div class="form-group">
                    <label for="nameAdd"><fmt:message key="name"/></label>
                    <input type="text" id="nameAdd" name="name" value="" required>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message
                        key="submit"/></button>
            </form>
        </div>
        <div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th><fmt:message key="name"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${requestScope.genres}" var="genre">
                    <tr>
                        <td>${genre.name}</td>
                        <td>
                            <a href="${movieServlet}/deleteGenre?genreId=${genre.id}" class="btn btn-danger"
                               roleId="button"><fmt:message
                                    key="delete"/></a>
                        </td>
                        <td>
                            <p>
                                <button class="btn btn-primary" type="button" data-toggle="collapse"
                                        data-target="#buttoncollapse${genre.id}"
                                        aria-expanded="false" aria-controls="Collapse">
                                    <fmt:message key="update"/>
                                </button>
                            </p>
                            <div class="collapse" id="buttoncollapse${genre.id}">
                                <div class="card card-block">
                                    <form action="${movieServlet}/updateGenre">
                                        <div class="form-group">
                                            <input type="hidden" name="genreId" value="${genre.id}">
                                            <label for="name"><fmt:message key="name"/></label>
                                            <input class="form-control" type="text" id="name" name="name"
                                                   value="${genre.name}" required>
                                        </div>
                                        <button type="submit" class="btn btn-primary"><fmt:message
                                                key="submit"/></button>
                                    </form>
                                </div>
                            </div>
                        </td>
                        <td>
                            <p>
                                <button class="btn btn-primary" type="button" data-toggle="collapse"
                                        data-target="#buttoncollapse${genre.id}-locale"
                                        aria-expanded="false" aria-controls="Collapse">
                                    <fmt:message key="addGenreLocale"/>
                                </button>
                            </p>
                            <div class="collapse" id="buttoncollapse${genre.id}-locale">
                                <div class="card card-block">
                                    <form action="${movieServlet}/addGenreLocale" class="form-group">
                                            <input type="hidden" name="genreId" value="${genre.id}">
                                            <label for="nameLocale"><fmt:message key="name"/></label>
                                            <input class="form-control" type="text" id="nameLocale" name="name"
                                                   value="" required>
                                        <c:forEach items="${requestScope.languages}" var="language">
                                            <c:if test="${language.id ne sessionScope.languageId}">
                                                <label for="lang${language.id}"><fmt:message key="${language.name}"/></label>
                                                <input id="lang${language.id}" name="languageId" type="radio" value="${language.id}">
                                            </c:if>
                                        </c:forEach>
                                        <button type="submit" class="btn btn-primary"><fmt:message
                                                key="submit"/></button>
                                    </form>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
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
                <fmt:message key="${errorMessage}"/>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>
</body>
</html>