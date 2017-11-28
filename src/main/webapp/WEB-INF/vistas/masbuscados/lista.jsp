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
                <h3>Listado de Productos</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <c:if test="${empty error}">
                    <a href="${pageContext.request.contextPath}/producto/create"><button class="btn btn-lg btn-primary" Type="button">Agregar Producto</button></a>
                </c:if>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 productos">

                <c:if test="${not empty error}">
                    <div class="alert alert-warning">${error}</div>
                </c:if>

                <c:if test="${not empty records}">
                    <div class="table-responsive">
                        <table class="table table-striped borderless">
                            <thead>
                            <tr>
                                <th class="col-md-4">#</th>
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Categoria</th>
                                <th class="col-md-4">Establecimiento</th>
                                <th class="col-md-4">Buscado</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="producto" items="${records}">
                                <tr>
                                    <td><c:out value="${producto.id}"/></td>
                                    <td><c:out value="${producto.nombre}"/></td>
                                    <td><c:out value="${producto.categoria.nombre}"/></td>
                                    <td>
                                        <c:forEach items="${producto.pivotTables}" var="pivot">
                                            ${pivot.establecimiento.nombre} ($${pivot.precio}) -
                                        </c:forEach>
                                    </td>
                                    <td><c:out value="${producto.cantidadBuscado(records, producto)}"/></td>
                                    <td><a href="#"><button type="button" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></button></a></td>
                                    <td><a href="#"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button></a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
</body>
</html>
