package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
    public List<Producto> preparaBusqueda(Integer[] listaProductos) {
        final Session session = sessionFactory.getCurrentSession();
         List<Producto> productos = session.createCriteria(Producto.class)
        .add(Restrictions.in("id",listaProductos))
        .list();
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
	public List<PivotTable> busquedaPorMenorPrecio(List<PivotTable> productosOrdenados){
	PivotTable elementoABorrar = new PivotTable();
		List<PivotTable> menorPrecio = productosOrdenados;
		for (Integer i = 0; i < productosOrdenados.size(); i++) {
			for (Integer j = 0; j < productosOrdenados.size(); j++) {
				if (i != j) {
					if ((productosOrdenados.get(j).getProducto().getId()) == (productosOrdenados.get(i).getProducto().getId())) {
						elementoABorrar = productosOrdenados.get(j);
						menorPrecio.remove(elementoABorrar);
					}
				}
			}
		}
		return menorPrecio;
	}

	@Override
	@Transactional
	public List<Establecimiento> entregaSoloEstablecimientos(List<PivotTable> menorPrecio) {
		List<Establecimiento> establecimientosMenorPrecio = new ArrayList<>();
		for (Integer i = 0; i < menorPrecio.size(); i++) {

			establecimientosMenorPrecio.add(menorPrecio.get(i).getEstablecimiento());
			establecimientosMenorPrecio.get(i).getProductosBuscados().add(menorPrecio.get(i).getProducto());
			menorPrecio.get(i).getProducto().setPrecioEnEstablecimiento(menorPrecio.get(i).getPrecio());

		}

		for (Integer i = 0; i < establecimientosMenorPrecio.size(); i++) {
			for (Integer j = 0; j < establecimientosMenorPrecio.size(); j++) {
				if (i != j) {
					if ((establecimientosMenorPrecio.get(j).equals(establecimientosMenorPrecio.get(i)))) {
						Establecimiento elementoABorrar = establecimientosMenorPrecio.get(j);
						establecimientosMenorPrecio.remove(elementoABorrar);
					}
				}
			}
		}
		return establecimientosMenorPrecio;
	}

	@Override
	@Transactional
	public List<Producto> findProductsByCategory(Integer id) {
		final Session session = sessionFactory.getCurrentSession();

		ListaCompras idLista = (ListaCompras) session.createCriteria(ListaCompras.class)
				.createAlias("producto", "pro")
				.add(Restrictions.eqOrIsNull("pro.id", id))
				.uniqueResult();

		if (idLista == null) {
			List cat;
			cat = session.createCriteria(Producto.class)
					.createAlias("categoria", "cat")
					.add(Restrictions.eq("cat.id", id))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();

			return cat;
		} else {
			return null;
		}
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
}
