package ar.edu.unlam.smartshop.servicios;

import java.util.List;

import javax.inject.Inject;

import ar.edu.unlam.smartshop.modelos.Categoria;
import org.springframework.stereotype.Service;

import ar.edu.unlam.smartshop.daos.CategoriaDao;

@Service("categoriaService")
public class CategoriaServicioImpl implements CategoriaServicio {

	@Inject
	private CategoriaDao categoriaDao;
	
	@Override
	public void save(Categoria categoria) {
		categoriaDao.save(categoria);
	}

	@Override
	public void update(Categoria categoria) {
		categoriaDao.update(categoria);
	}

	@Override
	public void delete(Integer id) {
		categoriaDao.delete(id);
	}

	@Override
	public List list() {
		return categoriaDao.list();
	}

	@Override
	public Categoria getById(Integer id) {
		return categoriaDao.getById(id);
	}
}
