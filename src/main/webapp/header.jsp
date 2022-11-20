<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="productCategoryDAO" class="com.epam.pizzeria.database.dao.impl.ProductCategoryLocaleDAOImpl"/>

<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
    </head>
    <body>
        <nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="index.jsp"><h5 id="brandColor" style="background-image: url('images/header/italy-flag.png'); background-size: cover">MAMMA MIA</h5></a>
                <ul class="navbar-nav">
                    <c:forEach var="productCategory" items="${productCategoryDAO.getAllProductCategoryLocale(sessionScope.localeId)}">
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp#${productCategory.name}"><h4 id="categoryColor">${productCategory.name}</h4></a>
                        </li>
                        <c:if test="${sessionScope.user.isAdmin}">
                            <li class="nav-item">
                                <form action="deleteCategory" method="post">
                                    <button class="btn btn-light" name="categoryId" value="${productCategory.productCategoryId}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                        </svg>
                                    </button>
                                </form>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
                <ul class="navbar-nav">
                    <c:if test="${sessionScope.user == null}">
                        <li class="nav-item">
                            <form action="login.jsp">
                                <button class="btn btn-light"><fmt:message key="button.login"/></button>
                            </form>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.user.isAdmin}">
                        <li class="nav-item">
                            <form action="adminPanel.jsp">
                                <button class="btn btn-light"><fmt:message key="button.adminPanel"/></button>
                            </form>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <li class="nav-item">
                            <form action="getBasket" method="get">
                                <button class="btn btn-light btn-lg">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" fill="currentColor" class="bi bi-cart3" viewBox="0 0 16 16">
                                        <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .49.598l-1 5a.5.5 0 0 1-.465.401l-9.397.472L4.415 11H13a.5.5 0 0 1 0 1H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l.84 4.479 9.144-.459L13.89 4H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                    </svg>
                                </button>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form action="userProfile.jsp">
                                <button class="btn btn-light"><fmt:message key="button.userProfile"/></button>
                            </form>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <form class="d-flex" action="changeLocale" method="post">
                            <input type="hidden" name="selectedLocale" value="RU" />
                            <button class="btn btn-light">RU</button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form class="d-flex" action="changeLocale" method="post">
                            <input type="hidden" name="selectedLocale" value="EN" />
                            <button class="btn btn-light">EN</button>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    </body>
</html>


