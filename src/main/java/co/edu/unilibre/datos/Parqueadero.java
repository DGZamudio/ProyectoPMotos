package co.edu.unilibre.datos;

import java.util.ArrayList;

/**
 * Parqueadero
 */
public class Parqueadero {
    private ArrayList<Moto> motos;
    private ArrayList<Pago> pagos;
    private ArrayList<Registro> registros;

    public ArrayList<Moto> obtenerMotos() {
        return this.motos;
    }

    public ArrayList<Pago> obtenerPagos() {
        return this.pagos;
    }

    public ArrayList<Registro> obtenerRegistros() {
        return this.registros;
    }

    public Parqueadero () {
        this.motos = new ArrayList<Moto>();
        this.pagos = new ArrayList<Pago>();
        this.registros = new ArrayList<Registro>();
    }
}
