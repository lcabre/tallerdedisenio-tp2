package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.TipoUusuario;

import java.util.List;

public interface TipoUsuarioDao {
    void save(TipoUusuario tipoUusuario);
    void update(TipoUusuario tipoUusuario);
    void delete(Integer id);
    List list();
    TipoUusuario getById(Integer id);
}
