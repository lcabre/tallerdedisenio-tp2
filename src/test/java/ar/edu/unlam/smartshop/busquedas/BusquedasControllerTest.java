package ar.edu.unlam.smartshop.busquedas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.smartshop.controladores.BusquedaController;
import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;
import ar.edu.unlam.smartshop.servicios.ProductoServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;

public class BusquedasControllerTest {
	
	@Test
	public void buscandoPorEstablecimientoCercanoDebeDirigirALaVistaBusquedaDeBusquedas() {
		BusquedaController cont = new BusquedaController();
		
		Usuario usuarioMock = mock(Usuario.class);
		ListaCompras listaComprasMock = mock(ListaCompras.class);
		List<Establecimiento> establecimientosMasCercanos = new ArrayList<>();
		Establecimiento establecimientoMock = mock(Establecimiento.class);
		establecimientosMasCercanos.add(establecimientoMock);
		
		ServicioLogin servicioLoginMock = mock(ServicioLogin.class);
		ListaComprasServicio servicioListaComprasMock = mock(ListaComprasServicio.class);
		ProductoServicio productoServicioMock = mock(ProductoServicio.class);
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		        	
		cont.setServicioLogin(servicioLoginMock);		
		cont.setServicioListaComprasMock(servicioListaComprasMock);		
		cont.setServicioProductoMock(productoServicioMock);
			
		when(servicioLoginMock.getUserByMail(any(String.class))).thenReturn(usuarioMock);
		when(servicioListaComprasMock.getByUserACtual(usuarioMock)).thenReturn(listaComprasMock);
		when(usuarioMock.getFullAdress()).thenReturn("Peru 1169");
		when(productoServicioMock.busquedaPorCercania(any(String.class), any(ListaCompras.class))).thenReturn(establecimientosMasCercanos);
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
			
		ModelAndView mav = cont.busquedaPorCercania(requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("/busquedas/busqueda");
		verify(productoServicioMock, times(1)).busquedaPorCercania(any(String.class), any(ListaCompras.class));
		verify(productoServicioMock, times(1)).parseJsonData((List<Establecimiento>) establecimientosMasCercanos);
		verify(servicioListaComprasMock, times(1)).getByUserACtual(usuarioMock);
		verify(servicioLoginMock, times(1)).getUserByMail(any(String.class));
		verify(usuarioMock, times(2)).getFullAdress();
	}
	
	
	@Test
	public void buscandoPorEstablecimientoBaratoDebeDirigirALaVistaBusquedaDeBusquedas() {
		BusquedaController cont = new BusquedaController();
		
		Usuario usuarioMock = mock(Usuario.class);
		ListaCompras listaComprasMock = mock(ListaCompras.class);
		List<Establecimiento> establecimientosMasCercanos = new ArrayList<>();
		Establecimiento establecimientoMock = mock(Establecimiento.class);
		establecimientosMasCercanos.add(establecimientoMock);
		
		ServicioLogin servicioLoginMock = mock(ServicioLogin.class);
		ListaComprasServicio servicioListaComprasMock = mock(ListaComprasServicio.class);
		ProductoServicio productoServicioMock = mock(ProductoServicio.class);
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		        
		cont.setServicioLogin(servicioLoginMock);		
		cont.setServicioListaComprasMock(servicioListaComprasMock);		
		cont.setServicioProductoMock(productoServicioMock);
		
		when(servicioLoginMock.getUserByMail(any(String.class))).thenReturn(usuarioMock);
		when(servicioListaComprasMock.getByUserACtual(usuarioMock)).thenReturn(listaComprasMock);
		when(usuarioMock.getFullAdress()).thenReturn("Peru 1169");
		when(productoServicioMock.busquedaPorMenorPrecio(any(ListaCompras.class))).thenReturn(establecimientosMasCercanos);
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
			
		ModelAndView mav = cont.busquedaPorMenorPrecio(requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("/busquedas/busqueda");
		verify(productoServicioMock, times(1)).busquedaPorMenorPrecio(any(ListaCompras.class));
		verify(productoServicioMock, times(1)).parseJsonData((List<Establecimiento>) establecimientosMasCercanos);
		verify(servicioListaComprasMock, times(1)).getByUserACtual(usuarioMock);
		verify(servicioLoginMock, times(1)).getUserByMail(any(String.class));
		verify(usuarioMock, times(1)).getFullAdress();
		
	}
	
	
	@Test
	public void buscandoPorEstablecimientoMasRapidoDebeDirigirALaVistaBusquedaDeBusquedas() {
		BusquedaController cont = new BusquedaController();
		
		Usuario usuarioMock = mock(Usuario.class);
		ListaCompras listaComprasMock = mock(ListaCompras.class);
		List<Establecimiento> establecimientosMasCercanos = new ArrayList<>();
		Establecimiento establecimientoMock = mock(Establecimiento.class);
		establecimientosMasCercanos.add(establecimientoMock);
		
		ServicioLogin servicioLoginMock = mock(ServicioLogin.class);
		ListaComprasServicio servicioListaComprasMock = mock(ListaComprasServicio.class);
		ProductoServicio productoServicioMock = mock(ProductoServicio.class);
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);	
		HttpSession sessionMock = mock(HttpSession.class); //sesion
		        
		
		cont.setServicioLogin(servicioLoginMock);		
		cont.setServicioListaComprasMock(servicioListaComprasMock);		
		cont.setServicioProductoMock(productoServicioMock);
		
		when(servicioLoginMock.getUserByMail(any(String.class))).thenReturn(usuarioMock);
		when(servicioListaComprasMock.getByUserACtual(usuarioMock)).thenReturn(listaComprasMock);
		when(usuarioMock.getFullAdress()).thenReturn("Peru 1169");
		when(productoServicioMock.busquedaPorMayorRapidezEnAtencion(any(ListaCompras.class))).thenReturn(establecimientosMasCercanos);
		when(requestMock.getSession()).thenReturn(sessionMock); //Cuando me piden la sesion retorno la sesion mockeada
			
		ModelAndView mav = cont.busquedaPorMayorRapidezDeAtencion(requestMock);
		
		assertThat(mav.getViewName()).isEqualTo("/busquedas/busqueda");
		verify(productoServicioMock, times(1)).busquedaPorMayorRapidezEnAtencion(any(ListaCompras.class));
		verify(productoServicioMock, times(1)).parseJsonData((List<Establecimiento>) establecimientosMasCercanos);
		verify(servicioListaComprasMock, times(1)).getByUserACtual(usuarioMock);
		verify(servicioLoginMock, times(1)).getUserByMail(any(String.class));
		verify(usuarioMock, times(1)).getFullAdress();
		

	}
	

}
