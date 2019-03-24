<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 24.01.2019
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>
<%@ page import="com.javalab.movieapp.Constants" %>
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
<html>
<head>
    <title><fmt:message key="movieInfo"/></title>
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
    <c:when test="${not empty user and user.roleId eq Constants.ADMIN_ROLE_ID}">
        <jsp:include page="adminNavBar.jsp">
            <jsp:param name="jspName" value="/movieInfo?movieId=${movie.id}"/>
        </jsp:include>
    </c:when>
    <c:otherwise>
        <jsp:include page="clientNavBar.jsp"/>
    </c:otherwise>
</c:choose>
<div class="row">
    <div class="col"></div>
    <div class="col-8">
<c:if test="${not empty requestScope.movie}">
    <div class="card flex-row flex-wrap">
        <div class="card-header border-0">
            <img class="card-img" src="data:image/jpg;base64,<c:out value='${movie.imageBase64}'/>" alt="">
        </div>
        <div class="card-block px-2">
            <h3 class="card-title"><c:choose>
                <c:when test="${empty movie.translatedTitle}">${movie.originalTitle}</c:when>
                <c:otherwise>${movie.translatedTitle}</c:otherwise>
            </c:choose></h3>
            <c:if test="${movie.translatedTitle ne movie.originalTitle and not empty movie.translatedTitle}"><p
                    class="card-text">${movie.originalTitle}</p></c:if>
            <p class="card-text"><strong><fmt:message key="releaseDate"/>: </strong>${movie.formattedReleaseDate}</p>
            <p class="card-text"><strong><fmt:message key="budget"/>: </strong>$ ${movie.formattedBudget}</p>
            <p class="card-text"><strong><fmt:message key="duration"/>: </strong>${movie.duration}</p>
            <c:if test="${not empty movie.rating}"><p class="card-text"><strong><fmt:message
                    key="rating"/>: </strong>${movie.rating}</p></c:if>
            <p class="card-text"><strong><fmt:message key="genres"/>: </strong>
                <c:forEach items="${movie.genres}" var="genre"
                           varStatus="loop">${genre}${!loop.last ? ',' : ''}</c:forEach>
            </p>
            <p>
                <a href="${movieServlet}/movieCrewInfo?movieId=${movie.id}" class="btn btn-primary"
                   roleId="button"><fmt:message
                        key="crewAndCast"/>
                </a>
            </p>
            <c:choose>
                <c:when test="${not empty sessionScope.user and user.roleId eq Constants.ADMIN_ROLE_ID}">
                    <p>
                    <div class="btn-group">
                        <button class="btn btn-primary" type="button" data-toggle="collapse"
                                data-target="#buttoncollapseUpdate"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="update"/>
                        </button>

                        <button class="btn btn-primary" type="button" data-toggle="collapse"
                                data-target="#buttoncollapseAdd"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="addGenre"/>
                        </button>
                    </div>
                    </p>
                    <div class="collapse" id="buttoncollapseUpdate">
                        <div class="form-group">
                            <form action="${movieServlet}/updateMovie" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="movieId" value="${movie.id}">
                                <div class="form-group">
                                    <label for="oTitle"><fmt:message key="originalTitle"/>:</label>
                                    <input type="text" id="oTitle" class="form-control" name="oTitle" required>
                                </div>
                                <div class="form-group">
                                    <label for="duration"><fmt:message key="duration"/>:</label>
                                    <input type="time" class="form-control" id="duration" name="duration" required>
                                </div>
                                <div class="form-group">
                                    <label for="budget"><fmt:message key="budget"/></label>
                                    <input name="budget" id="budget" type="number" required/>
                                </div>
                                <div class="form-group">
                                    <label for="datepicker"><fmt:message key="releaseDate"/></label>
                                    <input type="text" name="releaseDate" id="datepicker" required/>
                                </div>
                                <div class="custom-file mb-3">
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
                    <div class="collapse" id="buttoncollapseAdd">
                        <form action="${movieServlet}/addMovieGenre" name="genreAdd">
                            <input type="hidden" name="movieId" value="${movie.id}">
                            <div class="form-group">
                                <label for="nameAdd"><fmt:message key="name"/></label>
                                <input type="text" class="form-control" id="nameAdd" name="name" value="" required>
                            </div>
                            <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                        </form>
                    </div>
                    <p>
                        <button class="btn btn-danger" type="button" data-toggle="collapse"
                                data-target="#buttoncollapseDelete"
                                aria-expanded="false" aria-controls="Collapse">
                            <fmt:message key="deleteGenre"/>
                        </button>
                    <div class="collapse" id="buttoncollapseDelete">
                        <c:forEach items="${movie.genres}" var="genre">
                            <button onclick="location.href='${movieServlet}/deleteMovieGenre?genreId=${genre.id}&movieId=${movie.id}'"
                                    type="button"
                                    class="btn btn-danger"> ${genre.name}</button>
                        </c:forEach>
                    </div>
                    </p>
                </c:when>
                <c:when test="${not empty sessionScope.user and user.roleId eq '1'}">
                    <c:if test="${ empty movie.liked or movie.liked eq false}">
                        <a href="${movieServlet}/likeMovie?movieId=${movie.id}"
                           class="btn">❤
                        </a>
                    </c:if>
                    <c:if test="${ movie.liked eq true}">
                        <a href="${movieServlet}/unlikeMovie?movieId=${movie.id}"
                           class="btn">💔</a>
                    </c:if>
                    <form action="${movieServlet}/rateMovie">
                        <input type="hidden" name="location"
                               value="${requestScope['javax.servlet.forward.request_uri']}">
                        <input type="hidden" name="movieId" value="${movie.id}">
                        <input type="range" name="rate" id="rateInput" value="5" min="1" max="10"
                               oninput="rateOutput.value = rateInput.value">
                        <output name="rateOutputName" id="rateOutput">${movie.rate}</output>
                        <input type="submit" value="<fmt:message key="rate"/>">
                    </form>
                </c:when>
            </c:choose>
        </div>
    </div>
    </div>
    <div class="col"></div>
    </c:if>
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
