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
import com.sun.xml.internal.ws.api.ha.StickyFeature;
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

        Establecimiento masLejano = new Establecimiento();
        masLejano.setDireccion("Pres. Alvear");
        masLejano.setBarrio("Haedo");
        masLejano.setNombre("Mas lejano");
        masLejano.setNumero(150);

        Producto alfajor = new Producto();
        alfajor.setNombre("alfajor");
        alfajor.setEstablecimiento(coto);

        Producto caramelo = new Producto();
        caramelo.setNombre("caramelo");
        caramelo.setEstablecimiento(dia);
        caramelo.setEstablecimiento(masLejano);

        productoDao.save(alfajor);
        productoDao.save(caramelo);

        String direccionDelCliente = null;
        try {
            direccionDelCliente = URLEncoder.encode("Ramos mejia, necochea 100","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * fin preparacion
         */

        Integer[] listaProductos = {1, 2};//los ids seleccionados en la lista de compras

        List<Producto> productos = productoDao.findByIds(listaProductos);

        List<Establecimiento> establecimientosCercanos = new ArrayList<>();

        for (Producto producto:productos){
            Establecimiento establecimientoMasCercano = producto.getEstablecimientoMasCercano(direccionDelCliente);
            if(!establecimientosCercanos.contains(establecimientoMasCercano)){
                establecimientosCercanos.add(establecimientoMasCercano);
            }
        }

        return establecimientosCercanos;
    }
}
