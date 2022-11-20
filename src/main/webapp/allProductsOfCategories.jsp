<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<jsp:useBean id="productCategoryDAO" class="com.epam.pizzeria.database.dao.impl.ProductCategoryLocaleDAOImpl"/>
<jsp:useBean id="productDAO" class="com.epam.pizzeria.database.dao.impl.ProductDAOImpl"/>
<jsp:useBean id="productSizeDetailDAO" class="com.epam.pizzeria.database.dao.impl.ProductSizeDetailDAOImpl"/>

<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    </head>
    <body>
        <div class="container mt-4">
            <c:forEach var="productCategory" items="${productCategoryDAO.getAllProductCategoryLocale(sessionScope.localeId)}">
                <section>
                    <div class="row row-cols-1">
                        <h3><a id="${productCategory.name}">${productCategory.name}</a></h3>
                        <c:forEach var="product" items="${productDAO.getAllActiveProductsByCategoryId(productCategory.productCategoryId)}">
                            <div class="col-3 mb-3">
                                <div class="card border-0">
                                    <img src="images/${product.imageUrl}" class="card-img-top"><br/>
                                    <div class="card-body">
                                        <h5 class="card-title">${product.name}</h5>
                                        <p class="card-text">${product.description}</p>
                                    </div>
                                    <div class="card-footer">
                                        <c:choose>
                                            <c:when test="${product.isPizza}">
                                                <form action="selectProductSize" method="post">
                                                    <select name="productSizeId" required>
                                                        <c:forEach var="productSizeDetail" items="${productSizeDetailDAO.getAllProductSizeDetailByProductId(product.id)}">
                                                            <option value="${productSizeDetail.productSizeId}">${productSizeDetail.productSize.name} ${productSizeDetail.productSize.size} - ${productSizeDetail.price} ₸</option>
                                                        </c:forEach>
                                                    </select>
                                                    <c:if test="${sessionScope.user != null}">
                                                        <button class="btn btn-danger mt-3" type="submit" value="${product.id}" name="productId"><fmt:message key="button.select"/></button>
                                                    </c:if>
                                                </form>
                                                <c:if test="${sessionScope.user == null}">
                                                    <form action="login.jsp">
                                                        <button class="btn btn-danger"><fmt:message key="button.select"/></button>
                                                    </form>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <p>${product.price} ₸</p>
                                                <c:if test="${sessionScope.user == null}">
                                                    <form action="login.jsp">
                                                        <button class="btn btn-danger"><fmt:message key="button.addToBasket"/></button>
                                                    </form>
                                                </c:if>
                                                <c:if test="${sessionScope.user != null}">
                                                    <form action="createBasket" method="post">
                                                        <button class="btn btn-danger" type="submit" value="${product.id}" name="productId"><fmt:message key="button.addToBasket"/></button>
                                                    </form>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </c:forEach>
        </div>
    </body>
</html>
