package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.PivotTableDao;
import ar.edu.unlam.smartshop.daos.ProductoDao;
import ar.edu.unlam.smartshop.modelos.*;
import ar.edu.unlam.smartshop.modelos.api.MapAPI;
import ar.edu.unlam.smartshop.modelview.ProductoModelView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("productoService")
public class ProductoServicioImpl implements ProductoServicio {

    @Inject
    private ProductoDao productoDao;

    @Inject
    private PivotTableDao pivotTableDao;

    @Inject
    private CategoriaServicio categoriaServicio;

    @Inject
    private EstablecimientoServicio establecimientoServicio;

    @Override
    public void save(ProductoModelView productoModel) {
        Producto producto = productoDao.getById(productoModel.getIdProducto());

        Establecimiento establecimiento = establecimientoServicio.getById(productoModel.getIdEstablecimiento());
        PivotTable pivotTable = new PivotTable();

        pivotTable.setProducto(producto);
        pivotTable.setEstablecimiento(establecimiento);
        pivotTable.setPrecio(productoModel.getPrecio());

        pivotTableDao.save(pivotTable);
    }

    @Override
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
        return productoDao.getById(id);
    }

    @Override
    public void delete(Integer id) {

    }

    public List busquedaPorCercania(String direccion, ListaCompras lista) {

        List<Producto> productos = lista.getProductos();
        List<Establecimiento> establecimientosCercanos = new ArrayList<>();

        for (Producto producto:productos){
            if(producto.getPivotTables().size()>0){
                //Establecimiento establecimientoMasCercano = producto.getEstablecimientoMasCercano(direccion);
                Establecimiento establecimientoMasCercano = MapAPI.getEstablecimientoMasCercano(producto,direccion);
                if(!establecimientosCercanos.contains(establecimientoMasCercano)){
                    establecimientosCercanos.add(establecimientoMasCercano);
                }
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

    public List busquedaPorMenorPrecio(ListaCompras lista) {
        
        List<Producto> productos = lista.getProductos();
        
        List<PivotTable> tablaOrdenada = productoDao.ordenarProductosPorMenorPrecio(productos);

    	PivotTable elementoABorrar = new PivotTable();
    		
    	List<PivotTable> menorPrecio = tablaOrdenada;
        for (Integer i = 0; i < tablaOrdenada.size(); i++) {
            for (Integer j = 0; j < tablaOrdenada.size(); j++) {
                if (i != j) {
                    if ((tablaOrdenada.get(j).getProducto().getId()) == (tablaOrdenada.get(i).getProducto().getId())) {
                        elementoABorrar = tablaOrdenada.get(j);
                        menorPrecio.remove(elementoABorrar);
                    }
                }
            }
        }

    	List<Establecimiento> establecimientosMenorPrecio = new ArrayList<>();

    		for (Integer i = 0; i < menorPrecio.size(); i++) {

    			establecimientosMenorPrecio.add(menorPrecio.get(i).getEstablecimiento());
    			establecimientosMenorPrecio.get(i).getProductosBuscados().add(menorPrecio.get(i).getProducto());
    			menorPrecio.get(i).getProducto().setPrecioEnEstablecimiento(menorPrecio.get(i).getPrecio());

    		}

    		for (Integer i = 0; i < establecimientosMenorPrecio.size(); i++) {
    			for (Integer j = 0; j < establecimientosMenorPrecio.size(); j++) {
    				if (i != j) {
    					if ((establecimientosMenorPrecio.get(j).equals(establecimientosMenorPrecio.get(i)))) {
    						Establecimiento establecimientoABorrar = establecimientosMenorPrecio.get(j);
    						establecimientosMenorPrecio.remove(establecimientoABorrar);
    					}
    				}
    			}
    		}
       
        return establecimientosMenorPrecio;
    }

    @Override
    public List busquedaPorMayorRapidezEnAtencion(ListaCompras lista) {

        List<Producto> productos = lista.getProductos();

        List<Establecimiento> establecimientosMejorPuntuados = new ArrayList<>();

        for (Producto producto:productos){
            Establecimiento establecimientoMasCercano = producto.getEstablecimientoConMejorPuntuacion();
            if(!establecimientosMejorPuntuados.contains(establecimientoMasCercano)){
                establecimientosMejorPuntuados.add(establecimientoMasCercano);
            }
        }

        return establecimientosMejorPuntuados;
    }

    @Override
    public Object listByUser(Usuario loguedUser) {
        return productoDao.listByUser(loguedUser);
    }

    @Override
    public List listProductosEnEstablecimientos() {
        return productoDao.listProductosEnEstablecimientos();
    }

    @Override
    public List getMasBuscados(Usuario loguedUser) {
        return productoDao.getMasBuscados(loguedUser);
    }
}
