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
    <body>
        <jsp:include page="header.jsp"/>
        <div class="container mt-3 mb-3">
            <div class="row m-5 d-flex justify-content-center align-items-center">
                <div class="col-12">
                    <div class="card bg-dark text-white" style="border-radius: 1rem;">
                        <div class="card-body p-4 text-start">
                            <h3><fmt:message key="button.createNewCategory"/></h3>
                            <form class="mt-4" action="createNewCategory" method="post">
                                <c:forEach var="locale" items="${sessionScope.locales}">
                                    <div class="row mb-3">
                                        <label class="col-2 col-form-label" for="localeName + ${locale.id}">${locale.name}</label>
                                        <div class="col-10">
                                            <input class="form-control" id="localeName + ${locale.id}" name="categoryName" type="text" required/>
                                        </div>
                                    </div>
                                </c:forEach>
                                <button class="btn btn-danger mt-2"><fmt:message key="button.create"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
