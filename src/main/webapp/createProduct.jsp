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
<jsp:include page="header.jsp"/>
    <form action="createNewProduct" method="post">
        <input type="text" name="productName" placeholder="Наименование товара" required><br/>
        <input type="file" name="imageUrl"><br/>
        <c:if test="${requestScope.isPizza == false}">
            <textarea name="description" placeholder="Описание" required></textarea><br/>
            <input type="number" name="price" placeholder="Цена" required><br/>
        </c:if>
        <input type="hidden" name="isPizza" value="${requestScope.isPizza}">
        <select name="productCategoryId" required>
            <label>Выбрать категорию</label>
            <c:forEach var="productCategory" items="${requestScope.productCategories}">
                <option name="productCategoryId" value="${productCategory.getProductCategoryId()}">${productCategory.getName()}</option>
            </c:forEach>
        </select><br/>
        <c:if test="${requestScope.isPizza == true}">
            <label>Выберите ингредиенты для пиццы: </label>

            <c:forEach var="ingredient" items="${requestScope.ingredientList}">
                <input type="checkbox" name="selectedIngredientId" value="${ingredient.id}">${ingredient.name}<br/>
            </c:forEach>
            <c:forEach var="productSize" items="${sessionScope.productSizeList}">
                <label for="productSize">${productSize.name}</label>
                <input type="number" min="${minProductPrice}" name="productSizePrice" id="productSize" required/>
            </c:forEach>
        </c:if>
        <button type="submit">Добавить товар</button>
    </form>
</html>
