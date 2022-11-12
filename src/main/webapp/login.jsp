<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<c:if test="${sessionScope.user != null}">
    <c:redirect url="pageNotFound.jsp"/>
</c:if>
<html>
<body>
    <jsp:include page="header.jsp"/>
    <div class="container">
            <div class="m-5 row d-flex justify-content-center align-items-center">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card bg-dark text-white" style="border-radius: 1rem;">
                        <div class="card-body p-4 text-start">
                            <h3 class="fw-bold mb-2 text-uppercase text-center">Login</h3>
                            <form action="login" method="post">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="email"><fmt:message key="label.email"/></label>
                                    <input class="form-control form-control-lg" type="email" placeholder="<fmt:message key="label.enterEmail"/>" name="email" id="email" required/>
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="psw"><fmt:message key="label.password"/></label>
                                    <input class="form-control form-control-lg" type="password" placeholder="<fmt:message key="label.enterPassword"/>" name="password" id="psw" required/>
                                </div>
                                <button class="btn btn-danger mt-3" type="submit"><fmt:message key="button.login"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
