package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    public void update(Establecimiento producto) {

    }

    @Override
    @Transactional
    public void delete(Integer id) {

    }

    @Override
    @Transactional
    public List list() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Establecimiento.class).list();
    }

    @Override
    @Transactional
    public Establecimiento getById(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public List<Establecimiento> queContengan(String producto) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Establecimiento>) session.createCriteria(Establecimiento.class)
                .createAlias("productos", "prod")
                .add(Restrictions.eq("prod.nombre",producto))
                .list();
    }

}
