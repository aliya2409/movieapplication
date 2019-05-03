<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 06.02.2019
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.javalab.movieapp.Constants" %>
<html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>
<head>
    <title><fmt:message key="people"/></title>
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
<c:choose>
    <c:when test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID and requestScope.origin eq Constants.LIST_PERSON_ACTION}">
        <c:remove var="jspName" scope="request"/>
        <c:set var="jspName" value="/listPerson" scope="request"/>
    </c:when>
    <c:when test="${requestScope.origin ne Constants.LIST_PERSON_ACTION}">
        <c:remove var="jspName" scope="request"/>
        <c:set var="jspName" value="/movieCrewInfo?movieId=${requestScope.movieId}" scope="request"/>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID}">
        <jsp:include page="adminNavBar.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="clientNavBar.jsp"/>
    </c:otherwise>
</c:choose>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://epicblog.net/download/datepicker.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    });
</script>
<div class="row">
    <div class="col"></div>
    <div class="col-8">
        <c:choose>
            <c:when test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID and requestScope.origin eq Constants.LIST_PERSON_ACTION}">
                <div>
                    <p>
                        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                                data-target="#buttoncollapseAdd"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="add"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapseAdd">
                        <form class="form-group" action="${movieServlet}/addPerson" name="add" method="POST"
                              enctype="multipart/form-data">
                            <table>
                                <tr>
                                    <td>
                                        <a><fmt:message key="originalName"/></a>
                                    </td>
                                    <td>
                                        <input type="text" id="oName" class="form-control" name="oName" value=""
                                               required>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a><fmt:message key="originalSurname"/></a>
                                    </td>
                                    <td>
                                        <input type="text" id="oSurname" class="form-control" name="oSurname" value=""
                                               required>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a><fmt:message key="birthDate"/></a>
                                    </td>
                                    <td>
                                        <input type="text" name="birthD" class="datepicker form-control"
                                               pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))" required>
                                        <fmt:message key="inputDateFormat"/>
                                    </td>
                                </tr>
                            </table>
                            <div class="custom-file mb-3 form-control">
                                <input type="file" class="custom-file-input" accept="image/jpeg,image/png"
                                       id="imageFileAdd"
                                       name="image" required/>
                                <label class="custom-file-label" for="imageFileAdd"><fmt:message
                                        key="chooseFile"/></label>
                            </div>
                            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                        </form>
                    </div>
                </div>
            </c:when>
            <c:when test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID and requestScope.origin ne Constants.LIST_PERSON_ACTION}">
                <div>
                    <p>
                        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                                data-target="#buttoncollapseAddCrew"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="add"/>
                        </button>
                    </p>
                    <div class="collapse" id="buttoncollapseAddCrew">
                        <form action="${movieServlet}/addCrewMember" name="add">
                            <input type="hidden" name="movieId" value="${requestScope.movieId}">
                            <input type="hidden" name="location" value="">
                            <a><fmt:message key="originalName"/></a>
                            <br>
                            <input type="text" name="oName" value="" required>
                            <br>
                            <a><fmt:message key="originalSurname"/></a>
                            <br>
                            <input type="text" name="oSurname" value="" required>
                            <br>
                            <strong><fmt:message key="role"/>: </strong>
                            <label for="actor"><fmt:message key="actor"/></label>
                            <input id="actor" name="roleId" type="radio" value="1">
                            <label for="director"><fmt:message key="director"/></label>
                            <input id="director" name="roleId" type="radio" value="2">
                            <label for="writer"><fmt:message key="writer"/></label>
                            <input id="writer" name="roleId" type="radio" value="3">
                            <div class="mt-3">
                                <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:when>
        </c:choose>
        <div class="card-columns">
            <c:forEach items="${requestScope.people}" var="person">
                <div class="card bg-light mb-3" style="width:300px">
                    <img class="card-img-top person-image" id="person-image"
                         style="max-height:300px; max-width:300px; height:auto; width:auto;" align="center"
                         src="data:image/jpg;base64,<c:out value='${person.imageBase64}'/>" alt="Card image">
                    <h4 class="card-title">${person.translatedName} ${person.translatedSurname}</h4>
                    <c:if test="${person.translatedName ne person.originalName and person.translatedSurname ne person.originalSurname}">
                        <a>${person.originalName} ${person.originalSurname}</a></c:if>
                    <p><c:if test="${not empty person.roleId}">
                        <c:choose>
                            <c:when test="${person.roleId eq Constants.PERSON_ROLE_ACTOR}"><fmt:message
                                    key="actor"/></c:when>
                            <c:when test="${person.roleId eq Constants.PERSON_ROLE_DIRECTOR}"><fmt:message
                                    key="director"/></c:when>
                            <c:when test="${person.roleId eq Constants.PERSON_ROLE_WRITER}"><fmt:message key="writer"/></c:when>
                        </c:choose>
                    </c:if></p>
                    <p class="card-text"><strong><fmt:message key="birthDate"/>:</strong> ${person.formattedBirthDate} </p>
                    <c:if test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID and
                    requestScope.origin eq Constants.LIST_PERSON_ACTION}">
                        <a href="${movieServlet}/deletePerson?personId=${person.id}"
                           class="btn btn-danger" roleId="button"><fmt:message
                                key="delete"/></a>
                        <p>
                            <button class="btn btn-primary" type="button" data-toggle="collapse"
                                    data-target="#buttoncollapse${person.id}"
                                    aria-expanded="false" aria-controls="Collapse">
                                <fmt:message key="update"/>
                            </button>
                        </p>
                        <div class="collapse" id="buttoncollapse${person.id}">
                            <div class="card card-block">
                                <form class="form-group" action="${movieServlet}/updatePerson" method="POST"
                                      enctype="multipart/form-data">
                                    <input type="hidden" name="personId" value="${person.id}">
                                    <table>
                                        <tr>
                                            <td>
                                                <a><fmt:message key="originalName"/></a>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="oName"
                                                       value="${person.originalName}" required>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a><fmt:message key="originalSurname"/></a>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="oSurname"
                                                       value="${person.originalSurname}" required>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a><fmt:message key="birthDate"/></a>
                                            </td>
                                            <td>
                                                <input type="text" name="birthD" class="datepicker form-control"
                                                       pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))" required>
                                                <fmt:message key="inputDateFormat"/>
                                            </td>
                                        </tr>
                                    </table>
                                    <div class="custom-file mb-3 form-control">
                                        <input type="file" class="custom-file-input" id="imageFile" name="image"
                                               required/>
                                        <label class="custom-file-label" for="imageFile"><fmt:message
                                                key="chooseFile"/></label>
                                    </div>
                                    <div class="mt-3">
                                        <button type="submit" class="btn btn-primary"><fmt:message
                                                key="submit"/></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID and
                    requestScope.origin ne Constants.LIST_PERSON_ACTION}">
                        <a href="${movieServlet}/deleteCrewMember?personId=${person.id}&movieId=${requestScope.movieId}"
                           class="btn btn-danger" roleId="button"><fmt:message
                                key="delete"/></a>
                    </c:if>
                </div>
            </c:forEach>
        </div>
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
