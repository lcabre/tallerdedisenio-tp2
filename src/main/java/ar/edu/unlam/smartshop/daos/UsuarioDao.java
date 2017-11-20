package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.Usuario;

import java.util.List;

public interface UsuarioDao {
    void save(Usuario usuario);
    void update(Usuario usuario);
    void delete(Integer id);
    List list();
    Usuario getById(Integer id);
    Usuario getUser(Usuario usuario);
    Usuario getUserByMail(String email);
}
