<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Main</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style>
        <%@include file="/css/style.css"%>
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

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
<div class="text-center content-form">
    <h2>Існують ролі: касир, старший касир, товарознавець.</h2>
    <h3>Товарознавець може створювати товари та зазначати їх кількість на складі.</h3>
    <h3>Касир має можливість:</h3>
    <ul class="list-unstyled">
        <li>створити замовлення (чек);</li>
        <li>додати обрані товари по їх коду або по назві товару у замовлення;</li>
        <li>вказати / змінити кількість певного товару або вагу;</li>
        <li>закрити замовлення (чек).</li>
    </ul>
    <h3>Старший касир має змогу:</h3>
    <ul class="list-unstyled">
        <li>відмінити чек;</li>
        <li>відмінити товар в чеку;</li>
        <li>зробити X і Z звіти.</li>
    </ul>
</div>
<%@include file="partials/footer.jspf" %>
</body>
</html>