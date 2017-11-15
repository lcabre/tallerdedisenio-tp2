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
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/busquedas/cercania"><button class="btn btn-lg btn-primary" Type="button">Búsqueda por Cercanía</button></a>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9">
                <div id="map"></div>
            </div>
            <div class="col-md-3">
                <div id="directions-panel"></div>
            </div>
        </div>
    </div>
    ${direccionDelCliente}
    <%@ include file="/WEB-INF/vistas/includes/scripts.jsp" %>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDwZrfQ2Nod2H7aqcYAfbCcSS_OdFnt9tY" ></script>
    <script>-
        $( document ).ready(function() {
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 12,
                center: {lat: -34.618008, lng: -58.433882}
            });
            var infowindow = new google.maps.InfoWindow();
            var jsonData = null;
            var direccionDelCliente = null;
            var origin=null;
            var destination = null;
            var waypts = [];
            <c:if test="${not empty jsonData}">
                jsonData = jQuery.parseJSON('${jsonData}');
            </c:if>

            direccionDelCliente = '${direccionDelCliente}';

            origin = direccionDelCliente;
            destination = direccionDelCliente;

            for (var i = 0; i < jsonData.establecimientos.length; i++) {
                waypts.push({
                    location: jsonData.establecimientos[i].direccion,
                    stopover: true
                });
            }

            initMap(origin,destination, waypts, jsonData.establecimientos, map);

            function initMap(origin,destination, waypts, establecimientos, map) {
                console.log(waypts);
                console.log(establecimientos);
                var directionsService = new google.maps.DirectionsService;
                //var directionsDisplay = new google.maps.DirectionsRenderer;
                var directionsDisplay = new google.maps.DirectionsRenderer({
                    suppressMarkers: true
                });
                directionsDisplay.setMap(map);
                calculateAndDisplayRoute(directionsService, directionsDisplay, origin,destination, waypts, establecimientos);
            }

            function calculateAndDisplayRoute(directionsService, directionsDisplay, origin,destination, waypts, establecimientos) {
                directionsService.route({
                    origin: origin,
                    destination: destination,
                    waypoints: waypts,
                    optimizeWaypoints: true,
                    travelMode: 'WALKING'
                }, function(response, status) {
                    console.log(response);
                    if (status === 'OK') {
                        directionsDisplay.setDirections(response);
                        directionsDisplay.setMap(map);
                        var route = response.routes[0];
                        var summaryPanel = document.getElementById('directions-panel');
                        var startLocation = {};
                        var endLocation = {};
                        var waypointLocations = [];
                        summaryPanel.innerHTML = '';
                        // For each route, display summary information.
                        /*for (var i = 0; i < route.legs.length; i++) {

                        }*/

                        var legs = route.legs;
                        for (i = 0; i < legs.length; i++) {
                            if (i == 0) {
                                startLocation.latlng = legs[i].start_location;
                                startLocation.address = legs[i].start_address;
                            }
                            if (i != 0) {
                                var waypoint = {};
                                waypoint.latlng = legs[i].start_location;
                                waypoint.address = legs[i].start_address;
                                waypointLocations.push(waypoint);
                            }
                            if (i == legs.length - 1) {
                                //endLocation.latlng = legs[i].end_location;
                                //endLocation.address = legs[i].end_address;
                            }
                            var routeSegment = i + 1;
                            summaryPanel.innerHTML += '<b>Tramo: ' + routeSegment +
                                '</b><br>';
                            summaryPanel.innerHTML += route.legs[i].start_address + ' to ';
                            summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
                            summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
                        }
                        //createMarker(endLocation.latlng, "fin", "algun texto", "http://www.google.com/mapfiles/markerB.png");
                        createMarker(startLocation.latlng, "Tu ubicacion", "", "http://maps.gstatic.com/mapfiles/markers2/marker_greenA.png");
                        for (var i = 0; i < waypointLocations.length; i++) {
                            var establecimiento = establecimientos[route.waypoint_order[i]];
                            console.log(establecimiento);
                            var bodyText = "<b>Productos</b>";
                            establecimiento.productos.forEach(function(producto) {
                                bodyText +="<br>"+producto.nombre+" ($"+producto.precio+")";
                            });
                            createMarker(waypointLocations[i].latlng, establecimiento.nombre, bodyText, "http://www.google.com/mapfiles/marker_yellow.png");
                        }
                    } else {
                        window.alert('Directions request failed due to ' + status);
                    }
                });
            }

            function createMarker(latlng, label, html, url) {
                var contentString = '<span>' + label + '</span><br>' + html;
                var marker = new google.maps.Marker({
                    position: latlng,
                    map: map,
                    icon: url,
                    title: label,
                    zIndex: Math.round(latlng.lat() * -100000) << 5
                });

                google.maps.event.addListener(marker, 'click', function() {
                    infowindow.setContent(contentString);
                    infowindow.open(map, marker);
                });
            }
        });
    </script>
</body>
</html>
