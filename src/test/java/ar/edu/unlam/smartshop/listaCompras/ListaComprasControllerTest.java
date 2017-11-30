package ar.edu.unlam.smartshop.listaCompras;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import ar.edu.unlam.smartshop.controladores.ListaComprasController;
import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;

public class ListaComprasControllerTest {
	
	@Test
	public void alFinalizarListaDeComprasRedigirAmilista() {
	
	ListaComprasController cont = new ListaComprasController();
	
	Usuario usuarioMock = mock(Usuario.class);
	
	ListaCompras listaComprasMock = mock(ListaCompras.class);
	
	ServicioLogin servicioMock = mock(ServicioLogin.class);
	ListaComprasServicio listaComprasServiciosMock = mock(ListaComprasServicio.class);
	
	HttpServletRequest requestMock = mock(HttpServletRequest.class);	
	HttpSession sessionMock = mock(HttpSession.class); //sesion
		
	cont.setServicioLogin(servicioMock);
	cont.setListaComprasServicio(listaComprasServiciosMock);
	
	when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
	when(servicioMock.getUserByMail(any(String.class))).thenReturn(usuarioMock);
	when(listaComprasServiciosMock.getByUserACtual(usuarioMock)).thenReturn(listaComprasMock);
	
	String mav = cont.finalizarListaDeCompras(requestMock);
	String valorBuscado = "redirect:/milista";
	
	assertTrue(mav.equals(valorBuscado));
	verify(listaComprasMock, times(1)).setFinalizada(true);
	verify(listaComprasMock, times(1)).setActual(null);
	verify(listaComprasServiciosMock, times(1)).update(listaComprasMock);
	
	}

}
