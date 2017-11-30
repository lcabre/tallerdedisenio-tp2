package ar.edu.unlam.smartshop.establecimiento;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import ar.edu.unlam.smartshop.controladores.EstablecimientoController;
import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.servicios.EstablecimientoServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;

public class EstablecimientoControllerTest {
	
	@Test
	public void alPasarEstablecimientoyRequestAlControladorStoreDeberiaRedirigirAlSitio() {
	
	EstablecimientoController cont = new EstablecimientoController();
	
	Establecimiento establecimientoMock = mock(Establecimiento.class);
	
	ServicioLogin servicioMock = mock(ServicioLogin.class);
	EstablecimientoServicio establecimientoServicioMock = mock(EstablecimientoServicio.class);
	
	HttpServletRequest requestMock = mock(HttpServletRequest.class);	
	HttpSession sessionMock = mock(HttpSession.class); //sesion
	
	
	cont.setServicioLogin(servicioMock);
	cont.setEstablecimientoServicio(establecimientoServicioMock);
	
	when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
	
	String mav = cont.store(establecimientoMock, requestMock);
	String valorBuscado = "redirect:/establecimientos";
	
	assertTrue(mav.equals(valorBuscado));
	


}

}
