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
                            <h3 class="fw-bold mb-2 text-uppercase text-center"><fmt:message key="label.signup"/></h3>
                            <form action="signup" method="post">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="email"><fmt:message key="label.email"/></label>
                                    <c:if test="${requestScope.emailFormatIncorrect != null}">
                                        <label class="form-label text-danger" for="email"><fmt:message key="error.email"/></label>
                                    </c:if>
                                    <input class="form-control form-control-lg" type="text" name="email" id="email" required/>
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="firstName"><fmt:message key="label.firstName"/></label>
                                    <input class="form-control form-control-lg" type="text" name="firstName" id="firstName" required/>
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="birthday"><fmt:message key="label.birthday"/></label>
                                    <input class="form-control form-control-lg" type="date" name="birthday" id="birthday" required/>
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="phoneNumber"><fmt:message key="label.phoneNumber"/></label>
                                    <c:if test="${requestScope.phoneNumberFormatIncorrect != null}">
                                        <label class="form-label text-danger" for="phoneNumber"><fmt:message key="error.phoneNumber"/></label>
                                    </c:if>
                                    <input class="form-control form-control-lg" type="text" name="phoneNumber" id="phoneNumber" required/>
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="address"><fmt:message key="label.address"/></label>
                                    <input class="form-control form-control-lg" type="text" name="address" id="address" required/>
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="psw"><fmt:message key="label.password"/></label>
                                    <c:if test="${requestScope.passwordFormatIncorrect != null}">
                                        <label class="form-label text-danger" for="psw"><fmt:message key="error.password"/></label>
                                    </c:if>
                                    <input class="form-control form-control-lg" type="password" name="password" id="psw" required/>
                                </div>
                                <button class="btn btn-danger mt-3"><fmt:message key="button.signup"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
