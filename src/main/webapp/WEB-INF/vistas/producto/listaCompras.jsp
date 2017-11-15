<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <h3>Listado de productos comprados: </h3>
            
				<c:forEach items="${listaCompras}" var="listaProductos">
					${listaProductos}<br>
				</c:forEach>
				
			</div>
		</div>
	</div>
	
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>

</body>
</html>