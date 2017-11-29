package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.*;
import ar.edu.unlam.smartshop.modelview.ProductoCountModelView;
import ar.edu.unlam.smartshop.modelview.ProductoModelView;
import ar.edu.unlam.smartshop.servicios.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ProductoController {

    @Inject
    private ProductoServicio productoServicio;

    @Inject
    private ServicioLogin servicioLogin;
    
    @Inject
    private ListaComprasServicio listaComprasServicio;

    @Inject
    private PivotTableServicio pivotTableServicio;
    
    
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
        model.put("productoModel", new ProductoCountModelView());
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
        mp.put("records", productoServicio.listProductosEnEstablecimientos());
        mp.put("producto", producto);
        return new ModelAndView("/lista/addproducto", mp);
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

    @RequestMapping(path = "/productos/mas/buscados", method = RequestMethod.GET)
    public ModelAndView productosMasBuscados(HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        ModelMap model = new ModelMap();
        List<ProductoCountModelView> productos = productoServicio.getMasBuscados(loguedUser);

        if(productos.size()>0)
            model.put("records",productos);
        else
            model.put("error","No se encontraron suficientes datos para mostrar un analisis");

        return new ModelAndView("/masbuscados/lista", model);
    }

    @RequestMapping(path = "/productos/marcarofertado", method = RequestMethod.POST)
    public String marcarEnOfeta(@ModelAttribute("productoModel") ProductoCountModelView productoModel, HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        Producto producto = productoServicio.getById(productoModel.getId());

        List<PivotTable> pivots = producto.getPivotTables().stream().filter(o -> o.getProducto().getId().equals(productoModel.getId())).collect(Collectors.toList());
        for (PivotTable p:pivots){
            p.setEnOferta(true);
            pivotTableServicio.update(p);
        }

        return "redirect:/productos";
    }

    @RequestMapping(path = "/productos/quitaroferta", method = RequestMethod.POST)
    public String quitarOferta(@ModelAttribute("productoModel") ProductoCountModelView productoModel, HttpServletRequest request)
    {
        Usuario loguedUser = servicioLogin.getUserByMail((String) request.getSession().getAttribute("EMAIL"));
        Producto producto = productoServicio.getById(productoModel.getId());

        List<PivotTable> pivots = producto.getPivotTables().stream().filter(o -> o.getProducto().getId().equals(productoModel.getId())).collect(Collectors.toList());
        for (PivotTable p:pivots){
            p.setEnOferta(null);
            pivotTableServicio.update(p);
        }

        return "redirect:/productos";
    }
}
