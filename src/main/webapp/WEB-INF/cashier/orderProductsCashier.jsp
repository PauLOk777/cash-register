<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="orderProducts"/></title>
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
    <div class="mb-5">
        <form action="/cashier/orders/<c:out value="${orderId}"/>" method="post">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="productIdentifier"><fmt:message key="codeOrNameOfProduct"/></label>
                    <input type="text" name="productIdentifier" id="productIdentifier" class="form-control"/>
                </div>
                <div class="form-group col-md-6">
                    <label for="amount"><fmt:message key="amount"/></label>
                    <input type="number" name="amount" id="amount" class="form-control" value="1"/>
                </div>
            </div>
            <div class="btn-wrapper">
                <button type="submit" class="btn btn-primary"><fmt:message key="addProduct"/></button>
            </div>
        </form>
    </div>
    <div class="mb-5">
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th scope="col"><fmt:message key="code"/></th>
                <th scope="col"><fmt:message key="name"/></th>
                <th scope="col"><fmt:message key="price"/><fmt:message key="priceBy"/></th>
                <th scope="col"><fmt:message key="measure"/></th>
                <th scope="col"><fmt:message key="amount"/><fmt:message key="amountWeight"/></th>
                <th scope="col"><fmt:message key="setNewAmount"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <th scope="row"><c:out value="${product.value.code}"/></th>
                    <td><c:out value="${product.value.name}"/></td>
                    <td class="priceToParse"><c:out value="${product.value.price}"/></td>
                    <td><fmt:message key="${product.value.measure.name()}"/></td>
                    <td><c:out value="${product.key}"/></td>
                    <td>
                        <form action="/cashier/orders/<c:out value="${orderId}"/>/<c:out value="${product.value.id}"/>"
                              method="post" class="form-inline">
                            <div class="form-group mx-sm-1 input-group-sm">
                                <input type="number" name="amount" class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-info btn-sm"><fmt:message key="set"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="flex-sa">
        <form action="/cashier/orders/close/<c:out value="${orderId}"/>" method="post">
            <button type="submit" class="btn btn-outline-success btn-lg"><fmt:message key="closeOrder"/></button>
        </form>
    </div>
</div>
<%@include file="../../partials/footer.jspf" %>
</body>
</html>