package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.PivotTable;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;

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
}
