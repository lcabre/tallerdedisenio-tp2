package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.modelview.ProductoModelView;

import java.util.List;

public interface ProductoServicio {
    void save(ProductoModelView producto);
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
    List list();
    Producto getById(Integer id);

    List busquedaPorCercania(String direccion, ListaCompras lista);
    List busquedaPorMenorPrecio(ListaCompras lista);
    List busquedaPorMayorRapidezEnAtencion(ListaCompras lista);
    String parseJsonData(List<Establecimiento> establecimientos);
    Object listByUser(Usuario loguedUser);
    List listProductosEnEstablecimientos();

    List getMasBuscados(Usuario loguedUser);
}
