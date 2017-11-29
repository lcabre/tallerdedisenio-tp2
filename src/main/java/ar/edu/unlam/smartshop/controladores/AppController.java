package ar.edu.unlam.smartshop.controladores;

import ar.edu.unlam.smartshop.modelos.*;
import ar.edu.unlam.smartshop.servicios.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class AppController {
    private Boolean primeravez = false;//borrar cuando todo este integrado

    @Inject
    private TipoUsuarioServicio tipoUsuarioServicio;//borrar cuando todo este integrado

    @Inject
    private CategoriaServicio categoriaServicio;//borrar cuando todo este integrado

    @Inject
    private UsuarioServicio usuarioServicio;//borrar cuando todo este integrado

    @Inject
    private ProductoServicio productoServicio;//borrar cuando todo este integrado

    @Inject
    private PivotTableServicio pivotTableServicio;

    @Inject
    private ServicioLogin servicioLogin;

    @Inject
    private ListaComprasServicio listaComprasServicio;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        if(!primeravez){
            init();
        }
        return new ModelAndView("index");
    }

    private void init(){
        primeravez = true;

        TipoUusuario establecimiento = new TipoUusuario();
        establecimiento.setNombre("Establecimiento");
        tipoUsuarioServicio.save(establecimiento);
        TipoUusuario cliente = new TipoUusuario();
        cliente.setNombre("Cliente");
        tipoUsuarioServicio.save(cliente);

        Categoria golosinas = new Categoria();
        golosinas.setNombre("Golosinas");
        categoriaServicio.save(golosinas);
        Categoria almacen = new Categoria();
        almacen.setNombre("Almacen");
        categoriaServicio.save(almacen);
        Categoria carniceria = new Categoria();
        carniceria.setNombre("Carniceria");
        categoriaServicio.save(carniceria);
        Categoria herramientas = new Categoria();
        herramientas.setNombre("Herramientas");
        categoriaServicio.save(herramientas);

        Producto huevos = new Producto();
        huevos.setNombre("Huevos");
        huevos.setCategoria(almacen);
        productoServicio.save(huevos);

        Producto lataTomate = new Producto();
        lataTomate.setNombre("Tomate en Cubos");
        lataTomate.setCategoria(almacen);
        productoServicio.save(lataTomate);

        Producto fideos = new Producto();
        fideos.setNombre("Fideos");
        fideos.setCategoria(almacen);
        productoServicio.save(fideos);

        Producto alicate = new Producto();
        alicate.setNombre("Alicate");
        alicate.setCategoria(herramientas);
        productoServicio.save(alicate);

        Producto halls = new Producto();
        halls.setNombre("Halls");
        halls.setCategoria(golosinas);
        productoServicio.save(halls);

        Producto chupetines = new Producto();
        chupetines.setNombre("Chupetines");
        chupetines.setCategoria(golosinas);
        productoServicio.save(chupetines);

        Producto caramelos = new Producto();
        caramelos.setNombre("Caramelos");
        caramelos.setCategoria(golosinas);
        productoServicio.save(caramelos);

        Producto peceto = new Producto();
        peceto.setNombre("Peceto");
        peceto.setCategoria(carniceria);
        productoServicio.save(peceto);

        Producto lomo = new Producto();
        lomo.setNombre("Lomo");
        lomo.setCategoria(carniceria);
        productoServicio.save(lomo);

        Producto asado = new Producto();
        asado.setNombre("Asado");
        asado.setCategoria(carniceria);
        productoServicio.save(asado);

        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");
        usuario.setApellido("Coto");
        usuario.setEmail("establecimiento@gmail.com");
        usuario.setPassword(Password.hashPassword("123123"));
        usuario.setBarrio("Ramos Mejia");
        usuario.setDireccion("Av. de Mayo");
        usuario.setNumero(1000);

        TipoUusuario tipo = tipoUsuarioServicio.getById(1);
        usuario.setTipo(tipo);

        Establecimiento dia = new Establecimiento();
        dia.setNombre("Dia");
        dia.setBarrio("Ramos Mejia");
        dia.setDireccion("Necochea");
        dia.setNumero(125);
        dia.setUsuario(usuario);
        dia.setRapidezEnAtencion(500);

        PivotTable p1 = new PivotTable();
        p1.setEstablecimiento(dia);
        p1.setProducto(huevos);
        p1.setPrecio(150f);
        pivotTableServicio.save(p1);

        Establecimiento coto = new Establecimiento();
        coto.setNombre("Coto");
        coto.setBarrio("Ramos Mejia");
        coto.setDireccion("Av. de Mayo");
        coto.setNumero(400);
        coto.setUsuario(usuario);
        coto.setRapidezEnAtencion(750);

        PivotTable p2 = new PivotTable();
        p2.setEstablecimiento(coto);
        p2.setProducto(lataTomate);
        p2.setPrecio(45f);
        pivotTableServicio.save(p2);

        PivotTable p3 = new PivotTable();
        p3.setEstablecimiento(coto);
        p3.setProducto(huevos);
        p3.setPrecio(200f);
        pivotTableServicio.save(p3);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Leonardo");
        usuario2.setApellido("Cabre");
        usuario2.setEmail("usuario@gmail.com");
        usuario2.setPassword(Password.hashPassword("123123"));
        usuario2.setBarrio("Ramos Mejia");
        usuario2.setDireccion("Av. Gral. San Martin");
        usuario2.setNumero(500);

        TipoUusuario tipo2 = tipoUsuarioServicio.getById(2);
        usuario2.setTipo(tipo2);

        usuarioServicio.save(usuario2);
        ////////////////////////////////
        ListaCompras lista = listaComprasServicio.getByUserACtual(usuario2);
        listaComprasServicio.addProducto(lista, huevos, usuario2);
    }
}
