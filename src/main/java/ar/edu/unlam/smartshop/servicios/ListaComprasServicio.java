package ar.edu.unlam.smartshop.servicios;

import java.util.List;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;

public interface ListaComprasServicio {
	
	void save(ListaCompras lista);
	void update(ListaCompras lista);
	List list();
    ListaCompras getByUserACtual(Usuario loguedUser);
	void addProducto(ListaCompras lista, Producto pro, Usuario loguedUser);

	List getByUserHistorial(Usuario loguedUser);

    ListaCompras getById(Integer id);

    void delete(ListaCompras listaActual);
}
