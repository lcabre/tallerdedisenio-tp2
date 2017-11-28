<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Smart Shop</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:if test="${not empty sessionScope.TIPO && sessionScope.TIPO == 'Establecimiento'}">
                    <li class="${pageContext.request.requestURI.contains('establecimiento') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/establecimientos">Mis Establecimientos</a></li>
                    <li class="${pageContext.request.requestURI.contains('producto') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/productos">Mis Productos</a></li>
                   <!-- <li class="${pageContext.request.requestURI.contains('masbuscados') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/productos/mas/buscados">Productos Mas Buscados</a></li>-->
                </c:if>
                <c:if test="${not empty sessionScope.TIPO && sessionScope.TIPO == 'Cliente'}">
                    <li class="${pageContext.request.requestURI.contains('busqueda') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/busquedas">Busquedas</a></li>
                    <li class="${pageContext.request.requestURI.contains('lista') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/milista">Mi Lista</a></li>
                    <li class="${pageContext.request.requestURI.contains('historial') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/mihistorial">Historial</a></li>
                </c:if>
            </ul>
            ${sessionScope.NOMBRE}
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty sessionScope.NOMBRE}">
                    <li class=""><a href="${pageContext.request.contextPath}/login">Login</a></li>
                </c:if>
                <c:if test="${not empty sessionScope.NOMBRE}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            ${sessionScope.NOMBRE} <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!--<li><a href="#">Perfil</a></li>
                            <li role="separator" class="divider"></li>-->
                            <li><a href="${pageContext.request.contextPath}/logout">Salir</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

