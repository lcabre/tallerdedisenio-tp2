package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.ProductoDao;
import ar.edu.unlam.smartshop.modelos.Producto;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("productoService")
public class ProductoServicioImpl implements ProductoServicio {

    @Inject
    private ProductoDao productoDao;

    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void update(Producto p) {

    }

    @Override
    public List list() {
        return productoDao.list();
    }

    @Override
    public Producto getById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
