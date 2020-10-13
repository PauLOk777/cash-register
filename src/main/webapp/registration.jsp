<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="registration"/></title>
    <link rel="stylesheet" th:href="@{/css/style.css}" type="text/css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <style>
        <%@include file="/css/style.css"%>
    </style>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
</head>
<body class="d-flex flex-column min-vh-100">
<%@include file="partials/guestHeader.jspf" %>
<div class="authorization-form">
    <form action="/registration" method="post">
        <div class="form-row">
            <div class="form-group col-12">
                <label for="firstName"><fmt:message key="firstName"/></label>
                <input type="text" name="firstName" id="firstName" class="form-control"/>
            </div>
            <div class="form-group col-12">
                <label for="lastName"><fmt:message key="lastName"/></label>
                <input type="text" name="lastName" id="lastName" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label for="email"><fmt:message key="email"/></label>
            <input type="email" name="email" id="email" class="form-control"/>
        </div>
        <div class="form-row">
            <div class="form-group col-12">
                <label for="username"><fmt:message key="username"/></label>
                <input type="text" name="username" id="username" class="form-control"/>
            </div>
            <div class="form-group col-12">
                <label for="password"><fmt:message key="password"/></label>
                <input type="password" name="password" id="password" class="form-control"/>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-12">
                <label for="phoneNumber"><fmt:message key="phoneNumber"/></label>
                <input type="tel" name="phoneNumber" id="phoneNumber" class="form-control"/>
            </div>
            <div class="form-group col-12">
                <label for="role"><fmt:message key="position"/></label>
                <select name="role" id="role" class="form-control">
                    <c:forEach var="position" items="#{requestScope.positions}">
                        <option value="${position.name()}">
                            <fmt:message key="${position.name()}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="btn-wrapper">
            <button type="submit" class="btn btn-primary"><fmt:message key="registration"/></button>
        </div>
    </form>
</div>
<%@include file="partials/footer.jspf" %>
</body>
</html>