<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Leo
  Date: 22/10/2017
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nuevo Usuario</title>
    <%@ include file="/WEB-INF/vistas/includes/header.jsp" %>
</head>
<body>
    <div class="container">
        <div class="row">
            <%@ include file="/WEB-INF/vistas/includes/navbar.jsp" %>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>Registro de Usuario</h3>
                <form:form action="${pageContext.request.contextPath}/usuario/store" method="POST" modelAttribute="usuario">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <form:input path="nombre" type="text" id="nombre" placeholder="Nombre" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="apellido">Apellido</label>
                        <form:input path="apellido" type="text" id="apellido" placeholder="Apellido" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="barrio">Barrio</label>
                        <form:input path="barrio" type="text" id="barrio" placeholder="Barrio" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="direccion">Direccion</label>
                        <form:input path="direccion" type="text" id="direccion" placeholder="Direccion" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="numero">Numero</label>
                        <form:input path="numero" type="number" id="numero" placeholder="Numero" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="email">Mail</label>
                        <form:input path="email" type="email" id="email" placeholder="Mail" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <form:input path="password" type="password" id="password" placeholder="Password" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="tipo">Tipo de Usuario</label>
                        <form:select path="idTipoUsuario" id="tipo" class="form-control">
                            <option	value="">Selecciona un tipo</option>
                            <c:forEach items="${tipousuarios}" var="tipo">
                                <option value="${tipo.id}">${tipo.nombre}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <button class="btn btn-lg btn-primary" Type="Submit">Guardar</button>
                </form:form>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
</body>
</html>
