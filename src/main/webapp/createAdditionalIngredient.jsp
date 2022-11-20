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
    <body>
        <jsp:include page="header.jsp"/>
        <div class="container">
            <div class="row m-5 d-flex justify-content-center align-items-center">
                <div class="col-12">
                    <div class="card bg-dark text-white" style="border-radius: 1rem;">
                        <div class="card-body p-4 text-start">
                            <h3><fmt:message key="button.createNewIngredient"/></h3>
                            <form action="createAdditionalIngredient" method="post">
                                <div class="row m-3">
                                    <label class="col-3 col-form-label"><fmt:message key="label.ingredientName"/></label>
                                    <div class="col-9">
                                        <input class="form-control" type="text" name="ingredientName" required>
                                    </div>
                                </div>
                                <div class="row m-3">
                                    <label class="col-3 col-form-label"><fmt:message key="label.ingredientImage"/></label>
                                    <div class="col-auto">
                                        <input class="form-control btn-sm" type="file" name="imageUrl" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <h4 class="text-center"><fmt:message key="label.ingredientPriceDependingOnPizzaSize"/></h4>
                                        <div class="row m-3">
                                            <c:forEach var="productSize" items="${sessionScope.productSizeList}">
                                                <div class="col-4">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <label class="form-label" for="ingredient">${productSize.name}</label>
                                                            <input type="hidden" name="productSizeId" value="${productSize.id}"/>
                                                            <input class="form-control" id="ingredient" name="ingredientPrice" type="number" min=1 required/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <button class="btn btn-danger mt-3"><fmt:message key="button.create"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
