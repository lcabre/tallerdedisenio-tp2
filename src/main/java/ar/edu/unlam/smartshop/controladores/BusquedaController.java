package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.CategoriaServicio;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;
import ar.edu.unlam.smartshop.servicios.ProductoServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BusquedaController {

    @Inject
    private ProductoServicio productoServicio;

    @Inject
    private ServicioLogin servicioLogin;

    @Inject
    private ListaComprasServicio listaComprasServicio;

    @RequestMapping("/busquedas")
    public ModelAndView busquedas(){
        return new ModelAndView("/busquedas/busqueda");
    }

    @RequestMapping("/busquedas/cercania")
    public ModelAndView busquedaPorCercania(HttpServletRequest request){
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras listaCompras = listaComprasServicio.getByUserACtual(loguedUser);
        ModelMap model = new ModelMap();

        if(listaCompras!=null){
            List establecimientosMasCercanos = productoServicio.busquedaPorCercania(loguedUser.getFullAdress(),listaCompras);
            String json = productoServicio.parseJsonData((List<Establecimiento>) establecimientosMasCercanos);
            model.put("records",establecimientosMasCercanos);
            model.put("jsonData",json);
            model.put("direccionDelCliente",loguedUser.getFullAdress());
        }else{
            model.put("error","No tiene productos en su lista de compras");
        }

        return new ModelAndView("/busquedas/busqueda", model);
    }

    @RequestMapping("/busquedas/menorprecio")
    public ModelAndView busquedaPorMenorPrecio( HttpServletRequest request){
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras listaCompras = listaComprasServicio.getByUserACtual(loguedUser);
        ModelMap model = new ModelMap();

        if(listaCompras!=null){
            List establecimientosMasBaratos = productoServicio.busquedaPorMenorPrecio(listaCompras);
            String json = productoServicio.parseJsonData((List<Establecimiento>) establecimientosMasBaratos);

            model.put("records",establecimientosMasBaratos);
            model.put("jsonData",json);
            model.put("direccionDelCliente",loguedUser.getFullAdress());
        }else{
            model.put("error","No tiene productos en su lista de compras");
        }

        return new ModelAndView("/busquedas/busqueda", model);
    }

    @RequestMapping("/busquedas/mayorrapidezatencion")
    public ModelAndView busquedaPorMayorRapidezDeAtencion(HttpServletRequest request){
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras listaCompras = listaComprasServicio.getByUserACtual(loguedUser);
        ModelMap model = new ModelMap();

        if(listaCompras!=null){
            List establecimientosMejorPuntuados = productoServicio.busquedaPorMayorRapidezEnAtencion(listaCompras);
            String json = productoServicio.parseJsonData((List<Establecimiento>) establecimientosMejorPuntuados);

            model.put("records",establecimientosMejorPuntuados);
            model.put("jsonData",json);
            model.put("direccionDelCliente",loguedUser.getFullAdress());
        }else{
            model.put("error","No tiene productos en su lista de compras");
        }

        return new ModelAndView("/busquedas/busqueda", model);
    }

	public void setServicioLogin(ServicioLogin servicioMock) {
		// TODO Auto-generated method stub
		this.servicioLogin = servicioMock;
	}
	
	public void setServicioListaComprasMock(ListaComprasServicio servicioListaComprasMock) {
		// TODO Auto-generated method stub
		this.listaComprasServicio = servicioListaComprasMock;
	}

}
