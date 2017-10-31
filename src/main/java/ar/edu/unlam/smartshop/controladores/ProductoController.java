package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.servicios.ProductoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class ProductoController {

    @Inject
    private ProductoServicio productoServicio;

    @RequestMapping(path = "/producto/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelMap model = new ModelMap();
        model.put("producto", new Producto());
        return new ModelAndView("/producto/create", model);
    }

    @RequestMapping(path = "/producto", method = RequestMethod.POST)
    public String store(@ModelAttribute("producto") Producto producto) {
        productoServicio.save(producto);
        //return null;
        return "redirect:/productos";
    }

    @RequestMapping("/productos")
    public ModelAndView productos(){
        ModelMap model = new ModelMap();
        model.put("records",productoServicio.list());
        return new ModelAndView("/producto/lista", model);
    }

    @RequestMapping("/busquedas")
    public ModelAndView busquedas(){
        return new ModelAndView("/producto/busqueda");
    }

    @RequestMapping("/busquedas/cercania")
    public ModelAndView busquedaPorCercania(){
        ModelMap model = new ModelMap();
        model.put("records",productoServicio.busquedaPorCercania());
        return new ModelAndView("/producto/busqueda", model);
    }
}
