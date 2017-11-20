<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>Nuevo Establecimiento</title>
    <%@ include file="/WEB-INF/vistas/includes/header.jsp" %>
</head>
<body>
    <div class="container">
        <div class="row">
            <%@ include file="/WEB-INF/vistas/includes/navbar.jsp" %>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>Nuevo Establecimiento</h3>
                <form:form action="${pageContext.request.contextPath}/establecimiento" method="POST" modelAttribute="establecimiento">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <form:input path="nombre" type="text" id="nombre" placeholder="Nombre del producto" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="barrio">Barrio</label>
                        <form:input path="barrio" type="text" id="barrio" placeholder="Nombre del barrio" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="direccion">Direccion</label>
                        <form:input path="direccion" type="text" id="direccion" placeholder="Calle" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="numero">Numero</label>
                        <form:input path="numero" type="number" id="numero" placeholder="Numero de calle" class="form-control" />
                    </div>
                    <button class="btn btn-lg btn-primary" Type="Submit">Guardar</button>
                </form:form>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
</body>
</html>
