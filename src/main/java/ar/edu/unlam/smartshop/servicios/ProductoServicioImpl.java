package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.EstablecimientoDao;
import ar.edu.unlam.smartshop.daos.ProductoDao;
import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.api.JsonMatrixResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("productoService")
public class ProductoServicioImpl implements ProductoServicio {

    @Inject
    private ProductoDao productoDao;

    @Inject
    private EstablecimientoDao establecimientoDao;

    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void update(Producto p) {

    }

    @Override
    public List list() {
        return productoDao.list();
    }

    @Override
    public Producto getById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    public List busquedaPorCercania() {
        /**
         * Preparacion
         */
        Establecimiento coto = new Establecimiento();
        coto.setDireccion("Av. Rivadavia");
        coto.setBarrio("Ramos Mejia");
        coto.setNombre("Coto");
        coto.setNumero(13810);

        Establecimiento dia = new Establecimiento();
        dia.setDireccion("Av. de Mayo");
        dia.setBarrio("Ramos Mejia");
        dia.setNombre("Supermercado Dia");
        dia.setNumero(791);

        Producto alfajor = new Producto();
        alfajor.setNombre("alfajor");
        alfajor.setEstablecimiento(coto);

        Producto alfajor2 = new Producto();
        alfajor2.setNombre("alfajor");
        alfajor2.setEstablecimiento(dia);

        productoDao.save(alfajor);
        productoDao.save(alfajor2);

        String direccionDelCliente = null;
        try {
            direccionDelCliente = URLEncoder.encode("Ramos mejia, necochea 100","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * fin preparacion
         */

        List<Establecimiento> establecimientos = establecimientoDao.queContengan("alfajor");

        String API_KEY = "AIzaSyDwZrfQ2Nod2H7aqcYAfbCcSS_OdFnt9tY";
        Client client = ClientBuilder.newClient();
        String target = "https://maps.googleapis.com/maps/api/distancematrix/json?"+
                "origins="+direccionDelCliente+"&destinations=";

        try {
            for (Iterator<Establecimiento> i = establecimientos.iterator(); i.hasNext();) {
                Establecimiento e = i.next();
                if(i.hasNext())
                    target += URLEncoder.encode(e.getFullAddress()+"|", "UTF-8");
                else
                    target += URLEncoder.encode(e.getFullAddress(), "UTF-8");
            }
            target += "&key="+API_KEY;

            WebTarget webResource = client.target(target);
            Invocation.Builder invocationBuilder = webResource.request(MediaType.TEXT_PLAIN_TYPE);
            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Fallo : HTTP error code : "
                        + response.getStatus());
            }

            String jsonResponse = response.readEntity(String.class);
            JsonMatrixResponse json = new Gson().fromJson(jsonResponse, JsonMatrixResponse.class);

            List<Establecimiento> listado =new ArrayList<>();
            listado.add(establecimientos.get(json.getMenorDistanciaIndex()));

            if(json.getStatus().equals("OK")){
                return listado;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
