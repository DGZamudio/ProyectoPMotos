package co.edu.unilibre.datos;

import java.util.ArrayList;

/**
 * Parqueadero
 */
public class Parqueadero {
    private ArrayList<Registro> motosParqueadas;
    private ArrayList<Pago> pagos;
    private ArrayList<Registro> registros;

    public ArrayList<Registro> obtenerMotos() {
        return this.motosParqueadas;
    }

    public ArrayList<Pago> obtenerPagos() {
        return this.pagos;
    }

    public ArrayList<Registro> obtenerRegistros() {
        return this.registros;
    }

    public Parqueadero () {
        this.motosParqueadas = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.registros = new ArrayList<>();
    }
}
