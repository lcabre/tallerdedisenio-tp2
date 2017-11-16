package ar.edu.unlam.smartshop.modelos;

import ar.edu.unlam.smartshop.modelos.api.Distance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String barrio;
    private String direccion;
    private Integer numero;
    private Integer rapidezEnAtencion;

    @Transient
    private Distance distancia;

    @Transient
    private List<Producto> productosBuscados = new ArrayList<>();

    @OneToMany(mappedBy = "establecimiento",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PivotTable> pivotTables = new ArrayList<>();

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

    public  String getFullAddress(){
        return this.barrio+", "+this.direccion+" "+this.numero;
    }

    public Distance getDistancia() {
        return distancia;
    }

    public void setDistancia(Distance distancia) {
        this.distancia = distancia;
    }

    public List<PivotTable> getPivotTables() {
        return pivotTables;
    }

    public void setPivotTables(List<PivotTable> pivotTables) {
        this.pivotTables = pivotTables;
    }

    public List<Producto> getProductosBuscados() {
        return productosBuscados;
    }

    public void setProductosBuscados(List<Producto> productosBuscados) {
        this.productosBuscados = productosBuscados;
    }

    public void setProductoBuscado(Producto productosBuscado) {
        this.productosBuscados.add(productosBuscado);
    }

    public Float getPrecioProducto(Producto producto){
        for (PivotTable pivotTable:this.getPivotTables()) {
            if(pivotTable.getProducto().equals(producto))
                return pivotTable.getPrecio();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Establecimiento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", barrio='" + barrio + '\'' +
                ", direccion='" + direccion + '\'' +
                ", numero=" + numero +
                ", distancia=" + distancia +
                ", productosBuscados=" + productosBuscados +
                ", pivotTables=" + pivotTables +
                '}';
    }

    public Integer getRapidezEnAtencion() {
        return rapidezEnAtencion;
    }

    public void setRapidezEnAtencion(Integer rapidezEnAtencion) {
        this.rapidezEnAtencion = rapidezEnAtencion;
    }
}
