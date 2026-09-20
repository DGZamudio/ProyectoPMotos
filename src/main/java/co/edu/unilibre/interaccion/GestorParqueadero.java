package co.edu.unilibre.interaccion;

import java.util.ArrayList;

import co.edu.unilibre.datos.Moto;
import co.edu.unilibre.datos.Parqueadero;

/**
 * GestorParqueadero
 */
public class GestorParqueadero {

	public boolean registrarEntradaMoto(Parqueadero pq, Moto moto) {
	    ArrayList<Moto> espacios = pq.obtenerMotos();
		if (espacios.size() == 23) {
		    return false;
		}

		espacios.add(moto);

		return true;
	}

	public boolean registrarSalidaMoto(String placa) {
	    return true;
	}

	public boolean registrarPago() {
	    return true;
	}

	public boolean generarReporte() {
	    return true;
	}
}
