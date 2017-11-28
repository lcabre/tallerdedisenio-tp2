package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.*;

import java.util.List;

public interface ProductoDao {
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List list();
    Producto getById(Integer id);

    List<Producto> findByIds(List listaProductos);
	List<PivotTable> ordenarProductosPorMenorPrecio(List<Producto> productos);
    Object listByUser(Usuario loguedUser);
    List listProductosEnEstablecimientos();

    List<Producto> getMasBuscados(Usuario loguedUser);
}
