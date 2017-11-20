package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("tipoUsuariooDao")
public class TipoUsuarioDaoImpl implements TipoUsuarioDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(TipoUusuario tipoUusuario) {
        final Session session = sessionFactory.getCurrentSession();
		session.save(tipoUusuario);
    }

	@Override
    @Transactional
    public void update(TipoUusuario tipoUusuario) {
		final Session session = sessionFactory.getCurrentSession();
		session.update(tipoUusuario);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		TipoUusuario tipoUusuario = session.load(TipoUusuario.class, new Integer(id));
		if(null != tipoUusuario){
			session.delete(tipoUusuario);
		}
    }

    @Override
    @Transactional
    public List list() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(TipoUusuario.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    @Transactional
    public TipoUusuario getById(Integer id) {
        final Session session = sessionFactory.getCurrentSession();
        return session.load(TipoUusuario.class, id);
    }
}
