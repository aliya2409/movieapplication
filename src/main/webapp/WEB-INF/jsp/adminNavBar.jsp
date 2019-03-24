<%--
  Created by IntelliJ IDEA.
  User: Dauren-PC
  Date: 26.01.2019
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="${movieServlet}/link?path=homePage.jsp"><fmt:message
                        key="home"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${movieServlet}/listUser"><fmt:message key="users"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${movieServlet}/listMovie"><fmt:message key="movies"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${movieServlet}/listPerson"><fmt:message key="people"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${movieServlet}/listGenre"><fmt:message key="genres"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${movieServlet}/listLanguage"><fmt:message key="languages"/></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                    <fmt:message key="changeLocale"/>
                </a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${movieServlet}/changeLocale?languageId=1&location=${jspName}">English</a>
                    <a class="dropdown-item" href="${movieServlet}/changeLocale?languageId=2&location=${jspName}">Русский</a>
                </div>
            </li>
            <li>
                <a class="nav-link" href="${movieServlet}/logout"><fmt:message key="logout"/></a>
            </li>
        </ul>
        <form action="${movieServlet}/search" class="navbar-form navbar-left" method="POST">
            <input type="hidden" name="action" value="searchMovie">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="<fmt:message key="search"/>" name="search">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">🔍
                    </button>
                </div>
            </div>
        </form>
    </div>
</nav>