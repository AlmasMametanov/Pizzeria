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
    <body>
        <jsp:include page="header.jsp"/>
        <div class="container mt-3 mb-3">
            <h3><fmt:message key="label.usersProfile"/></h3>
            <table class="table table-striped mt-4 mb-4">
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
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td>${user.getId()}</td>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.birthday}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.address}</td>
                        <td>${user.isAdmin}</td>
                        <td>${user.isBanned}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
