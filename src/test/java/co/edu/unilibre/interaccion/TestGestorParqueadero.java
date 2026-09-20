package co.edu.unilibre.interaccion;

import org.junit.Before;
import org.junit.Test;

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

	}

	@Test
    public void registrarEntradaMotoFail() {

    }

	@Test
    public void registrarSalidaMotoOk() {

    }

    @Test
    public void registrarSalidaMotoFail() {

    }
}
