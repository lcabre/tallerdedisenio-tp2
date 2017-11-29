package ar.edu.unlam.smartshop.busquedas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.smartshop.controladores.BusquedaController;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;

public class BusquedasControllerTest {
	
	@Test
	public void buscandoPorEstablecimientoCercanoDebeDirigirALaVistaBusquedaDeBusquedas() {
		BusquedaController cont = new BusquedaController();
		
		Usuario usuarioMock = mock(Usuario.class);
		
		ServicioLogin servicioLoginMock = mock(ServicioLogin.class);
		ListaComprasServicio servicioListaComprasMock = mock(ListaComprasServicio.class);
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		        
		
		cont.setServicioLogin(servicioLoginMock);
		servicioLoginMock.getUserByMail(usuarioMock.getEmail());
		
		cont.setServicioListaComprasMock(servicioListaComprasMock);
		servicioListaComprasMock.getByUserACtual(usuarioMock);
		
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
			
		ModelAndView mav = cont.busquedaPorCercania(requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("/busquedas/busqueda");
		
		

	}
	
	
	@Test
	public void buscandoPorEstablecimientoBaratoDebeDirigirALaVistaBusquedaDeBusquedas() {
		BusquedaController cont = new BusquedaController();
		
		Usuario usuarioMock = mock(Usuario.class);
		
		ServicioLogin servicioLoginMock = mock(ServicioLogin.class);
		ListaComprasServicio servicioListaComprasMock = mock(ListaComprasServicio.class);
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		        
		
		cont.setServicioLogin(servicioLoginMock);
		servicioLoginMock.getUserByMail(usuarioMock.getEmail());
		
		cont.setServicioListaComprasMock(servicioListaComprasMock);
		servicioListaComprasMock.getByUserACtual(usuarioMock);
		
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
			
		ModelAndView mav = cont.busquedaPorMenorPrecio(requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("/busquedas/busqueda");
		
		

	}
	
	
	@Test
	public void buscandoPorEstablecimientoMasRapidoDebeDirigirALaVistaBusquedaDeBusquedas() {
		BusquedaController cont = new BusquedaController();
		
		Usuario usuarioMock = mock(Usuario.class);
		
		ServicioLogin servicioLoginMock = mock(ServicioLogin.class);
		ListaComprasServicio servicioListaComprasMock = mock(ListaComprasServicio.class);
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		        
		
		cont.setServicioLogin(servicioLoginMock);
		servicioLoginMock.getUserByMail(usuarioMock.getEmail());
		
		cont.setServicioListaComprasMock(servicioListaComprasMock);
		servicioListaComprasMock.getByUserACtual(usuarioMock);
		
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
			
		ModelAndView mav = cont.busquedaPorMayorRapidezDeAtencion(requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("/busquedas/busqueda");
		
		

	}
	

}
