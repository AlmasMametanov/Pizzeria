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
    <div class="row">
        <div class="col mt-3 mb-3">
            <div class="card border-0 background-color">
                <div class="card-body">
                    <c:forEach var="order" items="${requestScope.orders}" varStatus="loop">
                        <div class="row">
                            <div class="col-8">
                                <h3><fmt:message key="label.orderNumber"/> ${order.getId()}</h3>
                                <table class="table">
                                    <tr>
                                        <th class="align-middle"><fmt:message key="label.orderStatus"/></th>
                                        <th>
                                            <c:choose>
                                                <c:when test="${sessionScope.user.isAdmin}">
                                                    <form class="my-0 border rounded input-group" action="changeOrderStatus" method="post">
                                                        <select class="form-control" name="statusId">
                                                            <c:forEach var="status" items="${requestScope.statusLocaleList}">
                                                                <c:choose>
                                                                    <c:when test="${status.getStatusId() == order.getStatusId()}">
                                                                        <option value="${status.getStatusId()}" selected>${status.getName()}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${status.getStatusId()}">${status.getName()}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="hidden" name="userId" value="${requestScope.userId}">
                                                        <input type="hidden" name="orderId" value="${order.getId()}"/>
                                                        <button class="btn btn-danger" type="submit"><fmt:message key="button.edit"/></button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    ${order.getStatusLocale().getName()}
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th class="align-middle"><fmt:message key="label.orderDate"/></th>
                                        <th>${order.getDateStart()}</th>
                                    </tr>
                                    <tr>
                                        <th class="align-middle"><fmt:message key="label.orderDateFinish"/></th>
                                        <th>${order.getReadyIn()}</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="card border-0">
                                    <div class="card-body">
                                        <div class="row">
                                            <c:forEach var="orderDetail" items="${order.getOrderDetailList()}" varStatus="loop2">
                                                <div class="col-2">
                                                    <img src="images/${orderDetail.getProduct().getImageUrl()}" class="card-img-top">
                                                </div>
                                                <div class="col-6">
                                                    <h4>${orderDetail.getProduct().getName()}</h4>
                                                    <c:if test="${orderDetail.getProduct().getIsPizza()}">
                                                        <p class="text-muted">${orderDetail.getProductSize().getName()} ${orderDetail.getProductSize().getSize()}<br/>
                                                            <c:if test="${orderDetail.getOrderIngredientDetailList().size() != 0}">
                                                                Добавлено:
                                                                <c:forEach var="ingredient" items="${orderDetail.getOrderIngredientDetailList()}" varStatus="loop3">
                                                                    <c:choose>
                                                                        <c:when test="${!loop3.last}">
                                                                            ${ingredient.getProductSizeIngredientDetail().getAdditionalIngredient().getName()},
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            ${ingredient.getProductSizeIngredientDetail().getAdditionalIngredient().getName()}
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </c:if>
                                                        </p>
                                                    </c:if>
                                                </div>
                                                <div class="col-2 text-center">
                                                    <p>Quantity</p>
                                                    <p>${orderDetail.getCount()}</p>
                                                </div>
                                                <div class="col-2 text-center">
                                                    <p>${orderDetail.getPrice()} тенге</p>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h3 class="mt-3"><fmt:message key="label.totalCost"/> ${order.getTotalPrice()} тенге</h3>
                        </div>
                        <c:if test="${!loop.last}">
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