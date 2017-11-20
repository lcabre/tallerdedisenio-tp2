package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.*;
import ar.edu.unlam.smartshop.modelview.ProductoModelView;
import ar.edu.unlam.smartshop.servicios.*;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ProductoController {

    @Inject
    private ProductoServicio productoServicio;

    @Inject
    private ServicioLogin servicioLogin;
    
    @Inject
    private CategoriaServicio categoriaServicio;
    
    @Inject
    private ListaComprasServicio listaComprasServicio;
    
    
    @RequestMapping(path = "/producto/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ModelMap model = new ModelMap();
        model.put("producto", new ProductoModelView());
        model.put("productos", productoServicio.list());
        model.put("establecimientos", loguedUser.getEstablecimientos());
        return new ModelAndView("/producto/create", model);
    }

    @RequestMapping(path = "/producto", method = RequestMethod.POST)
    public String store(@ModelAttribute("producto") ProductoModelView productoModel) {
        productoServicio.save(productoModel);
        return "redirect:/productos";
    }

    @RequestMapping("/productos")
    public ModelAndView productos(HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));

        if(loguedUser.getEstablecimientos().size()>0)
            model.put("records",productoServicio.listByUser(loguedUser));
        else
            model.put("error","Debe tener por lo menos un establecimiento antes de poder agregar productos");

        return new ModelAndView("/producto/lista", model);
    }

    @RequestMapping("/busquedas/productos")
    public ModelAndView mostrarTodosLosProdutos() {
        ModelMap mp = new ModelMap();
        Producto producto = new Producto();
        mp.put("records", productoServicio.list());
        mp.put("producto", producto);
        return new ModelAndView("/lista/addproducto", mp);
    }
    
    @RequestMapping(path = "/busquedas/categoria/producto/save", method = RequestMethod.POST)
    public String agregarListaProductos(@ModelAttribute("producto") Producto producto, HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ListaCompras lista = listaComprasServicio.getByUserACtual(loguedUser);
    	listaComprasServicio.addProducto(lista, producto, loguedUser);
    	return "redirect: /milista";
    }
    
    @RequestMapping(path = "/milista", method = RequestMethod.GET)
    public ModelAndView listarProductosComprados(HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
    	ModelMap model = new ModelMap();
        ListaCompras listaCompras = listaComprasServicio.getByUserACtual(loguedUser);

        if(listaCompras!=null)
            model.put("lista",listaCompras);
        else
            model.put("error","No tiene producos agregados a la lista de compras");

    	return new ModelAndView("/lista/listaCompras", model);
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
}
