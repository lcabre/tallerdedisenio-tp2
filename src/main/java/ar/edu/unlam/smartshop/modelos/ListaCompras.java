package ar.edu.unlam.smartshop.modelos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ListaCompras{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_lista")
	private Integer id;

	@Column(name = "fecha", columnDefinition="DATE")
	private Date fecha;

	private Boolean finalizada;

	private Boolean actual;

	@ManyToMany(cascade =  CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "producto_listacompras",
			joinColumns = { @JoinColumn(name = "id_lista_compra") },
			inverseJoinColumns = { @JoinColumn(name = "id_producto") }
	)
	private List<Producto> productos = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public ListaCompras clone() {
		ListaCompras lista = new ListaCompras();
		for (Producto prod:this.productos) {
			lista.addProducto(prod);
		}
		//lista.setProductos(this.productos);
		lista.setActual(true);
		lista.setFinalizada(null);
		lista.setFecha(new Date());
		lista.setUsuario(this.usuario);

		return lista;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public void setProducto(Producto producto) {
		this.productos.add(producto);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public void addProducto(Producto producto){
		this.productos.add(producto);
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public Boolean getActual() {
		return actual;
	}

	public void setActual(Boolean actual) {
		this.actual = actual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
