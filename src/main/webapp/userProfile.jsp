<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<c:if test="${sessionScope.user == null}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<html>
    <body>
    <jsp:include page="header.jsp"/>
        <div class="container">
            <ul class="nav flex-column">
                <h3 class="nav-item mb-3 mt-4"><fmt:message key="label.userProfile"/></h3>
                <li class="nav-item mb-2"><fmt:message key="label.email"/>: ${sessionScope.user.email}</li>
                <li class="nav-item mb-2"><fmt:message key="label.firstName"/>: ${sessionScope.user.firstName}</li>
                <li class="nav-item mb-2"><fmt:message key="label.birthday"/>: ${sessionScope.user.birthday}</li>
                <li class="nav-item mb-2"><fmt:message key="label.phoneNumber"/>: ${sessionScope.user.phoneNumber}</li>
                <li class="nav-item mb-2"><fmt:message key="label.address"/>: ${sessionScope.user.address}</li>
                <li class="nav-item mb-2">
                    <form action="getOrders" method="get">
                        <button class="btn btn-danger"><fmt:message key="button.getOrders"/></button>
                    </form>
                </li>
                <li class="nav-item mb-2">
                    <form action="logout" method="get">
                        <button class="btn btn-danger"><fmt:message key="button.logout"/></button>
                    </form>
                </li>
            </ul>
        </div>
    </body>
</html>
