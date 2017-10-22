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
                <a href="${pageContext.request.contextPath}/producto/create"><button class="btn btn-lg btn-primary" Type="button">Agregar Producto</button></a>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 productos">
                <c:if test="${empty records}">
                    <div class="alert alert-danger">No tiene productos agregados aun</div>
                </c:if>

                <c:if test="${not empty records}">
                    <div class="table-responsive">
                        <table class="table table-striped borderless">
                            <thead>
                            <tr>
                                <th class="col-md-4">#</th>
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Precio</th>
                                <th class="col-md-44">Stock</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="producto" items="${records}">
                                <tr>
                                    <td><c:out value="${producto.id}"/></td>
                                    <td><c:out value="${producto.nombre}"/></td>
                                    <td><c:out value="${producto.precio}"/></td>
                                    <td><c:out value="${producto.stock}"/></td>
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
