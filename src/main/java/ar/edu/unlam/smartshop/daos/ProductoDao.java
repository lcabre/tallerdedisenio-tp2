package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.PivotTable;
import ar.edu.unlam.smartshop.modelos.Producto;

import java.util.List;

public interface ProductoDao {
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List list();
    Producto getById(Integer id);

    List<Producto> findByIds(Integer[] listaProductos);

	List<PivotTable> busquedaPorMenorPrecio(List<PivotTable> productosOrdenados);
	List<PivotTable> ordenarProductosPorMenorPrecio(List<Producto> productos);
	List<Producto> preparaBusqueda(Integer[] listaProductos);
	List<Establecimiento> entregaSoloEstablecimientos(List<PivotTable> menorPrecio);
    List<Producto> findProductsByCategory(Integer id);
}
