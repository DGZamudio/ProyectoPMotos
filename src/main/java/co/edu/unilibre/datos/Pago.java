package co.edu.unilibre.datos;

/**
 * Pago
 */
public class Pago {
    private int valor;
	private TipoPago tipoPago;
	private Moto moto;

	public int obtenerValor() {
	    return this.valor;
	}

	public TipoPago obtenerTipoPago() {
	    return this.tipoPago;
	}

	public void modificarTipoPago(TipoPago tipoPago) {
	    this.tipoPago = tipoPago;
	}

	public Moto obtenerMoto() {
	    return this.moto;
	}

	public Pago(int valor, Moto moto) {
	    this.valor = valor;
		this.moto = moto;
	}
}
