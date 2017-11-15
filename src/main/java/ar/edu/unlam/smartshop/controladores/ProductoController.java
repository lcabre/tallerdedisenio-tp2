package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.Establecimiento;
import ar.edu.unlam.smartshop.modelos.Producto;
import ar.edu.unlam.smartshop.servicios.ProductoServicio;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;

import ar.edu.unlam.smartshop.servicios.CategoriaServicio;
import ar.edu.unlam.smartshop.servicios.ListaComprasServicio;


@Controller
public class ProductoController {

    @Inject
    private ProductoServicio productoServicio;
    
    @Inject
    private CategoriaServicio categoriaServicio;
    
    @Inject
    private ListaComprasServicio listaComprasServicio;
    
    
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
        Integer[] listaProductos = {1, 2, 3};//viene por post
        String direccionDelCliente = "Ramos mejia, necochea 100";//viene por post
        ModelMap model = new ModelMap();

        List establecimientosMasCercanos = productoServicio.busquedaPorCercania(direccionDelCliente,listaProductos);
        String json = productoServicio.parseJsonData((List<Establecimiento>) establecimientosMasCercanos);
        model.put("records",establecimientosMasCercanos);
        model.put("jsonData",json);
        model.put("direccionDelCliente",direccionDelCliente);

        return new ModelAndView("/producto/busqueda", model);
    }

    @RequestMapping("/busquedas/menorprecio")
    public ModelAndView busquedaPorMenorPrecio(){
        ModelMap model = new ModelMap();
        List establecimientosMasBaratos = productoServicio.busquedaPorMenorPrecio();
        model.put("records",establecimientosMasBaratos);
        String json = productoServicio.parseJsonData((List<Establecimiento>) establecimientosMasBaratos);
        model.put("records",establecimientosMasBaratos);
        model.put("jsonData",json);
        return new ModelAndView("/producto/menorprecio", model);
    }

    @RequestMapping("/busquedas/categorias")
    public ModelAndView busquedaPorCategoria()
    {
    	ModelMap mp = new ModelMap();
    	mp.put("categorias", categoriaServicio.listCategorias());
    	return new ModelAndView("/producto/busqueda", mp);
    }
    
    @RequestMapping(path = "/busquedas/categoria", method = RequestMethod.GET)
    public String processFormBusquedaProductosPorCategoria(@RequestParam("id_categoria") Integer id)
    {
    	return ("redirect: categoria/"+id);
    }
    
    @RequestMapping(path = "/busquedas/categoria/{id}", method = RequestMethod.GET)
    public ModelAndView busquedaProductosPorCategoria(@PathVariable Integer id)
    {
    	ModelMap mp = new ModelMap();
    	Producto pro = new Producto();
    	
    	mp.put("productos", productoServicio.findProductsByCategory(id));
    	mp.put("producto", pro);
    	return new ModelAndView("/producto/busqueda", mp);
    }
    
    @RequestMapping(path = "/busquedas/categoria/producto/save", method = RequestMethod.POST)
    public String agregarListaProductos(@ModelAttribute("producto") Producto pro)
    {
    	listaComprasServicio.save(pro.getId());   // cambiar integer a object.
    	return "redirect: ../../productos/lista";
    }
    
    @RequestMapping(path = "/busquedas/productos/lista", method = RequestMethod.GET)
    public ModelAndView listarProductosComprados()
    {
    	ModelMap mp = new ModelMap();
    	mp.put("listaCompras", listaComprasServicio.list());
    	return new ModelAndView("/producto/listaCompras", mp);
    }
}
