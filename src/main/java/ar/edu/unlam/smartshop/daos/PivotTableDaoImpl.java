package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.PivotTable;
import ar.edu.unlam.smartshop.modelos.Producto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("pivotTableDao")
public class PivotTableDaoImpl implements PivotTableDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(PivotTable pivotTable) {

        final Session session = sessionFactory.getCurrentSession();

        try {
            session.save(pivotTable);
        }
        catch (Exception e) {
            //System.out.println("Ya existe el dato");
        }
    }

    @Override
    @Transactional
    public void update(PivotTable pivotTable) {

    }

    @Override
    @Transactional
    public void delete(Integer id) {

    }

    @Override
    @Transactional
    public List list() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(PivotTable.class).list();
    }

    @Override
    @Transactional
    public PivotTable getById(Integer id) {
        return null;
    }

    /*@Override
    @Transactional
    public List<PivotTable> findByIds(Integer[] listaProductos) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<PivotTable>) session.createCriteria(PivotTable.class)
                .add(Restrictions.in("id",listaProductos))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }*/
}
