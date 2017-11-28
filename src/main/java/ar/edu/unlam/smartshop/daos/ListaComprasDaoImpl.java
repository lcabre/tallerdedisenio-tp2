package ar.edu.unlam.smartshop.daos;

import java.util.List;

import javax.inject.Inject;

import ar.edu.unlam.smartshop.modelos.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
	public void save(ListaCompras lista) {
		final Session session = sessionFactory.getCurrentSession();
        session.save(lista);
	}

	@Override
	@Transactional
	public void update(ListaCompras lista) {
		final Session session = sessionFactory.getCurrentSession();
		session.update(lista);
	}

	@Override
	@Transactional
	public List list() {
		final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(ListaCompras.class)
				.createAlias("producto", "pro")
				.setProjection(Projections.property("pro.nombre"))
				.list();
	}

	@Override
	@Transactional
	public ListaCompras getByUserACtual(Usuario loguedUser) {
		final Session session = sessionFactory.getCurrentSession();
		ListaCompras listaCompras = (ListaCompras) session.createCriteria(ListaCompras.class)
				.createAlias("usuario", "usr")
				.add(Restrictions.eq("usr.id",loguedUser.getId()))
				.add(Restrictions.eq("actual",true))
				//.add(Restrictions.eq("finalizada",false))
				.uniqueResult();

		if (listaCompras!=null){
			Hibernate.initialize(listaCompras.getProductos());

			if(listaCompras.getProductos().size()>0){
				for (Producto producto:listaCompras.getProductos()) {
					Hibernate.initialize(producto.getPivotTables());
				}
			}
		}

		return listaCompras;
	}

	@Override
	@Transactional
	public List  getByUserHistorial(Usuario loguedUser) {
		final Session session = sessionFactory.getCurrentSession();
		List<ListaCompras> historial = session.createCriteria(ListaCompras.class)
				.createAlias("usuario", "usr")
				.add(Restrictions.eq("usr.id",loguedUser.getId()))
				.add(Restrictions.eq("finalizada",true))
				.setMaxResults(10)
				.addOrder(Order.desc("fecha"))
				.addOrder(Order.desc("id"))
				.list();

		if(historial!=null){
			for (ListaCompras lista:historial) {
				Hibernate.initialize(lista.getProductos());

				if(lista.getProductos().size()>0){
					for (Producto producto:lista.getProductos()) {
						Hibernate.initialize(producto.getPivotTables());
					}
				}
			}
		}

		return historial;
	}

	@Override
	@Transactional
	public ListaCompras getById(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		ListaCompras lista = (ListaCompras) session.createCriteria(ListaCompras.class)
				.add(Restrictions.eq("id",id))
				.uniqueResult();

		Hibernate.initialize(lista.getProductos());

		if(lista.getProductos().size()>0){
			for (Producto producto:lista.getProductos()) {
				Hibernate.initialize(producto.getPivotTables());
			}
		}

		return lista;
	}

	@Override
	@Transactional
	public void delete(ListaCompras listaActual) {
		final Session session = sessionFactory.getCurrentSession();
		session.delete(listaActual);
	}
}
