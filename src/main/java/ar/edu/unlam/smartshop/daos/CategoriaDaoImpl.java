package ar.edu.unlam.smartshop.daos;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.smartshop.modelos.Categoria;

@Repository("categoriaDao")
public class CategoriaDaoImpl implements CategoriaDao {
	
	@Inject
    private SessionFactory sessionFactory;
		
	@Override
	@Transactional
	public List listCategorias() {
		final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Categoria.class).list();
	}

}
