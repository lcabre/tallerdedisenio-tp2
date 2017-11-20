<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Leo
  Date: 22/10/2017
  Time: 13:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Smart Shop</title>
    <%@ include file="/WEB-INF/vistas/includes/header.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <%@ include file="/WEB-INF/vistas/includes/navbar.jsp" %>
    </div>

    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4 col-sm-offset-3 col-xs-12">
            <h1 class="text-center login-title">Inicio de Sesion</h1>
            <div class="account-wall">
                <img class="profile-img" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120" alt="">
                <form:form action="${pageContext.request.contextPath}/login/validation" method="POST" modelAttribute="usuario" class="form-signin">
                    <form:input path="email" type="email" id="email" placeholder="Mail" class="form-control" />
                    <form:input path="password" type="password" id="password" placeholder="Password" class="form-control" />
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                                ${error}
                        </div>
                    </c:if>
                </form:form>
            </div>
            <a href="${pageContext.request.contextPath}/registro" class="text-center new-account">Registro</a>
        </div>
    </div>

</div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
</body>
</html>