<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <title><fmt:message key="label.pageNotFound"/></title>
    </head>
    <body>
        <h3><fmt:message key="label.pageNotFound"/></h3>
    </body>
</html>
