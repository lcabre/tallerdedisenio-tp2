package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.CategoriaServicio;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;
import ar.edu.unlam.smartshop.servicios.ProductoServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ListaComprasController {

    @Inject
    private ServicioLogin servicioLogin;

    @Inject
    private ListaComprasServicio listaComprasServicio;

    @RequestMapping(path = "/lista/finalizar", method = RequestMethod.POST)
    public String finalizarListaDeCompras(@RequestParam("id") Integer id, HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras lista = listaComprasServicio.getByUserACtual(loguedUser);
        lista.setFinalizada(true);
        lista.setActual(false);
        listaComprasServicio.update(lista);

        return "redirect: /milista";
    }
}
