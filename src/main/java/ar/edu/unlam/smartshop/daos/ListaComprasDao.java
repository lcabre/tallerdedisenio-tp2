package ar.edu.unlam.smartshop.daos;

import java.util.List;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;

public interface ListaComprasDao {
	
	void save(Integer id);
	List list();
	
}
