package co.edu.unilibre.interaccion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import co.edu.unilibre.datos.Moto;
import co.edu.unilibre.datos.Parqueadero;

/**
 * TestGestorParqueadero
 */
public class TestGestorParqueadero {
    private Parqueadero parqueadero;
    private GestorParqueadero gestor;

    @Before
    public void setUp() {
        // Se ejecuta antes de cada test para garantizar un entorno limpio
        parqueadero = new Parqueadero();
        gestor = new GestorParqueadero();
    }

    @Test
	public void registrarEntradaMotoOk() {
	    Moto mt = new Moto("ABC 123", "132432564");
	    boolean res = gestor.registrarEntradaMoto(parqueadero, mt);
		assertTrue(res);
	}

	@Test
    public void registrarEntradaMotoFail() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 23; i++) {
            // Generar placas diferentes
            String placa = "";
            for (int j = 0; j < 3; j++) {
                placa += letras.charAt((i + j) % letras.length());
            }
            placa += " ";
            for (int j = 0; j < 3; j++) {
                placa += (i + j) % 10;
            }

            // Generar cedulas diferentes
            String cedula = "";
            for (int j = 0; j < 9; j++) {
                cedula += (i + j) % 10;
            }
            Moto mt = new Moto(placa, cedula);
            gestor.registrarEntradaMoto(parqueadero, mt);
        }
        Moto mt = new Moto("ZZZ 777", "192766656");
        boolean res = gestor.registrarEntradaMoto(parqueadero, mt);
        assertFalse(res);
    }

	@Test
    public void registrarSalidaMotoOk() {

    }

    @Test
    public void registrarSalidaMotoFail() {

    }
}
