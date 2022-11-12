<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<jsp:include page="header.jsp"/>

<ul>
    <c:forEach var="product" items="${sessionScope.productList}">
        <form action="changeProduct">
            <li><input type="text" name="productName" value="${product.name}"></li>
            <img src="images/${product.imageUrl}"><br/>
            <input type="file" name="imageUrl"><br/>
            <c:choose>
                <c:when test="${product.isPizza}">
                    <p>${product.description}</p>
                    <label>Изменить ингредиенты для пиццы: </label>
                    <c:forEach var="productIngredientDetail" items="${product.productIngredientDetailList}">
                        <input type="hidden" name="productIngredientIdList" value="${productIngredientDetail}">
                    </c:forEach>
                    <c:forEach var="ingredient" items="${sessionScope.ingredientList}">
                        <c:choose>
                            <c:when test="${fn:contains(product.productIngredientDetailList, ingredient.id)}">
                                <input type="checkbox" name="selectedIngredientId" value="${ingredient.id}" checked>${ingredient.name}<br/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="selectedIngredientId" value="${ingredient.id}">${ingredient.name}<br/>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                    <c:forEach var="productSizeDetail" items="${product.getProductSizeDetailList()}">
                        <label for="productSizeDetail">${productSizeDetail.getProductSize().getName()}</label>
                        <input type="hidden" name="productSizeDetailId" value="${productSizeDetail.getId()}">
                        <input type="number" min="${minProductPrice}" name="productSizeDetailPrice" id="productSizeDetail" value="${productSizeDetail.getPrice()}" required/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p><input type="text" name="description" value="${product.description}"></p>
                    <p><input type="text" name="price" value="${product.price}"></p>
                </c:otherwise>
            </c:choose>
            <button name="productId" value="${product.id}">Сохранить изменения</button>
        </form>
        <form action="changeProductActiveStatus" method="post">
            <input type="hidden" name="productId" value="${product.getId()}">
            <c:choose>
                <c:when test="${product.getIsActive()}">
                    <input type="submit" value="Деактивировать продукт">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Активировать продукт">
                </c:otherwise>
            </c:choose>
        </form>
    </c:forEach>
</ul>
</html>
