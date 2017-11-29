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
                    <div class="alert alert-warning">${error}, ingrese uno <a href="${pageContext.request.contextPath}/establecimiento/create" class="alert-link">aqui</a>.</div>
                </c:if>

                <c:if test="${empty error}">
                    <c:if test="${empty records}">
                        <div class="alert alert-warning">No tiene productos agregados aun</div>
                    </c:if>
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
                                <th>Oferta</th>
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
                                    <c:forEach items="${producto.pivotTables}" var="pivot">
                                        <c:set var="en_oferta" value="true" />
                                        <c:if test="${empty pivot.enOferta}">
                                            <c:set var="en_oferta" value="false" />
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${en_oferta == 'true'}">
                                        <form:form action="${pageContext.request.contextPath}/productos/quitaroferta" method="POST" modelAttribute="productoModel">
                                            <form:input path="id" type="hidden" name="id" value="${producto.id}"/>
                                            <td><button type="submit" class="btn btn-danger" title="Quitar oferta"><span class="glyphicon glyphicon-star"></span></button></td>
                                        </form:form>
                                    </c:if>
                                    <c:if test="${en_oferta == 'false'}">
                                        <form:form action="${pageContext.request.contextPath}/productos/marcarofertado" method="POST" modelAttribute="productoModel">
                                            <form:input path="id" type="hidden" name="id" value="${producto.id}"/>
                                            <td><button type="submit" class="btn btn-success" title="Marcar como oferta"><span class="glyphicon glyphicon-star"></span></button></td>
                                        </form:form>
                                    </c:if>

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
