package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Producto;

import java.util.List;

public interface ProductoDao {
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List list();
    Producto getById(Integer id);

    List<Producto> findByIds(Integer[] listaProductos);
}
