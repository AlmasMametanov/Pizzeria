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
    <body class="background-color">
        <jsp:include page="header.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <div class="col mt-3 mb-3">
                    <div class="card border-0">
                        <div class="card-body">
                            <c:forEach var="ingredient" items="${sessionScope.additionalIngredientList}" varStatus="loop">
                                <form action="updateIngredient" method="post">
                                    <div class="row">
                                        <div class="col-1 my-auto">
                                            <img src="images/ingredients/${ingredient.imageUrl}" class="card-img-top"><br/>
                                        </div>
                                        <div class="col-3 d-flex flex-column">
                                            <div class="row">
                                                <div class="col">
                                                    <input class="form-control border-0 background-color" style="font-size: 22px; font-weight: bold;" type="text" name="ingredientName" value="${ingredient.getName()}"/>
                                                </div>
                                            </div>
                                            <div class="row mt-auto">
                                                <div class="col">
                                                    <input class="btn btn-sm" type="file" name="imageUrl">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <c:forEach var="productSize" items="${ingredient.getProductSizeList()}">
                                                <div class="row">
                                                    <div class="col-5">
                                                        <p>${productSize.name}</p>
                                                    </div>
                                                    <div class="col-3">
                                                        <p>${productSize.size}</p>
                                                    </div>
                                                    <div class="col-4">
                                                        <input class="form-control border-0 mb-1 background-color" style="height: 30px" type="text" name="ingredientSizePrice" value="${productSize.getProductSizeIngredientDetail().getPrice()}"/>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="col-2 text-center my-auto">
                                            <input type="hidden" name="ingredientLoopIndex" value="${loop.index}">
                                            <button name="ingredientId" value="${ingredient.getId()}" class="btn btn-danger"><fmt:message key="button.save"/></button>
                                        </div>
                                    </div>
                                </form>
                                <form action="changeIngredientActiveStatus">
                                    <input type="hidden" name="ingredientId" value="${ingredient.getId()}">
                                    <c:choose>
                                        <c:when test="${ingredient.getIsActive()}">
                                            <input type="submit" class="btn btn-light background-color" value="<fmt:message key="button.deactivate"/>">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" class="btn btn-light background-color" value="<fmt:message key="button.activate"/>">
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                                <c:if test="${!loop.last}">
                                    <hr class="my-2"/>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
