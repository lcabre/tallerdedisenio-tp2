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
        Establecimiento dia2 = new Establecimiento();
        dia2.setDireccion("Av. de Mayo");
        dia2.setBarrio("Ramos Mejia");
        dia2.setNombre("Supermercado Dia 2");
        dia2.setNumero(100);
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
        Producto tomate = new Producto();
        tomate.setNombre("tomate");

        //Creo categorias
        Categoria snack = new Categoria();
        snack.setNombre("Snack");
        Categoria golosinas = new Categoria();
        golosinas.setNombre("Golosinas");

        //creo los objetos que modelan las tablas intermedias, para puder asocial un producto con un establecimiento y poder asignarle el precio
        PivotTable tablaIntermediaEntrePapasYCoto = new PivotTable();
        PivotTable tablaIntermediaEntreCarameloYDia = new PivotTable();
        PivotTable tablaIntermediaEntrePapasYMasLejano = new PivotTable();
        PivotTable pivottomatedia2 = new PivotTable();

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

        pivottomatedia2.setEstablecimiento(dia2);
        pivottomatedia2.setProducto(tomate);
        pivottomatedia2.setPrecio(101f);

        //guardo a travez de la tabla intermedia todo lo creado anteriormente
        pivotTableDao.save(tablaIntermediaEntrePapasYCoto);
        pivotTableDao.save(tablaIntermediaEntreCarameloYDia);
        pivotTableDao.save(tablaIntermediaEntrePapasYMasLejano);
        pivotTableDao.save(pivottomatedia2);

        String direccionDelCliente = null;
        try {
            direccionDelCliente = URLEncoder.encode("Ramos mejia, necochea 100","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Los ides de algunos productos seleccionados, suponiendo que el cliente armo la lista de compras, estos vendran por POST o GET a futuro
        Integer[] listaProductos = {1, 2, 3};
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

    public String parseJsonData(List<Establecimiento> establecimientos){
        String jsonData = "";
        for (Integer i=0; establecimientos.size() > i; i++){
            if(i==0)
                jsonData += "{\"establecimientos\":[{";
            else
                jsonData += ",{";

            jsonData += "\"nombre\":\""+establecimientos.get(i).getNombre()+"\",";
            jsonData += "\"direccion\":\""+establecimientos.get(i).getFullAddress()+"\",";
            jsonData += "\"productos\":[";
            for (Integer j=0; establecimientos.get(i).getProductosBuscados().size() > j; j++){
                if(j==0)
                    jsonData += "{";
                else
                    jsonData += ",{";
                jsonData += "\"nombre\":\""+establecimientos.get(i).getProductosBuscados().get(j).getNombre()+"\",";
                jsonData += "\"precio\":\""+establecimientos.get(i).getProductosBuscados().get(j).getPrecioEnEstablecimiento()+"\"";
                jsonData += "}";
            }
            jsonData += "]}";
        }
        jsonData += "]}";

        return jsonData;
    }
    
    
    public List busquedaPorMenorPrecio() {
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
        Establecimiento masBarato = new Establecimiento();
        masBarato.setDireccion("Pres. Alvear");
        masBarato.setBarrio("Haedo");
        masBarato.setNombre("Mas Barato");
        masBarato.setNumero(150);

        //Creo productos
        Producto papas = new Producto();
        papas.setNombre("Papas Fritas");
        Producto caramelo = new Producto();
        caramelo.setNombre("caramelo");
        Producto nachos = new Producto();
        nachos.setNombre("nachos");

        //Creo categorias
        Categoria snack = new Categoria();
        snack.setNombre("Snack");
        Categoria golosinas = new Categoria();
        golosinas.setNombre("Golosinas");
        

        //creo los objetos que modelan las tablas intermedias, para puder asocial un producto con un establecimiento y poder asignarle el precio
        PivotTable tablaIntermediaEntrePapasYCoto = new PivotTable();
        PivotTable tablaIntermediaEntreCarameloYDia = new PivotTable();
        PivotTable tablaIntermediaEntrePapasYMasBarato = new PivotTable();
        PivotTable tablaIntermedia1 = new PivotTable();
        PivotTable tablaIntermedia2 = new PivotTable();
        PivotTable tablaIntermedia3 = new PivotTable();

        //le asigno categoria a los productos
        papas.setCategoria(snack);
        caramelo.setCategoria(golosinas);
        nachos.setCategoria(snack);

        

        //asocio un producto con un establecimiento a traves de la tabla intermedia
        tablaIntermediaEntrePapasYCoto.setEstablecimiento(coto);
        tablaIntermediaEntrePapasYCoto.setProducto(papas);
        tablaIntermediaEntrePapasYCoto.setPrecio(18.5f);

        
        tablaIntermedia1.setEstablecimiento(coto);
        tablaIntermedia1.setProducto(nachos);     
        tablaIntermedia1.setPrecio(5.9f);
        

        //asocio un producto con un establecimiento a traves de la tabla intermedia
        tablaIntermediaEntreCarameloYDia.setEstablecimiento(dia);
        tablaIntermediaEntreCarameloYDia.setProducto(caramelo);
        tablaIntermediaEntreCarameloYDia.setPrecio(10.5f);
        
        
        tablaIntermedia2.setEstablecimiento(masBarato);
        tablaIntermedia2.setProducto(nachos);     
        tablaIntermedia2.setPrecio(26.5f);
        
        
        tablaIntermedia3.setEstablecimiento(masBarato);
        tablaIntermedia3.setProducto(papas);
        tablaIntermedia3.setPrecio(10.5f);


        //asocio un producto con un establecimiento a traves de la tabla intermedia
        tablaIntermediaEntrePapasYMasBarato.setEstablecimiento(masBarato);
        tablaIntermediaEntrePapasYMasBarato.setProducto(caramelo);
        tablaIntermediaEntrePapasYMasBarato.setPrecio(3.5f);

        //guardo a travez de la tabla intermedia todo lo creado anteriormente
        

        pivotTableDao.save(tablaIntermediaEntrePapasYCoto);
        pivotTableDao.save(tablaIntermediaEntreCarameloYDia);
        pivotTableDao.save(tablaIntermediaEntrePapasYMasBarato);
        pivotTableDao.save(tablaIntermedia1);
        pivotTableDao.save(tablaIntermedia2);
        pivotTableDao.save(tablaIntermedia3);
        



        //Los ides de algunos productos seleccionados, suponiendo que el cliente armo la lista de compras, estos vendran por POST o GET a futuro
        Integer[] listaProductos = {1,2,3};
        // fin preparacion del metodo
        
        List<Producto> productos = productoDao.preparaBusqueda(listaProductos);
        
        List<PivotTable> tablaOrdenada = productoDao.ordenarProductosPorMenorPrecio(productos);
        
        
        
        List<PivotTable> menorPrecio = productoDao.busquedaPorMenorPrecio(tablaOrdenada);
        
        
        List<Establecimiento> establecimientosMenorPrecio = productoDao.entregaSoloEstablecimientos(menorPrecio);

       
        return establecimientosMenorPrecio;
    }

}
