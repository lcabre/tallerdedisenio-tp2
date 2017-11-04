package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.PivotTableDao;
import ar.edu.unlam.smartshop.daos.ProductoDao;
import ar.edu.unlam.smartshop.modelos.Categoria;
import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.PivotTable;
import ar.edu.unlam.smartshop.modelos.Producto;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service("productoService")
public class ProductoServicioImpl implements ProductoServicio {

    @Inject
    private ProductoDao productoDao;

    @Inject
    private PivotTableDao pivotTableDao;

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
        //Preparacion del metodo
        //Creo establecimientos
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

        //Creo productos
        Producto papas = new Producto();
        papas.setNombre("Papas Fritas");
        Producto caramelo = new Producto();
        caramelo.setNombre("caramelo");

        //Creo categorias
        Categoria snack = new Categoria();
        snack.setNombre("Snack");
        Categoria golosinas = new Categoria();
        golosinas.setNombre("Golosinas");

        //creo los objetos que modelan las tablas intermedias, para puder asocial un producto con un establecimiento y poder asignarle el precio
        PivotTable tablaIntermediaEntrePapasYCoto = new PivotTable();
        PivotTable tablaIntermediaEntreCarameloYDia = new PivotTable();
        PivotTable tablaIntermediaEntrePapasYMasLejano = new PivotTable();

        //le asigno categoria a los productos
        papas.setCategoria(snack);
        caramelo.setCategoria(golosinas);

        //asocio un producto con un establecimiento a traves de la tabla intermedia
        tablaIntermediaEntrePapasYCoto.setEstablecimiento(coto);
        tablaIntermediaEntrePapasYCoto.setProducto(papas);
        tablaIntermediaEntrePapasYCoto.setPrecio(25.5f);

        //asocio un producto con un establecimiento a traves de la tabla intermedia
        tablaIntermediaEntreCarameloYDia.setEstablecimiento(dia);
        tablaIntermediaEntreCarameloYDia.setProducto(caramelo);
        tablaIntermediaEntreCarameloYDia.setPrecio(2f);

        //asocio un producto con un establecimiento a traves de la tabla intermedia
        tablaIntermediaEntrePapasYMasLejano.setEstablecimiento(masLejano);
        tablaIntermediaEntrePapasYMasLejano.setProducto(caramelo);
        tablaIntermediaEntrePapasYMasLejano.setPrecio(3f);

        //guardo a travez de la tabla intermedia todo lo creado anteriormente
        pivotTableDao.save(tablaIntermediaEntrePapasYCoto);
        pivotTableDao.save(tablaIntermediaEntreCarameloYDia);
        pivotTableDao.save(tablaIntermediaEntrePapasYMasLejano);

        String direccionDelCliente = null;
        try {
            direccionDelCliente = URLEncoder.encode("Ramos mejia, necochea 100","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Los ides de algunos productos seleccionados, suponiendo que el cliente armo la lista de compras, estos vendran por POST o GET a futuro
        Integer[] listaProductos = {1, 2};
        // fin preparacion del metodo

        List<Producto> productos = productoDao.findByIds(listaProductos);

        List<Establecimiento> establecimientosCercanos = new ArrayList<>();

        for (Producto producto:productos){
            //Hibernate.initialize(producto.getPivotTables());//Solucionar y sacar Eager
            Establecimiento establecimientoMasCercano = producto.getEstablecimientoMasCercano(direccionDelCliente);
            if(!establecimientosCercanos.contains(establecimientoMasCercano)){
                establecimientosCercanos.add(establecimientoMasCercano);
            }
        }

        return establecimientosCercanos;
    }
}
