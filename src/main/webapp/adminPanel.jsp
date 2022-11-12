<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="productSizeDAO" class="com.epam.pizzeria.database.dao.impl.ProductSizeDAOImpl"/>
<c:if test="${sessionScope.user == null}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<c:if test="${!sessionScope.user.isAdmin}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<html>
<body>
<jsp:include page="header.jsp"/>
<h2>Админская панель</h2>
<form action="getAllUser" method="get">
    <button><fmt:message key="button.getAllUser"/></button>
</form>

<form action="getUser" method="get">
    <input name="userId" type="text" placeholder="<fmt:message key="label.enterUserId"/>..."/>
    <button><fmt:message key="button.getUser"/></button>
</form>

<form action="createNewCategory" method="post">
    <button><fmt:message key="button.CreateNewCategory"/></button>
</form>

<form action="createNewProduct" method="post">
    <p>Добавить продукт</p>
    <p><input type="radio" name="isPizza" value="0" checked>это другой продукт <input type="radio" name="isPizza" value="1">это пицца</p>
    <button>Добавить новый товар</button>
</form>

<form action="createAdditionalIngredient" method="post">
    <input type="text" name="ingredientName" placeholder="Название ингредиента" required/> <br/>
    <c:forEach var="productSize" items="${productSizeDAO.allProductSize}">
        <label for="ingredient">${productSize.name}</label>
        <input type="hidden" name="productSizeId" value="${productSize.id}"/>
        <input id="ingredient" name="ingredientPrice" type="number" min=1 required/><br/>
    </c:forEach>
    <button>Добавить новый ингредиент</button>
</form>

<form action="getAllProduct" method="post">
    <button>Изменить продукт</button>
</form>
</body>
</html>
