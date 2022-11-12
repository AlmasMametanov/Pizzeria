<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<c:if test="${sessionScope.user == null}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<html>
<jsp:include page="header.jsp"/>
<form action="createBasket" method="post">
    <label>Добавьте по вкусу: </label>
    <c:forEach var="productSizeIngredientDetail" items="${requestScope.productSizeIngredientDetailList}">
        <input type="checkbox" name="selectedIngredientId" value="${productSizeIngredientDetail.id}">${productSizeIngredientDetail.additionalIngredient.name} - ${productSizeIngredientDetail.price} тенге<br/>
    </c:forEach>
    <input type="hidden" value="${requestScope.productSizeId}" name="productSizeId"/>
    <input type="hidden" value="${requestScope.productId}" name="productId"/>

    <button>Добавить в корзину</button>
</form>
</html>
