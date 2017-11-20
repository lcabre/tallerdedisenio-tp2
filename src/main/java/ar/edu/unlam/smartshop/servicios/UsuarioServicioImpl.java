package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.UsuarioDao;
import ar.edu.unlam.smartshop.modelos.Usuario;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("usuarioServicio")
public class UsuarioServicioImpl implements UsuarioServicio {

    @Inject
    UsuarioDao usuarioDao;

    @Override
    public void save(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        usuarioDao.update(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioDao.delete(id);
    }

    @Override
    public List list(){
        return usuarioDao.list();
    }

    @Override
    public Usuario getById(Integer id) {
        return usuarioDao.getById(id);
    }
}
