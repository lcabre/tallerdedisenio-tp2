package ar.edu.unlam.smartshop.servicios;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ar.edu.unlam.smartshop.modelos.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.smartshop.daos.ListaComprasDao;
import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;

@Service("listaComprasService")
public class ListaComprasServicioImpl implements ListaComprasServicio {

	@Inject
	private ListaComprasDao listaComprasDao;

	@Inject
	private ProductoServicio productoServicio;
	
	@Override
	public void save(ListaCompras lista) {
		listaComprasDao.save(lista);
	}

	@Override
	public void update(ListaCompras lista) {
		listaComprasDao.update(lista);
	}

	@Override
	@Transactional
	public List list() {
		return listaComprasDao.list();
	}

	@Override
	public ListaCompras getByUserACtual(Usuario loguedUser) {
		return listaComprasDao.getByUserACtual(loguedUser);
	}

	@Override
	public void addProducto(ListaCompras lista, Producto pro, Usuario loguedUser) {
		Producto producto = productoServicio.getById(pro.getId());

		if(lista==null){
			lista = new ListaCompras();
			lista.setActual(true);
			lista.setFecha(new Date());
			lista.setUsuario(loguedUser);
			lista.addProducto(producto);
			listaComprasDao.save(lista);
		}else{
			if(!this.containsId(lista.getProductos(), producto.getId())){
				lista.addProducto(producto);
				listaComprasDao.update(lista);
			}
		}
	}

	@Override
	public List getByUserHistorial(Usuario loguedUser) {
		return listaComprasDao.getByUserHistorial(loguedUser);
	}

	private boolean containsId(List<Producto> list, long id) {
		for (Producto object : list) {
			if (object.getId() == id) {
				return true;
			}
		}
		return false;
	}
}
