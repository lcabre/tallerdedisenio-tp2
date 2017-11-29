package ar.edu.unlam.smartshop.modelos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String barrio;
    private String direccion;
    private Integer numero;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUusuario tipo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ListaCompras> listaDeCompras = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Establecimiento> establecimientos = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public TipoUusuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUusuario tipo) {
        this.tipo = tipo;
    }

    public List<ListaCompras> getListaDeCompras() {
        return listaDeCompras;
    }

    public void setListaDeCompras(List<ListaCompras> listaDeCompras) {
        this.listaDeCompras = listaDeCompras;
    }

    public List<Establecimiento> getEstablecimientos() {
        return establecimientos;
    }

    public void setEstablecimientos(List<Establecimiento> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public void setEstablecimiento(Establecimiento establecimiento){
        this.establecimientos.add(establecimiento);
    }

    public void setListaDeCompra(ListaCompras lista){
        this.listaDeCompras.add(lista);
    }

    public String getFullAdress(){
        return this.barrio+", "+this.direccion+" "+this.numero;
    }

}
