<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
        <div class="container mt-3 mb-5">
            <form action="createBasket" method="post">
                <h3><fmt:message key="label.addToTaste"/></h3>
                <div class="row row-cols-6 mt-3">
                    <c:forEach var="productSizeIngredientDetail" items="${requestScope.productSizeIngredientDetailList}" varStatus="loop">
                        <div class="card border-0 form-check background-color">
                            <input id="defaultCheck + ${loop.index}" class="form-check-input" type="checkbox" name="selectedIngredientId" value="${productSizeIngredientDetail.id}">
                            <label for="defaultCheck + ${loop.index}" class="form-check-label text-center">
                                <img src="images/ingredients/${productSizeIngredientDetail.getAdditionalIngredient().getImageUrl()}" class="card-img-top"><br/>
                                <div class="card-body">
                                    <p class="card-text">${productSizeIngredientDetail.getAdditionalIngredient().getName()}</p>
                                    <h5 class="card-text">${productSizeIngredientDetail.getPrice()} ₸</h5>
                                </div>
                            </label>
                        </div>
                    </c:forEach>
                </div>
                <input type="hidden" value="${requestScope.productSizeId}" name="productSizeId"/>
                <input type="hidden" value="${requestScope.productId}" name="productId"/>
                <button class="btn btn-danger mt-2"><fmt:message key="button.addToBasket"/></button>
            </form>
        </div>
    </body>
</html>
