package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.EstablecimientoDao;
import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

@Service("establecimientoService")
public class EstablecimientoServicioImpl implements EstablecimientoServicio {

    @Inject
    private EstablecimientoDao establecimientoDao;

    @Inject
    private ServicioLogin servicioLogin;

    public void save(Establecimiento establecimiento, Usuario usuario) {
        establecimiento.setUsuario(usuario);
        Random r = new Random();
        establecimiento.setRapidezEnAtencion(r.nextInt(1000-1) + 1);
        establecimientoDao.save(establecimiento);
    }

    @Override
    public void update(Establecimiento establecimiento) {
        establecimientoDao.update(establecimiento);
    }

    @Override
    public List list() {
        return establecimientoDao.list();
    }

    @Override
    public Establecimiento getById(Integer id) {
        return establecimientoDao.getById(id);
    }

    @Override
    public List getByUser(Usuario loguedUser) {
        return establecimientoDao.getByUser(loguedUser);
    }

    @Override
    public void delete(Integer id) {
        establecimientoDao.delete(id);
    }
}
