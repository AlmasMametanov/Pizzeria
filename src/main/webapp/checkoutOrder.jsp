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
        <div class="container">
            <div class="row">
                <div class="col-6 mt-3 mb-3">
                    <div class="card border-0">
                        <div class="card-body">
                            <c:forEach var="basket" items="${sessionScope.basketsByUser}">
                                <div class="row">
                                    <div class="col-7">
                                        <h5 class="text-start">${basket.product.name}</h5>
                                    </div>
                                    <div class="col-5">
                                        <c:choose>
                                            <c:when test="${basket.count > 1}">
                                                <h5 class="text-end">${basket.count} x ${basket.product.price / basket.count} ₸</h5>
                                            </c:when>
                                            <c:otherwise>
                                                <h5 class="text-end">${basket.product.price} ₸</h5>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:choose>
                                            <c:when test="${basket.product.isPizza}">
                                                <p class="text-muted" style="font-size: 14px;">${basket.product.productSize.name} ${basket.product.productSize.size}<br/>
                                                    <c:if test="${basket.product.basketIngredientDetailList.size() != 0}">
                                                        <fmt:message key="label.added"/>
                                                        <c:forEach var="ingredientFromBasket" items="${basket.product.basketIngredientDetailList}" varStatus="loop">
                                                            <c:choose>
                                                                <c:when test="${!loop.last}">
                                                                    ${ingredientFromBasket.additionalIngredient.name},
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${ingredientFromBasket.additionalIngredient.name}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </c:if>
                                                </p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="text-muted" style="font-size: 14px;">${basket.product.description}</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                            <hr class="my-2"/>
                            <div class="row">
                                <div class="col">
                                    <h5><fmt:message key="label.totalCost"/> ${sessionScope.totalPrice} ₸</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-6 mt-3">
                    <div class="sticky-top second-sticky-top" style="z-index: 3;">
                        <div class="card mb-4 border-0 background-color">
                            <div class="card-body">
                                <h2>${sessionScope.deliveryMethodLocale.name}</h2>
                                <table class="table">
                                    <tr>
                                        <th class="align-middle"><fmt:message key="label.firstName"/></th>
                                        <th class="align-middle ps-4" style="font-weight: normal;">${sessionScope.user.firstName}</th>
                                    </tr>
                                    <tr>
                                        <th class="align-middle"><fmt:message key="label.phoneNumber"/></th>
                                        <th>
                                            <c:if test="${requestScope.phoneNumberFormatIncorrect != null}">
                                                <p class="text-danger"><fmt:message key="error.phoneNumber"/></p>
                                            </c:if>
                                            <form class="my-0 border rounded input-group" action="changeUserPhoneNumberInCheckoutPageAction" method="post">
                                                <input class="form-control border-0" type="text" name="phoneNumber" value="${sessionScope.user.phoneNumber}">
                                                <button class="btn btn-outline-light text-danger"><fmt:message key="button.edit"/></button>
                                            </form>
                                        </th>
                                    </tr>
                                    <c:if test="${sessionScope.deliveryMethodLocale.deliveryMethodId == 2}">
                                        <tr>
                                            <th class="align-middle"><fmt:message key="label.deliveryAddress"/></th>
                                            <th>
                                                <form class="my-0 border rounded input-group" action="changeUserAddressInCheckoutPageAction" method="post">
                                                    <input class="form-control border-0" type="text" name="address" value="${sessionScope.user.address}">
                                                    <button class="btn btn-outline-light text-danger"><fmt:message key="button.edit"/></button>
                                                </form>
                                            </th>
                                        </tr>
                                    </c:if>
                                </table>
                                <form action="createOrder" method="post">
                                    <h2><fmt:message key="label.methodOfPaymentByCardOnSite"/></h2>
                                    <div class="card border-0 background-color">
                                        <div class="card-body w-50">
                                            <div class="row">
                                                <div class="col">
                                                    <c:if test="${requestScope.cardFormatIncorrect != null}">
                                                        <p class="text-danger"><fmt:message key="error.cardNumber"/></p>
                                                    </c:if>
                                                    <input class="form-control" type="text" name="cardNumber" placeholder="<fmt:message key="label.cardNumber"/>"/><br/>
                                                </div>
                                            </div>
                                            <div class="row row-cols-2">
                                                <div class="col-8">
                                                    <c:if test="${requestScope.validityCardFormatIncorrect != null}">
                                                        <p class="text-danger"><fmt:message key="error.validity"/></p>
                                                    </c:if>
                                                    <input class="form-control" type="text" name="validity" placeholder="<fmt:message key="label.validity"/>"/>
                                                </div>
                                                <div class="col-4">
                                                    <c:if test="${requestScope.cvcOfBankCardIncorrect != null}">
                                                        <p class="text-danger"><fmt:message key="error.cvc"/></p>
                                                    </c:if>
                                                    <input class="form-control" type="password" name="cvc" placeholder="<fmt:message key="label.cvc"/>"/><br/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button class="btn btn-danger ms-3"><fmt:message key="button.placeAnOrderFor"/> ${sessionScope.totalPrice} ₸</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
