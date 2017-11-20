package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.TipoUsuarioDao;
import ar.edu.unlam.smartshop.modelos.TipoUusuario;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("tipoUsuarioServicio")
public class TipoUsuarioServicioImpl implements TipoUsuarioServicio {

    @Inject
    TipoUsuarioDao tipoUsuarioDao;

    @Override
    public void save(TipoUusuario tipoUsuario) {
        tipoUsuarioDao.save(tipoUsuario);
    }

    @Override
    public void update(TipoUusuario tipoUsuario) {
        tipoUsuarioDao.update(tipoUsuario);
    }

    @Override
    public void delete(Integer id) {
        tipoUsuarioDao.delete(id);
    }

    @Override
    public List list() {
        return tipoUsuarioDao.list();
    }

    @Override
    public TipoUusuario getById(Integer id) {
        return tipoUsuarioDao.getById(id);
    }
}
