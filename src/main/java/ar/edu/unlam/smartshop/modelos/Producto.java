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

    public Producto(String nombre, Categoria categoria) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
	}

	public Producto() {
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PivotTable> pivotTables = new ArrayList<>();

    @Transient
    private Establecimiento establecimientoMasCercano;

    @Transient
    private Establecimiento establecimientoConAtencionMasRapida;
    
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

    public Establecimiento getEstablecimientoMasCercano() {
        return establecimientoMasCercano;
    }

    public void setEstablecimientoMasCercano(Establecimiento establecimientoMasCercano) {
        this.establecimientoMasCercano = establecimientoMasCercano;
    }

    public Float getPrecioEnEstablecimiento() {
        return precioEnEstablecimiento;
    }

    public void setPrecioEnEstablecimiento(Float precioEnEstablecimiento) {
        this.precioEnEstablecimiento = precioEnEstablecimiento;
    }

    public Establecimiento getEstablecimientoMasCercano(String direccionCliente) {
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

            this.establecimientoMasCercano = Collections.min(establecimientos, Comparator.comparing(c -> c.getDistancia().getValue()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return establecimientoMasCercano;
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

	            this.establecimientoConAtencionMasRapida = Collections.max(establecimientos, Comparator.comparing(c -> c.getRapidezEnAtencion().byteValue()));

	       
	        return establecimientoConAtencionMasRapida;
	}
}
