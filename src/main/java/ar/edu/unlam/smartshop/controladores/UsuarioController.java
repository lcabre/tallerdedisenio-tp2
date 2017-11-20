package ar.edu.unlam.smartshop.controladores;


import ar.edu.unlam.smartshop.modelos.Password;
import ar.edu.unlam.smartshop.modelos.TipoUusuario;
import ar.edu.unlam.smartshop.modelos.Usuario;
import ar.edu.unlam.smartshop.modelview.UsuarioModelView;
import ar.edu.unlam.smartshop.servicios.TipoUsuarioServicio;
import ar.edu.unlam.smartshop.servicios.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;

@Controller
public class UsuarioController {

    @Inject
    private UsuarioServicio usuarioServicio;

    @Inject
    private TipoUsuarioServicio tipoUsuarioServicio;

    @RequestMapping(path = "/registro", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelMap model = new ModelMap();
        model.put("usuario", new UsuarioModelView());
        ArrayList<TipoUusuario> tipoUusuarios = (ArrayList<TipoUusuario>) tipoUsuarioServicio.list();
        model.put("tipousuarios", tipoUusuarios);
        return new ModelAndView("/usuario/create", model);
    }

    @RequestMapping(path = "/usuario/store", method = RequestMethod.POST)
    public String store(@ModelAttribute("usuario") UsuarioModelView usuarioModel) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioModel.getNombre());
        usuario.setApellido(usuarioModel.getApellido());
        usuario.setBarrio(usuarioModel.getBarrio());
        usuario.setDireccion(usuarioModel.getDireccion());
        usuario.setNumero(usuarioModel.getNumero());
        usuario.setEmail(usuarioModel.getEmail());
        String hashedPassword = Password.hashPassword(usuarioModel.getPassword());
        usuario.setPassword(hashedPassword);

        TipoUusuario tipo = new TipoUusuario();
        tipo = tipoUsuarioServicio.getById(usuarioModel.getIdTipoUsuario());
        usuario.setTipo(tipo);
        usuarioServicio.save(usuario);//Completar los metodos del servicio
        return "redirect:/login";//llevar a pagina de login
    }
}
