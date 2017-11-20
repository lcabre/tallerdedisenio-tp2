package ar.edu.unlam.smartshop.daos;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.smartshop.modelos.Categoria;

@Repository("categoriaDao")
public class CategoriaDaoImpl implements CategoriaDao {
	
	@Inject
    private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void save(Categoria categoria) {
		final Session session = sessionFactory.getCurrentSession();
		session.save(categoria);
	}

	@Override
	@Transactional
	public void update(Categoria categoria) {
		final Session session = sessionFactory.getCurrentSession();
		session.update(categoria);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		Categoria categoria = session.get(Categoria.class, id);
		if(null != categoria){
			session.delete(categoria);
		}
	}

	@Override
	@Transactional
	public List list() {
		final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Categoria.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	@Transactional
	public Categoria getById(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		return session.get(Categoria.class, id);
	}

}
