package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Usuario;

import java.lang.reflect.Array;
import java.util.List;

public interface EstablecimientoDao {
    void save(Establecimiento producto);
    void update(Establecimiento producto);
    void delete(Integer id);
    List list();
    Establecimiento getById(Integer id);
    List<Establecimiento> queContengan(Integer[] productos);

    List getByUser(Usuario loguedUser);
}
