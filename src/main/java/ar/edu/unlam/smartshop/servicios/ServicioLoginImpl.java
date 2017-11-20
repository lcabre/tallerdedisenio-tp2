package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.UsuarioDao;
import ar.edu.unlam.smartshop.modelos.Password;
import ar.edu.unlam.smartshop.modelos.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

	@Inject
	private UsuarioDao usuarioDao;

	@Override
	public Usuario getUser (Usuario usuario) {
		Usuario dbUser = usuarioDao.getUser(usuario);
		if (dbUser != null){
			if(Password.checkPassword(usuario.getPassword(),dbUser.getPassword())){
				return dbUser;
			}
		}
		return null;
	}

	@Override
	public Usuario getUserByMail (String email) {
		return usuarioDao.getUserByMail(email);
	}
}
