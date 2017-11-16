package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;

import java.util.List;

public interface ProductoServicio {
    void save(Producto producto);
    void saveConCategoria(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List busquedaPorCercania(String direccion, Integer[] listaProductos);
    List list();
    Producto getById(Integer id);
    String parseJsonData(List<Establecimiento> establecimientos);
    List busquedaPorMenorPrecio(Integer[] listaProductos);
    List busquedaPorMayorRapidezEnAtencion(Integer[] listaProductos);
    List<Producto> findProductsByCategory(Integer id);
}
