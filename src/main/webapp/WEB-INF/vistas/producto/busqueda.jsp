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
                <h3>Listado de establecimientos</h3>
                <c:forEach var="establecimiento" items="${records}">
                    asdasd
                    <ul>
                        <li><c:out value="${establecimiento.id}"/></li>
                        <li><c:out value="${establecimiento.nombre}"/></li>
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
                test
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
</body>
</html>
