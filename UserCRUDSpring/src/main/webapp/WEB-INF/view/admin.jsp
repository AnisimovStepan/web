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
            <a class="nav-link active" href="${pageContext.request.contextPath}/admin" style="border-radius: 0" draggable="false">Admin</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/user" style="border-radius: 0" draggable="false">User</a>
        </div>
        <div class="container-fluid bg-light">
            <div style="margin: 2rem">
                <h1 style="margin-bottom: 1rem">Admin panel</h1>

                <nav>
                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="users-table-tab" data-toggle="tab" href="#users-table" role="tab" aria-controls="users-table" aria-selected="true" draggable="false">Users table</a>
                        <a class="nav-item nav-link" id="new-user-tab" data-toggle="tab" href="#new-user" role="tab" aria-controls="new-user" aria-selected="false" draggable="false">New User</a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane show active" id="users-table" role="tabpanel" aria-labelledby="users-table-tab">
                        <div class="card">
                            <h5 class="card-header">All users</h5>
                            <div class="card-body">
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th class="align-middle" scope="col" style="border-top: none">ID</th>
                                        <th class="align-middle" scope="col" style="border-top: none">Login</th>
                                        <%--<th>Password</th>--%>
                                        <th class="align-middle" scope="col" style="border-top: none">Roles</th>
                                        <th class="align-middle" scope="col" style="border-top: none">First name</th>
                                        <th class="align-middle" scope="col" style="border-top: none">Last Name</th>
                                        <th class="align-middle" scope="col" style="border-top: none">Middle name</th>
                                        <th class="align-middle" scope="col" style="border-top: none">Edit</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${userList}" var="user" >
                                            <tr>
                                                <td class="align-middle"><c:out value="${user.id}"/></td>
                                                <td class="align-middle"><c:out value="${user.login}"/></td>
                                                    <%--<td><c:out value="${user.password}"/></td>--%>
                                                <td class="align-middle">
                                                    <c:forEach items="${user.roles}" var="role" ><c:out value="${role.role.name()};"/></c:forEach>
                                                </td>
                                                <td class="align-middle"><c:out value="${user.firstName}"/></td>
                                                <td class="align-middle"><c:out value="${user.lastName}"/></td>
                                                <td class="align-middle"><c:out value="${user.middleName}"/></td>
                                                <td class="align-middle">
                                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#userEditModel" data-whatever="@getbootstrap">Edit</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="new-user" role="tabpanel" aria-labelledby="new-user-tab">
                        <div class="card">
                            <h5 class="card-header">Add new user</h5>
                            <div class="card-body">
                                <form class="container-fluid" style="max-width: 380px;" method="POST" action="${pageContext.request.contextPath}/admin/create-user">
                                    <div class="form-group">
                                        <label for="login">Login</label>
                                        <input type="text" class="form-control" name="login" id="login" value="${user.login}" placeholder="Login" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input type="text" class="form-control" name="password" id="password" value="${user.password}" placeholder="Password" required>
                                    </div>

                                    <div class="form-group">
                                        <label>User role:</label>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="ADMIN" id="admin"
                                                <c:forEach items="${user.roles}" var="role" >
                                                       <c:if test="${role.role.name() == 'ADMIN'}">checked</c:if>
                                                </c:forEach>
                                            >
                                            <label class="form-check-label" for="admin">Admin</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="USER" id="user"
                                                <c:forEach items="${user.roles}" var="role" >
                                                       <c:if test="${role.role.name() == 'USER'}">checked</c:if>
                                                </c:forEach>
                                            >
                                            <label class="form-check-label" for="user">User</label>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="firstName">First name</label>
                                        <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" placeholder="First name" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Last name</label>
                                        <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" placeholder="Last name" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="middleName">Middle name</label>
                                        <input type="text" class="form-control" id="middleName" name="middleName" value="${user.middleName}" placeholder="Middle name">
                                    </div>

                                    <c:if test="${(errorMessage != null) || (errorMessage != '')}">
                                        <div class="invalid-feedback" style="display: block; margin-bottom: 1rem; font-size: 1rem;">${errorMessage}</div>
                                    </c:if>

                                    <input class="btn btn-primary btn-block" type="submit" value="Add new user" draggable="false">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="userEditModel" tabindex="-1" role="dialog" aria-labelledby="userEditModelLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="userEditModelLabel">Edit user</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form class="container-fluid" style="max-width: 380px;" method="POST" action="${pageContext.request.contextPath}/admin/create-user">
                        <div class="form-group">
                            <label for="login">Login</label>
                            <input type="text" class="form-control" name="login" id="loginModal" value="${user.login}" placeholder="Login" required>
                        </div>

                        <div class="form-group">
                            <label>User role:</label>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="ADMIN" id="adminModal"
                                <c:forEach items="${user.roles}" var="role" >
                                       <c:if test="${role.role.name() == 'ADMIN'}">checked</c:if>
                                </c:forEach>
                                >
                                <label class="form-check-label" for="admin">Admin</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="USER" id="userModal"
                                <c:forEach items="${user.roles}" var="role" >
                                       <c:if test="${role.role.name() == 'USER'}">checked</c:if>
                                </c:forEach>
                                >
                                <label class="form-check-label" for="user">User</label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="firstName">First name</label>
                            <input type="text" class="form-control" id="firstNameModal" name="firstName" value="${user.firstName}" placeholder="First name" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last name</label>
                            <input type="text" class="form-control" id="lastNameModal" name="lastName" value="${user.lastName}" placeholder="Last name" required>
                        </div>
                        <div class="form-group">
                            <label for="middleName">Middle name</label>
                            <input type="text" class="form-control" id="middleNameModal" name="middleName" value="${user.middleName}" placeholder="Middle name">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger" id="deleteModal">Delete</button>
                    <input class="btn btn-primary" type="submit" id="saveModel" value="Save" draggable="false">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/static/jquery/jquery-3.0.0.min.js"></script>
<script src="/static/bootstrap/bootstrap.min.js"></script>
<script src="/static/js/common.js"></script>
</body>
</html>

