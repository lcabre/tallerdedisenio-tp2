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
    <title>Nuevo Producto</title>
    <%@ include file="/WEB-INF/vistas/includes/header.jsp" %>
</head>
<body>
    <div class="container">
        <div class="row">
            <%@ include file="/WEB-INF/vistas/includes/navbar.jsp" %>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>Nuevo Producto</h3>
                <form:form action="${pageContext.request.contextPath}/producto" method="POST" modelAttribute="producto">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <form:input path="nombre" type="text" id="nombre" placeholder="Nombre del producto" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="stock">Stock</label>
                        <form:input path="stock" type="number" id="stock" placeholder="Stock del producto" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="precio">Precio</label>
                        <form:input path="precio" type="number" id="precio" placeholder="Precio del producto" class="form-control" />
                    </div>
                    <button class="btn btn-lg btn-primary" Type="Submit">Guardar</button>
                </form:form>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
</body>
</html>
