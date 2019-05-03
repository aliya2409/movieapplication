<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 01.02.2019
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.javalab.movieapp.Constants" %>
<fmt:setLocale value="${locale}"/>
<html>
<fmt:setBundle basename="locale"/>
<head>
    <title><fmt:message key="movies"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>.card-img {
        max-height: 300px;
        max-width: 200px;
        height: auto;
        width: auto;
    }</style>
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
<c:set var="jspName" value="/listMovie" scope="request"/>
<c:choose>
    <c:when test="${not empty user and user.roleId eq 2}">
        <jsp:include page="adminNavBar.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="clientNavBar.jsp"/>
    </c:otherwise>
</c:choose>
<c:if test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID and requestScope.origin eq Constants.LIST_MOVIE_ACTION}">
    <p>
        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                data-target="#buttoncollapseAdd"
                aria-expanded="false" aria-controls="Collapse">
            <fmt:message key="add"/>
        </button>
    </p>
    <div class="collapse" id="buttoncollapseAdd">
        <div class="form-group">
            <form action="${movieServlet}/addMovie" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="oTitle"><fmt:message key="originalTitle"/>:</label>
                    <input type="text" id="oTitle" class="form-control" name="oTitle" required>
                    <label for="duration"><fmt:message key="duration"/>:</label>
                    <input type="time" class="form-control" id="duration" name="duration" required>
                    <label for="budget"><fmt:message key="budget"/></label>
                    <input name="budget" id="budget" class="form-control" type="number" required/>
                    <label for="datepicker"><fmt:message key="releaseDate"/></label>
                    <input type="text" class="form-control" name="releaseDate" id="datepicker" pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))" required/>
                    <label for="description"><fmt:message key="description"/></label>
                    <input type="text" class="form-control" name="description" id="description"
                           required/>
                    <br>
                    <div class="custom-file mb-3">
                        <input type="file" class="custom-file-input form-control" accept="image/jpeg,image/png"
                               id="imageFileAdd"
                               name="image" required/>
                        <label class="custom-file-label" for="imageFileAdd"><fmt:message key="chooseFile"/></label>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
            </form>
        </div>
    </div>
</c:if>
<c:if test="${empty requestScope.movies}"><h3 align="center"><fmt:message key="nothingFound"/></h3></c:if>
<c:forEach items="${requestScope.movies}" var="movie">
    <div class="container" style="margin-top: 80px">
        <div class="card flex-row flex-wrap">
            <div class="card-header border-0">
                <img class="card-img" src="data:image/jpg;base64,<c:out value='${movie.imageBase64}'/>" alt="">
            </div>
            <div class="card-block px-2">
                <h4 class="card-title"><a href="${movieServlet}/movieInfo?movieId=${movie.id}">
                    <c:choose>
                    <c:when test="${empty movie.translatedTitle}">${movie.originalTitle}</c:when>
                    <c:otherwise>${movie.translatedTitle}</c:otherwise>
                </c:choose>
                </a></h4>
                <p class="card-text"><c:choose>
                    <c:when test="${movie.translatedTitle eq movie.originalTitle or empty movie.translatedTitle}">${movie.duration}</c:when>
                    <c:otherwise>
                        ${movie.originalTitle}, ${movie.duration}
                    </c:otherwise>
                </c:choose>
                </p>
                <p class="card-text"><fmt:message key="releaseDate"/>: ${movie.formattedReleaseDate}</p>
                <p class="card-text">(<c:forEach items="${movie.genres}" var="genre"
                                                 varStatus="loop">${genre}${!loop.last ? ',' : ''}</c:forEach>)</p>
            </div>
        </div>
    </div>
</c:forEach>
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
