package ar.edu.unlam.smartshop.daos;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;

@Repository("listaComprasDao")
public class ListaComprasDaoImpl implements ListaComprasDao {
	
	@Inject
	private SessionFactory sessionFactory;
		
	@Override
	@Transactional
	public void save(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		ListaCompras list = new ListaCompras();
		
		Producto prod = (Producto) session.createCriteria(Producto.class)
											.add(Restrictions.eq("id", id))
											.uniqueResult();
		list.setProducto(prod);
        session.save(list);
	}

	@Override
	@Transactional
	public List list() {
		final Session session = sessionFactory.getCurrentSession();
		List<Producto> pro;
		pro = session.createCriteria(ListaCompras.class)
					.createAlias("producto", "pro")
					.setProjection(Projections.property("pro.nombre"))
					.list();
		
		return pro;
	}

}
