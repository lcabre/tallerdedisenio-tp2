<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Establecimientos mas baratos</title>
    <%@ include file="/WEB-INF/vistas/includes/header.jsp" %>
</head>
<body>

    <div class="container">
        <div class="row">
            <%@ include file="/WEB-INF/vistas/includes/navbar.jsp" %>
        </div>

        <div class="row">
            <div class="col-md-8">
                            <h3>Listado de establecimientos mas Baratos TEST</h3>
                    <c:forEach var="establecimiento" items="${records}">
                    <ul>
                        <li>Establecimiento: <c:out value="${establecimiento.nombre}"/> - <c:out value="${establecimiento.direccion}"/> </li>
                        <c:forEach items="${establecimiento.getProductosBuscados()}" var="producto" varStatus="rowStatus">

                            <ul>
                                <li>Producto: <c:out value="${producto.nombre}"/> - Precio: <c:out value="$${producto.getPrecioEnEstablecimiento()}"/></li>
                            </ul>
                        </c:forEach>
                    </ul>

                </c:forEach>
                <div id="map"></div>
            </div>
            <div class="col-md-4">
                <div id="directions-panel"></div>
            </div>
        </div>
    </div>
    


<%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>

    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDwZrfQ2Nod2H7aqcYAfbCcSS_OdFnt9tY" ></script>
    <script>-
        $( document ).ready(function() {
            var jsonData = null;
            var origin=null;
            var destination = null;
            var waypts = [];
            <c:if test="${not empty jsonData}">
                jsonData = jQuery.parseJSON('${jsonData}');
            </c:if>

            console.log(jsonData.establecimientos[0].direccion, jsonData.establecimientos.length);

            if(jsonData && jsonData.establecimientos.length == 2){
                origin = jsonData.establecimientos[0].direccion;
                destination = jsonData.establecimientos[jsonData.establecimientos.length-1].direccion;
            }else{
                if(jsonData && jsonData.establecimientos.length >= 2){
                    origin = jsonData.establecimientos[0].direccion;
                    destination = jsonData.establecimientos[jsonData.establecimientos.length-1].direccion;

                    var checkboxArray = document.getElementById('waypoints');
                    for (var i = 0; i < jsonData.establecimientos.length; i++) {
                        if (i !== 0 && i !== jsonData.establecimientos.length-1) {
                            waypts.push({
                                location: jsonData.establecimientos[i].direccion,
                                stopover: true
                            });
                        }
                    }
                }else {
                    origin = jsonData.establecimientos[0].direccion;
                    destination = jsonData.establecimientos[0].direccion;
                }
            }

            console.log(origin,destination, waypts);
            initMap(origin,destination, waypts);

        });

        function initMap(origin,destination, waypts) {
            var directionsService = new google.maps.DirectionsService;
            var directionsDisplay = new google.maps.DirectionsRenderer;
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 6,
                center: {lat: 41.85, lng: -87.65}
            });
            directionsDisplay.setMap(map);
            calculateAndDisplayRoute(directionsService, directionsDisplay, origin,destination, waypts);
        }

        function calculateAndDisplayRoute(directionsService, directionsDisplay, origin,destination, waypts) {
            console.log(destination);
            directionsService.route({
                origin: origin,
                destination: destination,
                waypoints: waypts,
                optimizeWaypoints: true,
                travelMode: 'DRIVING'
            }, function(response, status) {
                if (status === 'OK') {
                    directionsDisplay.setDirections(response);
                    var route = response.routes[0];
                    var summaryPanel = document.getElementById('directions-panel');
                    summaryPanel.innerHTML = '';
                    // For each route, display summary information.
                    for (var i = 0; i < route.legs.length; i++) {
                        var routeSegment = i + 1;
                        summaryPanel.innerHTML += '<b>Route Segment: ' + routeSegment +
                            '</b><br>';
                        summaryPanel.innerHTML += route.legs[i].start_address + ' to ';
                        summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
                        summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
                    }
                } else {
                    window.alert('Directions request failed due to ' + status);
                }
            });
        }
    </script>
</body>
</html>