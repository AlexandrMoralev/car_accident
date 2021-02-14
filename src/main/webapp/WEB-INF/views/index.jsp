<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Accidents</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>

</head>

<body>
<div class="container">
    <h1>Accidents</h1>
    <br>
    <div class="container-fluid">
        <nav class="navbar navbar-expand navbar-light bg-faded">
            <a class="navbar-brand" href="#">Navbar</a>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="nav navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link disabled" href="/car_accident/">Accidents <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/car_accident/create">Add accident</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/car_accident/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <br>
    <div class="btn-group" role="group">
        <a href="<c:url value='/create'/>" class="btn btn-secondary" role="button">+ Добавить инцидент</a>
    </div>
    <br>
    <table class="table table-hover" id="table">

        <thead>
        <tr>
            <th>name</th>
            <th>type</th>
            <th>rules</th>
            <th>text</th>
            <th>address</th>
        </tr>
        </thead>
        <tbody id="datatable">
        <c:forEach items="${accidents}" var="accident">
            <tr>
                <td><a href="<c:url value='/edit?id=${accident.id}'/>"
                       title="Редактировать инцидент">${accident.name}</a></td>
                <td>${accident.typeName}</td>
                <td>${accident.ruleNames}</td>
                <td>${accident.text}</td>
                <td>${accident.address}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
