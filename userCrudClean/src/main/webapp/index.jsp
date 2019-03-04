<%--
  Created by Stepan.
  Date: 03.03.2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8">
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
                <a class="change-color-button" href="${pageContext.request.contextPath}/create-user" style="z-index: 10;" draggable="false">Create user</a>
                <div class="header" style="position: absolute; width: 100%; left: 0; z-index: 0">User CRUD</div>
            </div>

            <div class="container-block">
                <table>
                    <tr>
                        <th>Login</th>
                        <th>First name</th>
                        <th>Last Name</th>
                        <th>Middle name</th>
                    </tr>
                    <c:forEach items="${userList}" var="user" >
                        <tr onclick="window.location.href = '${pageContext.request.contextPath}/edit-user?login=${user.login}';">
                            <td>${user.login}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.middleName}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</html>

