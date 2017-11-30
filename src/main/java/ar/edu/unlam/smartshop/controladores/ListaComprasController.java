package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.ListaCompras;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;
import ar.edu.unlam.smartshop.servicios.ServicioLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ListaComprasController {

    @Inject
    private ServicioLogin servicioLogin;

    @Inject
    private ListaComprasServicio listaComprasServicio;

    @RequestMapping(path = "/lista/finalizar", method = RequestMethod.POST)
    public String finalizarListaDeCompras(HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras lista = listaComprasServicio.getByUserACtual(loguedUser);
        lista.setFinalizada(true);
        lista.setActual(null);
        listaComprasServicio.update(lista);

        return "redirect:/milista";
    }

    @RequestMapping(path = "/historial/buscar", method = RequestMethod.POST)
    public String reutilizarLista(@RequestParam("id") Integer id, HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));

        ListaCompras lista = listaComprasServicio.getById(id);
        ListaCompras listaActual = listaComprasServicio.getByUserACtual(loguedUser);
        ListaCompras nuevaLista = lista.clone();

        if(listaActual!=null){
            listaActual.setProductos(nuevaLista.getProductos());
            listaComprasServicio.update(listaActual);
        }else
            listaComprasServicio.save(nuevaLista);

        return "redirect:/milista";
    }

    @RequestMapping(path = "/mihistorial", method = RequestMethod.GET)
    public ModelAndView listarHistorialDeCompras(HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ModelMap model = new ModelMap();
        List listaCompras = listaComprasServicio.getByUserHistorial(loguedUser);

        if(listaCompras.size()>0)
            model.put("lista",listaCompras);
        else
            model.put("error","No tiene listas de compras guardadas");

        return new ModelAndView("/historial/historial", model);
    }

    @RequestMapping(path = "/busquedas/categoria/producto/save", method = RequestMethod.POST)
    public String agregarProducto(@ModelAttribute("producto") Producto producto, HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras lista = listaComprasServicio.getByUserACtual(loguedUser);
        listaComprasServicio.addProducto(lista, producto, loguedUser);
        return "redirect:/milista";
    }
}