package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.EstablecimientoServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class EstablecimientoController {

    @Inject
    private EstablecimientoServicio establecimientoServicio;

    @Inject
    private ServicioLogin servicioLogin;
    
    @RequestMapping(path = "/establecimiento/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelMap model = new ModelMap();
        model.put("establecimiento", new Establecimiento());
        return new ModelAndView("/establecimiento/create", model);
    }

    @RequestMapping(path = "/establecimiento", method = RequestMethod.POST)
    public String store(@ModelAttribute("establecimiento") Establecimiento establecimiento, HttpServletRequest request) {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        establecimientoServicio.save(establecimiento, loguedUser);
        return "redirect:/establecimientos";
    }

    @RequestMapping("/establecimientos")
    public ModelAndView productos(HttpServletRequest request){
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ModelMap model = new ModelMap();
        model.put("records",establecimientoServicio.getByUser(loguedUser));

        return new ModelAndView("/establecimiento/lista", model);
    }

	public void setServicioLogin(ServicioLogin servicioMock) {
		// TODO Auto-generated method stub
		this.servicioLogin = servicioMock;
	}

	public void setEstablecimientoServicio(EstablecimientoServicio establecimientoServicioMock) {
		// TODO Auto-generated method stub
		this.establecimientoServicio = establecimientoServicioMock;
	}
}
