package co.edu.unilibre.interaccion;

import java.time.Duration;
import java.util.ArrayList;

import co.edu.unilibre.datos.Estado;
import co.edu.unilibre.datos.Moto;
import co.edu.unilibre.datos.Pago;
import co.edu.unilibre.datos.Parqueadero;
import co.edu.unilibre.datos.Registro;
import co.edu.unilibre.datos.TipoPago;

/**
 * GestorParqueadero
 */
public class GestorParqueadero {

	public boolean registrarEntradaMoto(Parqueadero pq, Moto moto) {
	    ArrayList<Registro> motosParqueadas = pq.obtenerMotos();
		if (motosParqueadas.size() == 23) {
		    return false;
		}
		for (Registro rg :  motosParqueadas) {
		    if (rg.obtenerMoto().obtenerPlaca() == moto.obtenerPlaca()) {
				return false;
			}
		}

		Registro entrada = new Registro(moto, Estado.ENTRADA);
		motosParqueadas.add(entrada);

		return true;
	}

	public boolean registrarSalidaMoto(Parqueadero pq, String placa, TipoPago tipoPago) {
	    ArrayList<Registro> motosParqueadas = pq.obtenerMotos();
		for (int i = 0; i < motosParqueadas.size(); i++) {
		    Registro rg = motosParqueadas.get(i);
		    Moto localMoto = rg.obtenerMoto();
		    if (localMoto.obtenerPlaca().equalsIgnoreCase(placa)) {
                Registro salida = new Registro(localMoto, Estado.SALIDA);

                // Registramos el pago
                Pago pagoOk = registrarPago(pq, localMoto, rg, salida, tipoPago);

                if (pagoOk != null) {
                    pq.obtenerRegistros().add(rg);      // Guardamos los registros
                    pq.obtenerRegistros().add(salida);
                    motosParqueadas.remove(i);          // Sacamos la moto
                    return true;
                }
                return false;
            }
		}
	    return false;
	}

	public Pago registrarPago(Parqueadero pq, Moto mt, Registro entrada, Registro salida, TipoPago tipoPago) {
	    int precio = calcularPrecio(entrada, salida);
		if (precio == -1) {
		    return null;
		}

		Pago pago = new Pago(precio, tipoPago, mt);
		pq.obtenerPagos().add(pago);
	    return pago;
	}

	private int calcularPrecio(Registro entrada, Registro salida) {
	    long minutosTranscurridos = Duration.between(entrada.obtenerHora(), salida.obtenerHora()).toMinutes();

		if (minutosTranscurridos < 0) {
            return -1;
        }

        return (int) (minutosTranscurridos * 40);
	}

	public boolean generarReporte() {
	    return true;
	}
}
