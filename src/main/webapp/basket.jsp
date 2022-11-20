<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<c:if test="${sessionScope.user == null}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>

<html>
    <body class="background-color">
        <jsp:include page="header.jsp"/>
        <c:if test="${sessionScope.basketsByUser.size() != 0}">
            <div class="container">
                <div class="row">
                    <div class="col-9 mt-3">
                        <div class="card mb-4 border-0">
                            <div class="card-body">
                                <c:forEach var="basket" items="${sessionScope.basketsByUser}" varStatus="loop">
                                    <div class="row">
                                        <div class="col-3">
                                            <img src="images/${basket.product.imageUrl}" class="card-img-top">
                                        </div>
                                        <div class="col-4">
                                            <h4>${basket.product.name}</h4>
                                            <c:if test="${basket.product.isPizza}">
                                                <p>${basket.product.productSize.name} ${basket.product.productSize.size}</p>
                                                <c:if test="${basket.product.basketIngredientDetailList.size() != 0}">
                                                    <div class="dropdown">
                                                        <button type="button" data-bs-toggle="dropdown" class="btn btn-light dropdown-toggle"><fmt:message key="button.addedIngredients"/></button>
                                                        <ul class="dropdown-menu">
                                                            <c:forEach var="ingredientFromBasket" items="${basket.product.basketIngredientDetailList}">
                                                                <li class="dropdown-item">${ingredientFromBasket.additionalIngredient.name}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${!basket.product.isPizza}">
                                                <p>${basket.product.description}</p>
                                            </c:if>
                                        </div>
                                        <div class="col-2 text-center">
                                            <p><fmt:message key="label.quantity"/></p>
                                            <form action="changeProductCountInBasket" method="post">
                                                <input type="hidden" name="basketLoopIndex" value="${loop.index}">
                                                <input type="hidden" name="basketsByUser" value="${sessionScope.basketsByUser}">
                                                <div class="d-flex mb-3" style="max-width: 70px; margin: auto;">
                                                    <input type="number" class="form-control" min="${minProductCountInBasket}" max="${maxProductCountInBasket}" name="newProductCount" value="${basket.count}"/>
                                                </div>
                                                <button class="btn btn-danger" name="basketId" value="${basket.id}"><fmt:message key="button.save"/></button>
                                            </form>
                                        </div>
                                        <div class="col-2 text-center my-auto">
                                            <h6>${basket.product.price} ₸</h6>
                                        </div>
                                        <div class="col-1 my-auto">
                                            <form action="deleteProductFromBasket" method="post">
                                                <input type="hidden" name="basketLoopIndex" value="${loop.index}">
                                                <input type="hidden" name="basketsByUser" value="${sessionScope.basketsByUser}">
                                                <button class="btn btn-light" type="submit" name="basketId" value="${basket.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                                    </svg>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                    <c:if test="${!loop.last}">
                                        <hr class="my-4"/>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-3 mt-3">
                        <div class="sticky-top second-sticky-top" style="z-index: 3;">
                            <div class="card border-0">
                                <div class="card-body">
                                    <h4><fmt:message key="label.totalCost"/> ${sessionScope.totalPrice} ₸</h4>
                                    <form action="checkoutOrder" method="post">
                                        <c:forEach var="deliveryMethodLocale" items="${sessionScope.deliveryMethodLocaleList}">
                                            <div class="form-check">
                                                <input class="form-check-input" id="deliveryMethodLocale + ${deliveryMethodLocale.id}" type="radio" name="deliveryMethodLocaleId" value="${deliveryMethodLocale.id}" required>
                                                <label class="form-check-label" for="deliveryMethodLocale + ${deliveryMethodLocale.id}">${deliveryMethodLocale.name}</label>
                                            </div>
                                        </c:forEach>
                                        <div class="text-center mt-3">
                                            <button class="btn btn-danger" type="submit"><fmt:message key="button.checkoutOrder"/></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </body>
</html>
