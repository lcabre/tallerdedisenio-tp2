package ar.edu.unlam.smartshop.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.smartshop.daos.ListaComprasDao;
import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;

@Service("listaComprasService")
public class ListaComprasServicioImpl implements ListaComprasServicio {

	@Inject
	private ListaComprasDao listaComprasDao;
	
	@Override
	public void save(Integer id) {
		// TODO Auto-generated method stub
		listaComprasDao.save(id);
	}

	@Override
	@Transactional
	public List list() {
		// TODO Auto-generated method stub
		return listaComprasDao.list();
	}

}
