package ar.edu.unlam.smartshop.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.smartshop.controladores.ControladorLogin;
import ar.edu.unlam.smartshop.modelos.TipoUusuario;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;

public class LoginControllerTest {
	
	@Test
	public void alPasarUsuarioyRequestDeberiaRedirigirAlSitio() {
		ControladorLogin cont = new ControladorLogin();
		
		Usuario usuarioMock = mock(Usuario.class);
		TipoUusuario tipoMock = mock(TipoUusuario.class);
		//tipoMock.setNombre("Cliente");
		ServicioLogin servicioMock = mock(ServicioLogin.class);
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		
		cont.setServicioLogin(servicioMock);
		servicioMock.getUser(usuarioMock);
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada

		when(tipoMock.getNombre()).thenReturn("Cliente");
		when(usuarioMock.getApellido()).thenReturn("Rodriguez"); 
		when(usuarioMock.getNombre()).thenReturn("Erika");
		when(usuarioMock.getEmail()).thenReturn("erika@gmail.com");
		when(usuarioMock.getTipo()).thenReturn(tipoMock);
		when(servicioMock.getUser(any(Usuario.class))).thenReturn(usuarioMock); // Cuando
		
		ModelAndView mav = cont.validarLogin(usuarioMock, requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("redirect:/");
		verify(sessionMock, times(1)).setAttribute("NOMBRE", "Rodriguez, Erika");
		verify(sessionMock, times(1)).setAttribute("TIPO", "Cliente");
		verify(sessionMock, times(1)).setAttribute("EMAIL", "erika@gmail.com");


	}

}
