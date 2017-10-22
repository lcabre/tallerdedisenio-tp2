package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Producto;

import java.util.List;

public interface ProdcutoServicio {
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List list();
    Producto getById(Integer id);
}
