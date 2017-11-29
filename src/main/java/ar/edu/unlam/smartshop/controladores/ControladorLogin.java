package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

	@Inject
	private ServicioLogin servicioLogin;

	// Este metodo escucha la URL localhost:8080/NOMBRE_APP/login si la misma es invocada por metodo http GET
	@RequestMapping("/login")
	public ModelAndView loginForm() {
		ModelMap model = new ModelMap();
		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		return new ModelAndView("login", model);
	}

	@RequestMapping(path = "/login/validation", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		Usuario usuarioBuscado = servicioLogin.getUser(usuario);
		if (usuarioBuscado != null) {
			request.getSession().setAttribute("NOMBRE", usuarioBuscado.getApellido()+", "+usuarioBuscado.getNombre());
			request.getSession().setAttribute("TIPO", usuarioBuscado.getNombreTipo());
			request.getSession().setAttribute("EMAIL", usuarioBuscado.getEmail());
			return new ModelAndView("redirect:/");
		} else {
			model.put("error", "Usuario o clave incorrecta");
		}
		return new ModelAndView("login", model);
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("NOMBRE");
		request.getSession().removeAttribute("TIPO");
		request.getSession().removeAttribute("EMAiL");
		return "redirect:/";
	}

	public void setServicioLogin(ServicioLogin servicioMock) {
		// TODO Auto-generated method stub
		this.servicioLogin = servicioMock;
	}
}
