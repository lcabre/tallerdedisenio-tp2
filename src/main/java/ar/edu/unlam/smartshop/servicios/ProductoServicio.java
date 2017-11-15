package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Producto;

import java.util.List;

public interface ProductoServicio {
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List busquedaPorCercania();
    List list();
    Producto getById(Integer id);
    List busquedaPorMayorRapidezEnAtencion();
}
