<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
<table>
    <tr>
        <th><fmt:message key="label.userId"/></th>
        <th><fmt:message key="label.email"/></th>
        <th><fmt:message key="label.firstName"/></th>
        <th><fmt:message key="label.birthday"/></th>
        <th><fmt:message key="label.phoneNumber"/></th>
        <th><fmt:message key="label.address"/></th>
        <th><fmt:message key="label.isAdmin"/></th>
        <th><fmt:message key="label.isBanned"/></th>
    </tr>
    <tr>
        <td>${sessionScope.userDataForAdmin.getId()}</td>
        <td>${sessionScope.userDataForAdmin.getEmail()}</td>
        <td>${sessionScope.userDataForAdmin.getFirstName()}</td>
        <td>${sessionScope.userDataForAdmin.getBirthday()}</td>
        <td>${sessionScope.userDataForAdmin.getPhoneNumber()}</td>
        <td>${sessionScope.userDataForAdmin.getAddress()}</td>
        <td>${sessionScope.userDataForAdmin.getIsAdmin()}</td>
        <td>${sessionScope.userDataForAdmin.getIsBanned()}</td>
    </tr>
</table>
<form action="changeUserBanStatus" method="post">
    <input type="hidden" name="userId" value="${sessionScope.userDataForAdmin.getId()}">
    <c:choose>
        <c:when test="${sessionScope.userDataForAdmin.isBanned}">
            <input type="submit" value=<fmt:message key="button.unbanUser"/>>
        </c:when>
        <c:otherwise>
            <input type="submit" value=<fmt:message key="button.banUser"/>>
        </c:otherwise>
    </c:choose>
</form>
<form action="getOrders" method="get">
    <button name="userId" value="${sessionScope.userDataForAdmin.getId()}"><fmt:message key="button.getOrders"/></button>
</form>
</body>
</html>
