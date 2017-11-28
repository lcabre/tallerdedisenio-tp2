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
				<c:if test="${not empty error}">
					<div class="alert alert-warning">${error}.</div>
				</c:if>
				<c:if test="${not empty lista}">
					<div class="table-responsive">
						<table class="table table-striped borderless">
							<thead>
							<tr>
								<th class="col-md-4">#</th>
								<th class="col-md-4">Fecha</th>
								<th class="col-md-4">Productos</th>
								<th></th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="lis" items="${lista}">
								<tr>
									<td><c:out value="${lis.id}"/></td>
									<td><c:out value="${lis.fecha}"/></td>
									<td>
										<c:forEach var="producto" items="${lis.productos}">
											${producto.nombre} -
										</c:forEach>
									</td>
									<form:form action="${pageContext.request.contextPath}/historial/buscar" method="POST" modelAttribute="producto">
										<input type="hidden" name="id" value="${lis.id}"/>
										<td><button type="submit" class="btn btn-success">Usar</button></td>
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