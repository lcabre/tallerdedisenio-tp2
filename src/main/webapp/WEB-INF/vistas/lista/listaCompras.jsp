<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de productos comprados</title>
<%@ include file="/WEB-INF/vistas/includes/header.jsp" %>
</head>
<body>

	<div class="container">
    	<div class="row">
            <%@ include file="/WEB-INF/vistas/includes/navbar.jsp" %>
        </div>
        
        <div class="row">
            <div class="col-md-12">
                <h3>Listado de productos: </h3>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<a href="${pageContext.request.contextPath}/busquedas/productos"><button class="btn btn-lg btn-primary" Type="button">Agregar Productos</button></a>
				<c:if test="${not empty lista}">
					<form:form action="${pageContext.request.contextPath}/lista/finalizar" method="POST" cssClass="form-inline">
						<input type="hidden" name="id" value="${lista.id}"/>
						<button class="btn btn-lg btn-success">Guardar Lista</button>
					</form:form>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<c:if test="${not empty error}">
					<div class="alert alert-warning">${error}, ingrese uno <a href="${pageContext.request.contextPath}/busquedas/productos" class="alert-link">aqui</a>.</div>
				</c:if>
				<c:if test="${not empty lista.productos}">
					<div class="table-responsive">
						<table class="table table-striped borderless">
							<thead>
							<tr>
								<th class="col-md-4">#</th>
								<th class="col-md-4">Nombre</th>
								<th class="col-md-4">Categoria</th>
								<th>Agregar</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="producto" items="${lista.productos}">
								<tr>
									<td><c:out value="${producto.id}"/></td>
									<td><c:out value="${producto.nombre}"/></td>
									<td><c:out value="${producto.categoria.nombre}"/></td>
									<form:form action="#" method="POST" modelAttribute="producto">
										<input type="hidden" name="id" value="${producto.id}"/>
										<td><button type="button" class="btn btn-success"><span class="glyphicon glyphicon-trash"></span></button></td>
									</form:form>
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