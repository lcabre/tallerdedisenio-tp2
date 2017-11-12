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
                <h3>Listado de establecimientos mas Cercanos TEST</h3>
                <c:forEach var="establecimiento" items="${records}">
                    <ul>
                        <li>ID: <c:out value="${establecimiento.id}"/> - Nombre: <c:out value="${establecimiento.nombre}"/></li>
                        <c:forEach items="${establecimiento.getProductosBuscados()}" var="producto" varStatus="rowStatus">
                            <ul>
                                <li>Producto: <c:out value="${producto.nombre}"/> - Precio: <c:out value="$${producto.getPrecioEnEstablecimiento()}"/></li>
                            </ul>
                        </c:forEach>
                    </ul>

                </c:forEach>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/busquedas/cercania"><button class="btn btn-lg btn-primary" Type="button">Búsqueda por Cercanía</button></a>
            </div>
        </div>
                
        <div class="row">
            <div class="col-md-12 productos">
				<a href="${pageContext.request.contextPath}/busquedas/categorias"><button class="btn btn-lg btn-info" type="button">Búsqueda por categoria</button></a><br><br>
            </div>
            
            
            <div class="col-md-6">
            
            	<c:if test="${empty productos}">
            
	            	<form:form method="GET" action="${pageContext.request.contextPath}/busquedas/categoria">
	            	
		            	<select name="id_categoria" id="id_categoria" class="form-control" onchange="search_producto(this.value)">
		            		<option value="">Selecciona una categoria</option>
		            		<c:forEach items="${categorias}" var="categoria">
		            			<option value="${categoria.id}">${categoria.nombre}</option>
		            		</c:forEach>
		            	</select><br>
		            	
		            	<div class="col-md-6">
		            		<button type='submit' class='btn btn-primary btnSearch' style="display:none">Buscar Producto</button>
		            	</div>
		            	
		            </form:form>
	            
	            </c:if> 
	            	
	            <!-- Select lista de productos para buscar por categoria -->
	            
	            <c:if test="${not empty productos}">
	            
	            	<form:form method="POST" action="${pageContext.request.contextPath}/busquedas/categoria/producto/save" modelAttribute="producto">
	             		
		            	<form:select path="id" id="producto" class="form-control" onchange="search_producto(this.value)">
		            		<option	value="">Selecciona un producto</option>
		            		<c:forEach items="${productos}" var="producto">
		            			<option value="${producto.id}">${producto.nombre}</option>
		            		</c:forEach>
		            	</form:select><br>
		            	 
		            	 
		            	<div class="col-md-6">
		            		<button type='submit' class='btn btn-primary btnSearch' style="display:none">Agregar a lista de compras</button>
		            	</div>
	            	
	            	</form:form>
	            
	            </c:if>

	       </div>
             
        </div>
    </div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>

<script>
	<%@ include file="/js/funciones.js" %>
	var ruta = "${pageContext.request.contextPath}";
</script>

</body>
</html>
