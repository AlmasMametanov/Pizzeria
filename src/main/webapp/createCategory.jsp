<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<c:if test="${sessionScope.user == null}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<c:if test="${!sessionScope.user.isAdmin}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<html>
<style>
    table,th,td {
        border: 1px solid black;
    }
</style>
<body>
<jsp:include page="header.jsp"/>
<form action="/createNewCategory" method="post">
    <c:forEach var="locale" items="${sessionScope.locales}">
        ${locale.name}<input name="categoryName" type="text" required/><br>
    </c:forEach>
    <button type="submit"><fmt:message key="button.createNewCategory"/></button>
</form>
</body>
</html>
