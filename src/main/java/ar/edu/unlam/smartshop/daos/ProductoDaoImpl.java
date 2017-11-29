package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.*;
import ar.edu.unlam.smartshop.modelview.ProductoCountModelView;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Repository("productoDao")
public class ProductoDaoImpl implements ProductoDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Producto producto) {

        final Session session = sessionFactory.getCurrentSession();

        try {
            session.save(producto);
        }
        catch (Exception e) {
            //System.out.println("Ya existe el dato");
        }
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
        return session.createCriteria(Producto.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
    }

    @Override
    @Transactional
    public Producto getById(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		return session.get(Producto.class, id);
    }

    @Override
    @Transactional
    public List<Producto> findByIds(List listaProductos) {
		final Session session = sessionFactory.getCurrentSession();
		List<Producto> productos = session.createCriteria(Producto.class)
				.add(Restrictions.in("id",listaProductos.toArray()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();

		for (Producto producto:productos) {
			Hibernate.initialize(producto.getPivotTables());
		}
		return productos;
    }

	@SuppressWarnings("unchecked")
	@Override
    @Transactional
    public List<PivotTable> ordenarProductosPorMenorPrecio(List<Producto> productos) {
        final Session session = sessionFactory.getCurrentSession();
		List<PivotTable> menorPrecio = session.createCriteria(PivotTable.class)
        .add(Restrictions.in("producto",productos))
        .addOrder(Order.asc("precio"))
        .list();
		 return menorPrecio;
	}

	@Override
	@Transactional
	public Object listByUser(Usuario loguedUser) {
		final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Producto.class)
				.createAlias("pivotTables", "pivot")
				.createAlias("pivot.establecimiento","est" )
				.createAlias("est.usuario","usr" )
				.add(Restrictions.eq("usr.id",loguedUser.getId()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

	@Override
	@Transactional
	public List listProductosEnEstablecimientos() {
		final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Producto.class)
				.createAlias("pivotTables", "pivot")
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

	@Override
	@Transactional
	public List getMasBuscados(Usuario loguedUser) {
		final Session session = sessionFactory.getCurrentSession();
		/*return session.createCriteria(Producto.class)
				.createAlias("listaDeCompras", "lista")
				.createAlias("pivotTables", "pivot")
				.createAlias("pivot.establecimiento","est" )
				.createAlias("est.usuario","usr" )
				.add(Restrictions.eq("usr.id",loguedUser.getId()))
				.setProjection(Projections.projectionList()
						.add(Projections.alias(Projections.groupProperty("est.id"),"id_establecimiento"))
						.add(Projections.alias(Projections.groupProperty("id"), "id_producto"))
						.add(Projections.alias(Projections.property("nombre"),"nombre"))
						.add(Projections.alias(Projections.count("id"),"total"))
				)
				.list();*/

		return session.createCriteria(Producto.class)
				.createAlias("listaDeCompras", "lista")
				.setProjection(Projections.projectionList()
						.add(Projections.alias(Projections.groupProperty("id"), "id"))
						.add(Projections.alias(Projections.property("nombre"),"nombre"))
						.add(Projections.alias(Projections.count("id"),"total"))
				)
				.setResultTransformer(new AliasToBeanResultTransformer(ProductoCountModelView.class))
				.list();
	}
}
