package co.edu.unilibre.datos;

import java.time.LocalDateTime;

/**
 * Registro
 */
public class Registro {
	private LocalDateTime hora;
	private Moto moto;
	private Estado estado;

	public LocalDateTime obtenerHora() {
	    return this.hora;
	}

	public Moto obtenerMoto() {
	    return this.moto;
	}

	public Estado obtenerEstado() {
	    return this.estado;
	}

	public Registro(Moto moto, Estado estado) {
	    this.hora = LocalDateTime.now();
		this.moto = moto;
		this.estado = estado;
	}
}
