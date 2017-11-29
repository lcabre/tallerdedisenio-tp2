package ar.edu.unlam.smartshop.modelos;

import ar.edu.unlam.smartshop.modelos.api.JsonMatrixResponse;
import com.google.gson.Gson;

import javax.persistence.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PivotTable> pivotTables = new ArrayList<>();

    @ManyToMany(mappedBy = "productos", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<ListaCompras> listaDeCompras = new ArrayList<>();

    @Transient
    private Establecimiento establecimientoBusqueda;

    @Transient
    private Float precioEnEstablecimiento;

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<PivotTable> getPivotTables() {
        return pivotTables;
    }

    public void setPivotTables(List<PivotTable> pivotTables) {
        this.pivotTables = pivotTables;
    }

    public void setPivotTable(PivotTable pivotTable){
        this.pivotTables.add(pivotTable);
    }

    public List<ListaCompras> getListaDeCompras() {
        return listaDeCompras;
    }

    public void setListaDeCompras(List<ListaCompras> listaDeCompras) {
        this.listaDeCompras = listaDeCompras;
    }

    public Establecimiento getEstablecimientoBusqueda() {
        return establecimientoBusqueda;
    }

    public void setEstablecimientoBusqueda(Establecimiento establecimientoBusqueda) {
        this.establecimientoBusqueda = establecimientoBusqueda;
    }

    public Float getPrecioEnEstablecimiento() {
        return precioEnEstablecimiento;
    }

    public void setPrecioEnEstablecimiento(Float precioEnEstablecimiento) {
        this.precioEnEstablecimiento = precioEnEstablecimiento;
    }

    public Establecimiento getEstablecimientoMasCercano(String direccionCliente) {

        try {
            direccionCliente = URLEncoder.encode(direccionCliente,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String API_KEY = "AIzaSyDwZrfQ2Nod2H7aqcYAfbCcSS_OdFnt9tY";
        Client client = ClientBuilder.newClient();
        String target = "https://maps.googleapis.com/maps/api/distancematrix/json?"+
                "origins="+direccionCliente+"&destinations=";

        List<Establecimiento> establecimientos = new ArrayList<>();

        for (PivotTable tablaPivot:this.getPivotTables()) {
            if(!establecimientos.contains(tablaPivot.getEstablecimiento())){
                establecimientos.add(tablaPivot.getEstablecimiento());
            }
        }

        try {
            for (Iterator<Establecimiento> i = establecimientos.iterator(); i.hasNext();) {
                Establecimiento e = i.next();
                if(i.hasNext())
                    target += URLEncoder.encode(e.getFullAddress()+"|", "UTF-8");
                else
                    target += URLEncoder.encode(e.getFullAddress(), "UTF-8");
            }
            target += "&key="+API_KEY;
            target += "&mode=walking";

            WebTarget webResource = client.target(target);
            Invocation.Builder invocationBuilder = webResource.request(MediaType.TEXT_PLAIN_TYPE);
            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Fallo : HTTP error code : "
                        + response.getStatus());
            }

            String jsonResponse = response.readEntity(String.class);
            JsonMatrixResponse distancias = new Gson().fromJson(jsonResponse, JsonMatrixResponse.class);

            for (Integer i=0; establecimientos.size() > i; i++){
                establecimientos.get(i).setDistancia(distancias.get(i));
                this.setPrecioEnEstablecimiento(establecimientos.get(i).getPrecioProducto(this));
                establecimientos.get(i).setProductoBuscado(this);
            }

            this.establecimientoBusqueda = Collections.min(establecimientos, Comparator.comparing(c -> c.getDistancia().getValue()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return establecimientoBusqueda;
    }

    public Establecimiento getEstablecimientoConMejorPuntuacion() {
        List<Establecimiento> establecimientos = new ArrayList<>();

        for (PivotTable tablaPivot:this.getPivotTables()) {
            if(!establecimientos.contains(tablaPivot.getEstablecimiento())){
                establecimientos.add(tablaPivot.getEstablecimiento());
            }
        }

        for (Integer i=0; establecimientos.size() > i; i++){
            this.setPrecioEnEstablecimiento(establecimientos.get(i).getPrecioProducto(this));
            establecimientos.get(i).setProductoBuscado(this);
        }

        this.establecimientoBusqueda = Collections.max(establecimientos, Comparator.comparing(Establecimiento::getRapidezEnAtencion));

        return establecimientoBusqueda;
    }

    public int cantidadBuscado(List<Producto> lista, Producto producto) {
        int count = 0;
        for (Producto p : lista) {
            if (p.equals(producto)) {
                count++;
            }
        }
        return count;
    }
}
