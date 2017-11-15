package ar.edu.unlam.smartshop.servicios;

import java.util.List;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;

public interface ListaComprasServicio {
	
	void save(Integer id);
	List list();
	
}
