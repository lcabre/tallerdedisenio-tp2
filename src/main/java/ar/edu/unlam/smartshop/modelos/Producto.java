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
import java.net.URLEncoder;
import java.util.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer stock;
    private Float precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "establecimiento_producto",
            joinColumns = { @JoinColumn(name = "producto_id") },
            inverseJoinColumns = { @JoinColumn(name = "establecimiento_id") }
    )
    private List<Establecimiento> establecimientos = new ArrayList<>();

    @Transient
    private Establecimiento establecimientoMasCercano;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Establecimiento> getEstablecimientos() {
        return establecimientos;
    }

    public void setEstablecimientos(List<Establecimiento> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public Establecimiento getEstablecimientoMasCercano(String direccionCliente) {
        String API_KEY = "AIzaSyDwZrfQ2Nod2H7aqcYAfbCcSS_OdFnt9tY";
        Client client = ClientBuilder.newClient();
        String target = "https://maps.googleapis.com/maps/api/distancematrix/json?"+
                "origins="+direccionCliente+"&destinations=";

        try {
            for (Iterator<Establecimiento> i = this.establecimientos.iterator(); i.hasNext();) {
                Establecimiento e = i.next();
                if(i.hasNext())
                    target += URLEncoder.encode(e.getFullAddress()+"|", "UTF-8");
                else
                    target += URLEncoder.encode(e.getFullAddress(), "UTF-8");
            }
            target += "&key="+API_KEY;

            WebTarget webResource = client.target(target);
            Invocation.Builder invocationBuilder = webResource.request(MediaType.TEXT_PLAIN_TYPE);
            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Fallo : HTTP error code : "
                        + response.getStatus());
            }

            String jsonResponse = response.readEntity(String.class);
            JsonMatrixResponse distancias = new Gson().fromJson(jsonResponse, JsonMatrixResponse.class);

            for (Integer i=0; this.establecimientos.size() > i; i++){
                this.establecimientos.get(i).setDistancia(distancias.get(i));
            }

            return Collections.min(this.establecimientos, Comparator.comparing(c -> c.getDistancia().getValue()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return establecimientoMasCercano;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        if(!this.establecimientos.contains(establecimiento))
            this.establecimientos.add(establecimiento);
    }
}
