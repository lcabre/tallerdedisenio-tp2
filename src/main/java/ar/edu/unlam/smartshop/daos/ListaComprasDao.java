package ar.edu.unlam.smartshop.daos;

import java.util.List;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;

public interface ListaComprasDao {
	
	void save(ListaCompras lista);
	List list();
    ListaCompras getByUserACtual(Usuario loguedUser);
    void update(ListaCompras lista);

    List getByUserHistorial(Usuario loguedUser);
}
