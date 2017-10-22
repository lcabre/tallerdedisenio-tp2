package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Producto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("productoDao")
public class ProductoDaoImpl implements ProductoDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Producto producto) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(producto);
    }

    @Override
    @Transactional
    public void update(Producto producto) {

    }

    @Override
    @Transactional
    public void delete(Integer id) {

    }

    @Override
    @Transactional
    public List list() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Producto.class).list();
    }

    @Override
    @Transactional
    public Producto getById(Integer id) {
        return null;
    }
}
