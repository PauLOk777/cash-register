<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="activeOrders"/></title>
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
<%@include file="../../partials/cashierHeader.jspf" %>
<div class="content-form">
    <div class="flex-sa mb-5">
        <form action="/cashier/orders" method="post">
            <button type="submit" class="btn btn-primary btn-lg"><fmt:message key="newOrder"/></button>
        </form>
    </div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="creationDate"/></th>
            <th scope="col"><fmt:message key="totalPrice"/></th>
            <th scope="col"><fmt:message key="closeOrder"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <th scope="row">
                    <a href="/cashier/orders/<c:out value="${order.id}"/>"><c:out value="${order.id}"/></a>
                </th>
                <td><c:out value="${order.createDate}"/></td>
                <td class="priceToParse"><c:out value="${order.totalPrice}"/></td>
                <td>
                    <form action="/cashier/orders/close/<c:out value="${order.id}"/>" method="post">
                        <button type="submit" class="btn btn-outline-success btn-sm">
                            <fmt:message key="close"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="../../partials/footer.jspf" %>
</body>
</html>