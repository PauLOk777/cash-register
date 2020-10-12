<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Not found</title>
</head>
<body>
<div class="input-form">
    <h1><fmt:message key="warning"/></h1>
    <h1><fmt:message key="error.page.not.found"/></h1>
</div>
</body>
</html>