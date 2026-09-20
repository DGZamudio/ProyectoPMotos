package co.edu.unilibre.datos;

/**
 * Moto
 */
public class Moto {
	private String placa;
	private String marca;
	private String propietario;

	public String obtenerPlaca() {
	    return this.placa;
	}

	public String obtenerMarca() {
	    return this.marca;
	}

	public void modificarMarca(String marca) {
	    this.marca = marca;
	}

	public String obtenerPropietario() {
	    return this.propietario;
	}

	public Moto(String placa, String propietario) {
	    this.placa = placa;
		this.propietario = propietario;
	}
}
