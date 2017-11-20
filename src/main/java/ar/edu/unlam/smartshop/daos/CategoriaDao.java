package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Categoria;

import java.util.List;

public interface CategoriaDao {

	void save(Categoria categoria);
	void update(Categoria categoria);
	void delete(Integer id);
	List list();
	Categoria getById(Integer id);
}
