package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Usuario;

import java.util.List;

public interface EstablecimientoServicio {
    void save(Establecimiento producto, Usuario usuario);
    void update(Establecimiento producto);
    void delete(Integer id);
    List list();
    Establecimiento getById(Integer id);

    List getByUser(Usuario loguedUser);
}
