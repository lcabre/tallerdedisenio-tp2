package ar.edu.unlam.smartshop.modelos.api;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.PivotTable;
import ar.edu.unlam.smartshop.modelos.Producto;
import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class MapAPI {

    public static Establecimiento getEstablecimientoMasCercano(Producto producto, String direccionCliente){
        String API_KEY = "AIzaSyDwZrfQ2Nod2H7aqcYAfbCcSS_OdFnt9tY";
        try {
            direccionCliente = URLEncoder.encode(direccionCliente,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Client client = ClientBuilder.newClient();
        String target = "https://maps.googleapis.com/maps/api/distancematrix/json?"+
                "origins="+direccionCliente+"&destinations=";

        List<Establecimiento> establecimientos = new ArrayList<>();
        Establecimiento establecimientoBusqueda = null;

        for (PivotTable tablaPivot:producto.getPivotTables()) {
            if(!establecimientos.contains(tablaPivot.getEstablecimiento())){
                establecimientos.add(tablaPivot.getEstablecimiento());
            }
        }

        try {
            for (Iterator<Establecimiento> i = establecimientos.iterator(); i.hasNext();) {
                Establecimiento e = i.next();
                if(i.hasNext())
                    target += URLEncoder.encode(e.getFullAddress()+"|", "UTF-8");
                else
                    target += URLEncoder.encode(e.getFullAddress(), "UTF-8");
            }
            target += "&key="+API_KEY;
            target += "&mode=walking";

            WebTarget webResource = client.target(target);
            Invocation.Builder invocationBuilder = webResource.request(MediaType.TEXT_PLAIN_TYPE);
            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Fallo : HTTP error code : "
                        + response.getStatus());
            }

            String jsonResponse = response.readEntity(String.class);
            JsonMatrixResponse distancias = new Gson().fromJson(jsonResponse, JsonMatrixResponse.class);

            for (Integer i=0; establecimientos.size() > i; i++){
                establecimientos.get(i).setDistancia(distancias.get(i));
                producto.setPrecioEnEstablecimiento(establecimientos.get(i).getPrecioProducto(producto));
                establecimientos.get(i).setProductoBuscado(producto);
            }

            establecimientoBusqueda = Collections.min(establecimientos, Comparator.comparing(c -> c.getDistancia().getValue()));
            producto.setEstablecimientoBusqueda(establecimientoBusqueda);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return establecimientoBusqueda;
    }
}
