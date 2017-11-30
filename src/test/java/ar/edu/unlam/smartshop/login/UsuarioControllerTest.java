package ar.edu.unlam.smartshop.login;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import ar.edu.unlam.smartshop.controladores.UsuarioController;
import ar.edu.unlam.smartshop.modelos.TipoUusuario;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.modelview.UsuarioModelView;
import ar.edu.unlam.smartshop.servicios.TipoUsuarioServicio;
import ar.edu.unlam.smartshop.servicios.UsuarioServicio;

public class UsuarioControllerTest {
	
	@Test
	public void alPasarUsuarioyRequestDeberiaRedirigirAlSitio() {
		
		UsuarioController cont = new UsuarioController();
		
		Usuario usuarioMock = mock(Usuario.class);
		UsuarioModelView usuarioModelMock = mock(UsuarioModelView.class);
		TipoUusuario tipoMock = mock(TipoUusuario.class);
		
		TipoUsuarioServicio tipoUsuarioServicio = mock(TipoUsuarioServicio.class);
		UsuarioServicio servicioUsuarioMock = mock(UsuarioServicio.class);
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		

		cont.setServicioUsuario(servicioUsuarioMock);
		cont.setTipoUsuarioServicio(tipoUsuarioServicio);
		servicioUsuarioMock.save(usuarioMock);
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada

		when(usuarioModelMock.getNombre()).thenReturn("Erika");
		when(usuarioModelMock.getApellido()).thenReturn("Rodriguez");
		when(usuarioModelMock.getBarrio()).thenReturn("San Justo");
		when(usuarioModelMock.getDireccion()).thenReturn("Peru");
		when(usuarioModelMock.getNumero()).thenReturn(1169);
		when(usuarioModelMock.getEmail()).thenReturn("erika@hotmail.com");
		when(usuarioModelMock.getPassword()).thenReturn("1231234");
		when(usuarioModelMock.getIdTipoUsuario()).thenReturn(1);
		when(tipoUsuarioServicio.getById(1)).thenReturn(tipoMock);
		
		String mav = cont.store(usuarioModelMock);
		String valorBuscado = "redirect:/login";
		
		assertTrue(mav.equals(valorBuscado));
		verify(servicioUsuarioMock, times(1)).save(usuarioMock);		

	}

}
