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
        <div class="container-fluid">
            <div class="row">
                <div class="col mt-3 mb-3">
                    <div class="card border-0">
                        <div class="card-body">
                            <c:forEach var="product" items="${sessionScope.productList}" varStatus="loop1">
                                <form action="changeProduct" method="post">
                                    <div class="row">
                                        <div class="col-3">
                                            <img src="images/${product.imageUrl}" class="card-img-top">
                                        </div>
                                        <div class="col-9">
                                            <div class="row m-3">
                                                <div class="col">
                                                    <input class="form-control" style="font-size: 22px; font-weight: bold;" type="text" name="productName" value="${product.name}">
                                                </div>
                                            </div>
                                            <div class="row m-3">
                                                <label class="col-4 col-form-label" style="font-size: 18px; font-weight: bold;"><fmt:message key="label.changeImage"/></label>
                                                <div class="col-auto">
                                                    <input class="form-control btn-sm" type="file" name="imageUrl">
                                                </div>
                                            </div>
                                            <c:choose>
                                                <c:when test="${!product.isPizza}">
                                                    <div class="row m-3">
                                                        <label class="col-4 col-form-label" style="font-size: 18px; font-weight: bold;"><fmt:message key="label.changeProductDescription"/></label>
                                                        <div class="col-8">
                                                            <textarea class="form-control" name="description">${product.description}</textarea>
                                                        </div>
                                                    </div>
                                                    <div class="row m-3">
                                                        <label class="col-4 col-form-label" style="font-size: 18px; font-weight: bold;"><fmt:message key="label.changePrice"/></label>
                                                        <div class="col-8 my-auto">
                                                            <input class="form-control" type="text" name="price" value="${product.price}"/>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="row m-3">
                                                        <label class="col-4 col-form-label" style="font-size: 18px; font-weight: bold;"><fmt:message key="label.productDescription"/></label>
                                                        <div class="col-8">
                                                            <p>${product.description}</p>
                                                        </div>
                                                    </div>
                                                    <c:forEach var="productIngredientDetail" items="${product.productIngredientDetailList}">
                                                        <input type="hidden" name="productIngredientIdList" value="${productIngredientDetail}">
                                                    </c:forEach>
                                                    <div class="row m-3">
                                                        <div class="col-4" style="font-size: 18px; font-weight: bold;"><fmt:message key="label.changePizzaIngredients"/></div>
                                                        <div class="col-8">
                                                            <div class="row">
                                                                <c:forEach var="ingredient" items="${sessionScope.ingredientList}" varStatus="loop">
                                                                    <div class="col-auto">
                                                                        <c:choose>
                                                                            <c:when test="${product.productIngredientDetailList.contains(ingredient.id)}">
                                                                                <input id="ingredient + ${loop.index}" type="checkbox" name="selectedIngredientId" value="${ingredient.id}" checked>
                                                                                <label for="ingredient + ${loop.index}">${ingredient.name}</label>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input id="ingredient + ${loop.index}" type="checkbox" name="selectedIngredientId" value="${ingredient.id}">
                                                                                <label for="ingredient + ${loop.index}">${ingredient.name}</label>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row m-3">
                                                        <p class="text-center" style="font-size: 18px; font-weight: bold;"><fmt:message key="label.pizzaPriceDependingOnTheSize"/></p>
                                                        <c:forEach var="productSizeDetail" items="${product.getProductSizeDetailList()}" varStatus="loop">
                                                            <div class="col-4">
                                                                <label>${productSizeDetail.getProductSize().getName()}</label>
                                                                <input type="hidden" name="productSizeDetailId" value="${productSizeDetail.getId()}">
                                                                <input class="form-control" type="number" min="${minProductPrice}" name="productSizeDetailPrice" value="${productSizeDetail.getPrice()}"/>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col d-flex flex-row-reverse">
                                            <button class="btn btn-danger" name="productId" value="${product.id}"><fmt:message key="button.save"/></button>
                                        </div>
                                    </div>
                                </form>
                                <div class="row">
                                    <div class="col d-flex flex-row-reverse">
                                        <form action="changeProductActiveStatus" method="post">
                                            <input type="hidden" name="productId" value="${product.getId()}">
                                            <c:choose>
                                                <c:when test="${product.getIsActive()}">
                                                    <input class="btn btn-danger end" type="submit" value="<fmt:message key="button.deactivate"/>">
                                                </c:when>
                                                <c:otherwise>
                                                    <input class="btn btn-danger" type="submit" value="<fmt:message key="button.activate"/>">
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </div>
                                </div>
                                <c:if test="${!loop1.last}">
                                    <hr class="my-4"/>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
