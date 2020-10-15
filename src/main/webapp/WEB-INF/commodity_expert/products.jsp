<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/mytaglib.tld" prefix="mytg"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="products"/></title>
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
<%@include file="../../partials/commodityExpertHeader.jspf" %>
<div class="content-form">
    <div class="mb-5">
        <form action="/commodity_expert/products" method="post">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="code"><fmt:message key="code"/></label>
                    <input type="text" name="code" id="code" class="form-control"/>
                </div>
                <div class="form-group col-md-6">
                    <label for="name"><fmt:message key="name"/></label>
                    <input type="text" name="name" id="name" class="form-control"/>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="price"><fmt:message key="price"/><fmt:message key="inCoins"/></label>
                    <input type="number" name="price" id="price" class="form-control"/>
                </div>
                <div class="form-group col-md-6">
                    <label for="amount"><fmt:message key="amount"/><fmt:message key="amountWeight"/></label>
                    <input type="number" name="amount" id="amount" class="form-control"/>
                </div>
            </div>
            <div class="form-row justify-content-center mb-4">
                <div class="col-xl-3 col-xs-offset-3">
                    <label for="measure"><fmt:message key="measure"/></label>
                    <select name="measure" id="measure" class="form-control">
                        <c:forEach var="measure" items="${measures}">
                            <option value="${measure.name()}">
                                <fmt:message key="${measure.name()}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="btn-wrapper">
                <button type="submit" class="btn btn-primary"><fmt:message key="addNewProduct"/></button>
            </div>
        </form>
    </div>
    <table class="table mb-5">
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
        <c:forEach var="product" items="${requestScope.products}">
            <tr>
                <th scope="row"><c:out value="${product.code}"/></th>
                <td><c:out value="${product.name}"/></td>
                <td><mytg:price><c:out value="${product.price}"/></mytg:price></td>
                <td><fmt:message key="${product.measure.name()}"/></td>
                <td><c:out value="${product.amount}"/></td>
                <td>
                    <form action="/commodity_expert/products/<c:out value="${product.id}"/>" method="post"
                          class="form-inline">
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
    <nav>
        <ul class="pagination d-flex justify-content-center">
            <li class="page-item <c:out value="${currentPage <= 1 ? 'disabled' : ''}"/>">
                <a class="page-link"
                   href="/commodity_expert/products?size=<c:out value="${size}"/>&page=<c:out value="${currentPage - 1}"/>">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:if test="${pageNumbers.size() > 0}">
                <c:forEach var="pageNumber" items="${pageNumbers}">
                    <li class="page-item <c:out value="${currentPage == pageNumber ? 'active' : ''}"/>">
                        <a class="page-link"
                           href="/commodity_expert/products?size=<c:out value="${size}"/>&page=<c:out value="${pageNumber}"/>">
                            <c:out value="${pageNumber}"/>
                        </a>
                    </li>
                </c:forEach>
            </c:if>
            <li class="page-item <c:out value="${currentPage >= products.size() ? 'disabled' : ''}"/>">
                <a class="page-link"
                   href="/commodity_expert/products?size=<c:out value="${size}"/>&page=<c:out value="${currentPage + 1}"/>">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>
<%@include file="../../partials/footer.jspf" %>
</body>
</html>