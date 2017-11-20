package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Usuario;

public interface ServicioLogin {
	Usuario getUser(Usuario usuario);
	Usuario getUserByMail (String email);
}
