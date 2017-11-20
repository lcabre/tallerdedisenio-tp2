package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.TipoUusuario;

import java.util.List;

public interface TipoUsuarioServicio {

	void save(TipoUusuario usuario);
	void update(TipoUusuario usuario);
	void delete(Integer id);
	List list();
	TipoUusuario getById(Integer id);
}
