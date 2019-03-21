<%--
  Created by Stepan.
  Date: 03.03.2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' >
    <meta name="author" content="Anisimov" />
    <meta name="description" content="User CRUD" />
    <!--<meta http-equiv="X-UA-Compatible" main="IE=edge" />-->
    <meta name="viewport" content="width=device-width,height = device-height, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" type="image/png" href="/static/images/favico.png">
    <link rel="apple-touch-icon-precomposed" href="/static/images/favico.png">

    <%--<link rel="stylesheet" href="static/css/app.css"/>--%>
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css"/>

    <title>User CRUD</title>
</head>
<body ondragstart="" ontouchstart="" onmouseover="">
<noscript>
    You need to enable JavaScript to run this app.
</noscript>
<!-- this is where our React app will render -->
<div id="root">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/" draggable="false">User CRUD</a>
        <div class="collapse navbar-collapse" id="navigation">
            <div class="mr-auto mt-2 mt-lg-0"></div>
            <ul class="navbar-nav my-2 my-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/sign-out" draggable="false">Sign out</a>
                </li>
            </ul>
        </div>
    </nav>
    <div style="display: -webkit-box; display: -ms-flexbox; display: flex;">
        <div class="nav flex-column nav-pills" style="min-height: calc(100vh - 56px); width: 225px; padding-top: 1rem">
            <c:forEach items="${welcomeUser.authorities}" var="authority">
                <c:if test="${authority.authority == 'ADMIN'}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin" style="border-radius: 0" draggable="false">Admin</a>
                </c:if>
            </c:forEach>
            <a class="nav-link active" href="${pageContext.request.contextPath}/user" style="border-radius: 0" draggable="false">User</a>
        </div>
        <div class="container-fluid bg-light">
            <div style="margin: 2rem">
                <h1 style="margin-bottom: 1rem">User page</h1>
                <div>Hello, ${welcomeUser.username}!</div>
            </div>
        </div>
    </div>
    <%--<div class="containerWrapper"></div>--%>
    <%--<div class="container transparent-bg-light">--%>
    <%--<div class="header-wrapper flex-right">--%>
    <%--<a class="change-color-button" href="${pageContext.request.contextPath}/" style="z-index: 10; visibility: hidden" draggable="false">Create user</a>--%>
    <%--<div class="header" style="position: absolute; width: 100%; left: 0; z-index: 0">User CRUD</div>--%>
    <%--</div>--%>

    <%--<div class="flex-center">--%>
    <%--<form class="container-block" method="POST" style="width: 60%;" action="${pageContext.request.contextPath}/sign-in">--%>
    <%--<div class="form__row header-form">--%>
    <%--<svg class="svg-col-mid header-form__icon" viewBox="0 0 22.543 22.997" xmlns="http://www.w3.org/2000/svg" width="100%" height="100%"><path d="M17.801 6.529a6.53 6.53 0 1 1-13.059-.003 6.53 6.53 0 0 1 13.059.003M10.361 22.997H0v-6.431l5.182-1.591zM12.182 22.997h10.361v-6.431l-5.182-1.591zM14.459 14.17l-3.188 1.841-3.187-1.841v4.016l3.187-1.84 3.188 1.84z"></path></svg>--%>
    <%--<span style="vertical-align: middle;">Sign in</span>--%>
    <%--</div>--%>

    <%--<div class="form__row">--%>
    <%--<input class="form-input" type="text" name="login" placeholder="Login" autocorrect="off" autocapitalize="none" autocomplete="login" draggable="false">--%>
    <%--</div>--%>

    <%--<div class="form__row">--%>
    <%--<input class="form-input" type="password" name="password" placeholder="password" autocorrect="off" autocapitalize="none" draggable="false">--%>
    <%--</div>--%>

    <%--<c:if test="${errorMessage != null}">--%>
    <%--<div class="form__row form__error">${errorMessage}</div>--%>
    <%--</c:if>--%>

    <%--<div class="form__row">--%>
    <%--<input class="change-color-button" type="submit" value="Login" draggable="false" style="width: 100%">--%>
    <%--</div>--%>
    <%--</form>--%>
    <%--</div>--%>
    <%--</div>--%>
</div>
<script src="/static/jquery/jquery-3.0.0.min.js"></script>
<script src="/static/bootstrap/bootstrap.min.js"></script>
</body>
</html>

