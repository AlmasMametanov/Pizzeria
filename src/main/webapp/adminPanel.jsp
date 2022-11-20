<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="container mt-3 mb-5">
            <h2><fmt:message key="label.adminPanel"/></h2>
            <form class="mt-5" action="getUser" method="get">
                <div class="input-group">
                    <input class="form-control" name="userId" type="text" placeholder="<fmt:message key="label.enterUserId"/>..."/>
                    <button class="btn btn-danger"><fmt:message key="button.getUser"/></button>
                </div>
            </form>
            <hr class="my-2"/>
            <form action="createNewProduct" method="post">
                <h4><fmt:message key="button.createNewProduct"/></h4>
                <div class="form-check">
                    <input class="form-check-input" id="anotherProduct" type="radio" name="isPizza" value="0" checked>
                    <label class="form-check-label" for="anotherProduct"><fmt:message key="label.anotherProduct"/></label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" id="isPizza" type="radio" name="isPizza" value="1">
                    <label class="form-check-label" for="isPizza"><fmt:message key="label.isPizza"/></label>
                </div>
                <button class="btn btn-danger mt-2"><fmt:message key="button.create"/></button>
            </form>
            <hr class="my-2"/>
            <form action="createAdditionalIngredient" method="post">
                <button class="btn btn-danger mt-3"><fmt:message key="button.createNewIngredient"/></button>
            </form>
            <form action="createNewCategory" method="post">
                <button class="btn btn-danger mt-3"><fmt:message key="button.createNewCategory"/></button>
            </form>
            <form action="getAllUser" method="get">
                <button class="btn btn-danger mt-3"><fmt:message key="button.getAllUser"/></button>
            </form>
            <form action="getAllAdditionalIngredient" method="post">
                <button class="btn btn-danger mt-3"><fmt:message key="button.getAllIngredient"/></button>
            </form>
            <form action="getAllProduct" method="post">
                <button class="btn btn-danger mt-3"><fmt:message key="button.getAllProduct"/></button>
            </form>
        </div>
    </body>
</html>
