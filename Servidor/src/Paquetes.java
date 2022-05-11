/**
 * 
 */

/**
 * @author af.garciab
 *
 */
public class Paquetes {
		
	

	
	/**
	 * nombre del usuario
	 */
	private String nombreUsuario;
	
	private int idPaquete;
	
	private String estadoPaquete;

	public Paquetes(String nombreUsuario, int idPaquete, String estadoPaquete) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.idPaquete = idPaquete;
		this.estadoPaquete = estadoPaquete;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getIdPaquete() {
		return idPaquete;
	}

	public void setIdPaquete(int idPaquete) {
		this.idPaquete = idPaquete;
	}

	public String getEstadoPaquete() {
		return estadoPaquete;
	}

	public void setEstadoPaquete(String estadoPaquete) {
		this.estadoPaquete = estadoPaquete;
	}
	
	
	
	
}
