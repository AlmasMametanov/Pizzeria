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
                            <h3><fmt:message key="button.createNewProduct"/></h3>
                            <form class="mt-4" action="createNewProduct" method="post">
                                <div class="row m-3">
                                    <label class="col-3 col-form-label"><fmt:message key="label.productName"/></label>
                                    <div class="col-9">
                                        <input class="form-control" type="text" name="productName" required>
                                    </div>
                                </div>
                                <div class="row m-3">
                                    <label class="col-3 col-form-label"><fmt:message key="label.productImage"/></label>
                                    <div class="col-auto">
                                        <input class="form-control btn-sm" type="file" name="imageUrl" required>
                                    </div>
                                </div>
                                <div class="row m-3">
                                    <label class="col-3 col-form-label"><fmt:message key="label.selectCategory"/></label>
                                    <div class="col-9">
                                    <select class="form-select" name="productCategoryId" required>
                                            <c:forEach var="productCategory" items="${requestScope.productCategories}">
                                                <option name="productCategoryId" value="${productCategory.getProductCategoryId()}">${productCategory.getName()}</option>
                                            </c:forEach>
                                    </select>
                                    </div>
                                </div>
                                <c:if test="${!requestScope.isPizza}">
                                    <div class="row m-3">
                                        <label class="col-3 col-form-label"><fmt:message key="label.productDescription"/></label>
                                        <div class="col-9">
                                            <textarea class="form-control" name="description" required></textarea>
                                        </div>
                                    </div>
                                    <div class="row m-3">
                                        <label class="col-3 col-form-label"><fmt:message key="label.price"/></label>
                                        <div class="col-9">
                                            <input class="form-control" type="number" name="price" required>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${requestScope.isPizza}">
                                    <div class="row row-cols-6 m-3">
                                        <div class="col-3"><fmt:message key="label.selectIngredients"/></div>
                                        <div class="col-9">
                                            <div class="row">
                                                <c:forEach var="ingredient" items="${requestScope.ingredientList}" varStatus="loop">
                                                    <div class="col-auto">
                                                        <input id="ingredient + ${loop.index}" type="checkbox" name="selectedIngredientId" value="${ingredient.id}"/>
                                                        <label for="ingredient + ${loop.index}">${ingredient.name}</label>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12">
                                            <h4 class="text-center"><fmt:message key="label.pizzaPriceDependingOnTheSize"/></h4>
                                            <c:forEach var="productSize" items="${sessionScope.productSizeList}">
                                                <div class="row m-3">
                                                    <label class="col-3 col-form-label">${productSize.name}</label>
                                                    <div class="col-9">
                                                        <input class="form-control" type="number" min="${minProductPrice}" name="productSizePrice" required/>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                                <input type="hidden" name="isPizza" value="${requestScope.isPizza}">
                                <button class="btn btn-danger mt-3"><fmt:message key="button.create"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
