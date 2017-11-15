package ar.edu.unlam.smartshop.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ar.edu.unlam.smartshop.daos.CategoriaDao;

@Service("categoriaService")
public class CategoriaServicioImpl implements CategoriaServicio {

	@Inject
	private CategoriaDao categoriaDao;
	
	@Override
	public List listCategorias() {
		return categoriaDao.listCategorias();
	}

}
