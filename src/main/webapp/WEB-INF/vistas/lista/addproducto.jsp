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
                <a href="${pageContext.request.contextPath}/milista"><button class="btn btn-lg btn-primary" Type="button">Lista de Productos</button></a>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 productos">
                <c:if test="${not empty records}">
                        <div class="table-responsive">
                            <table class="table table-striped borderless" id="datatable">
                                <thead>
                                    <tr>
                                        <th class="col-md-4"></th>
                                        <th class="col-md-4">Nombre</th>
                                        <th class="col-md-4">Categoria</th>
                                        <th>Agregar</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th class="col-md-4"></th>
                                    <th class="col-md-4">Nombre</th>
                                    <th class="col-md-4">Categoria</th>
                                    <th></th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <c:forEach var="producto" items="${records}">
                                    <tr>
                                        <td><c:out value="${producto.id}"/></td>
                                        <td><c:out value="${producto.nombre}"/></td>
                                        <td><c:out value="${producto.categoria.nombre}"/></td>
                                        <form:form action="${pageContext.request.contextPath}/busquedas/categoria/producto/save" method="POST" modelAttribute="producto">
                                            <form:input path="id" type="hidden" name="id" value="${producto.id}"/>
                                            <td><button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span></button></td>
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
    <script>
        $(document).ready(function() {
            $('#datatable').dataTable({
                "autoWidth":true
                , "info":true
                , "ordering":false
                , "paging":true
                //,"scrollX":        true
                //,"scrollCollapse": true
                , FixedHeader: true
                ,"processing": false
                , "bDestroy": true
                ,dom: 'lBfrtip'
                ,"lengthMenu": [[50, 100, 200, -1], [50, 100, 200, "All"]],
                "order": [[ 0, "desc" ]],
                "language": {
                    "lengthMenu": "Mostrando _MENU_ registros",
                    "search": "Buscar",
                    "zeroRecords": "No se encontraron registros",
                    "info": "Mostrando _START_ de _END_ sobre un total de _TOTAL_ elementos",
                    "infoFiltered":   "(filtrados de un total de _MAX_ total registros)",
                    "processing": "<i class='fa fa-refresh fa-spin'></i>",
                    "paginate": {
                        "first":      "Primera",
                        "last":       "Ultima",
                        "next":       "Siguiente",
                        "previous":   "Anterior"
                    }
                },
                initComplete: function () {
                    console.log("entre");
                    var cont=0;
                    this.api().columns().every( function () {
                        if(cont<3 && cont>0){
                            var column = this;
                            var texto = (cont===1)?"Filtro por nombre":(cont===2)?"Filtro por Categoria":"";
                            var select = $('<select class="form-control"><option value="">'+texto+'</option></select>')
                                .appendTo( $(column.footer()).empty() )
                                .on( 'change', function () {
                                    var val = $.fn.dataTable.util.escapeRegex(
                                        $(this).val()
                                    );
                                    column
                                        .search( val ? '^'+val+'$' : '', true, false )
                                        .draw();
                                } );
                            column.data().unique().sort().each( function ( d, j ) {
                                if(d)
                                    select.append( '<option value="'+d+'">'+d+'</option>' )
                            } );
                        }
                        cont++;
                    } );
                }
                //initComplete: makeSelectFilters
            });
        } );
    </script>
</body>
</html>
