package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Usuario;

import java.util.List;

public interface UsuarioServicio {

	void save(Usuario usuario);
	void update(Usuario usuario);
	void delete(Integer id);
	List list();
	Usuario getById(Integer id);
}
