package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.List;

@Repository("establecimientoDao")
public class EstablecimientoDaoImpl implements EstablecimientoDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Establecimiento establecimiento) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(establecimiento);
    }

    @Override
    @Transactional
    public void update(Establecimiento establecimiento) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(establecimiento);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        final Session session = sessionFactory.getCurrentSession();
        session.delete(id);
    }

    @Override
    @Transactional
    public List list() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Establecimiento.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    @Transactional
    public Establecimiento getById(Integer id) {
        final Session session = sessionFactory.getCurrentSession();
        return session.get(Establecimiento.class, id);
    }

    @Override
    @Transactional
    public List<Establecimiento> queContengan(Integer[] producto) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Establecimiento>) session.createCriteria(Establecimiento.class)
                .createAlias("productos", "prod")
                .add(Restrictions.in("prod.id",producto))
                .list();
    }

    @Override
    @Transactional
    public List getByUser(Usuario loguedUser) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Establecimiento>) session.createCriteria(Establecimiento.class)
                .createAlias("usuario", "user")
                .add(Restrictions.in("user.id",loguedUser.getId()))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

}
