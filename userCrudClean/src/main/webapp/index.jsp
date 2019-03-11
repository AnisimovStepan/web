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
    <link rel="icon" type="image/png" href="static/images/favico.png">
    <link rel="apple-touch-icon-precomposed" href="static/images/favico.png">

    <link rel="stylesheet" href="static/css/app.css"/>

    <title>User CRUD</title>
</head>
<body ondragstart="" ontouchstart="" onmouseover="">
    <noscript>
        You need to enable JavaScript to run this app.
    </noscript>
    <!-- this is where our React app will render -->
    <div id="root">
        <div class="containerWrapper"></div>
        <div class="container transparent-bg-light">
            <div class="header-wrapper flex-right">
                <a class="change-color-button" href="${pageContext.request.contextPath}/" style="z-index: 10; visibility: hidden" draggable="false">Create user</a>
                <div class="header" style="position: absolute; width: 100%; left: 0; z-index: 0">User CRUD</div>
            </div>

            <div class="flex-center">
                <form class="container-block" method="POST" style="width: 60%;" action="${pageContext.request.contextPath}/sign-in">
                    <div class="form__row header-form">
                        <svg class="svg-col-mid header-form__icon" viewBox="0 0 22.543 22.997" xmlns="http://www.w3.org/2000/svg" width="100%" height="100%"><path d="M17.801 6.529a6.53 6.53 0 1 1-13.059-.003 6.53 6.53 0 0 1 13.059.003M10.361 22.997H0v-6.431l5.182-1.591zM12.182 22.997h10.361v-6.431l-5.182-1.591zM14.459 14.17l-3.188 1.841-3.187-1.841v4.016l3.187-1.84 3.188 1.84z"></path></svg>
                        <span style="vertical-align: middle;">Sign in</span>
                    </div>

                    <div class="form__row">
                        <input class="form-input" type="text" name="login" placeholder="Login" autocorrect="off" autocapitalize="none" autocomplete="login" draggable="false">
                    </div>

                    <div class="form__row">
                        <input class="form-input" type="password" name="password" placeholder="password" autocorrect="off" autocapitalize="none" draggable="false">
                    </div>

                    <c:if test="${errorMessage != null}">
                        <div class="form__row form__error">${errorMessage}</div>
                    </c:if>

                    <div class="form__row">
                        <input class="change-color-button" type="submit" value="Login" draggable="false" style="width: 100%">
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

