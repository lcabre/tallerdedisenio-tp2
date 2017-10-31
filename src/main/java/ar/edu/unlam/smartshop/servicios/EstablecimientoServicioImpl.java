package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.EstablecimientoDao;
import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("establecimientoService")
public class EstablecimientoServicioImpl implements EstablecimientoServicio {

    @Inject
    private EstablecimientoDao establecimientoDao;

    public void save(Establecimiento producto) {
        establecimientoDao.save(producto);
    }

    @Override
    public void update(Establecimiento p) {

    }

    @Override
    public List list() {
        return establecimientoDao.list();
    }

    @Override
    public Establecimiento getById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
