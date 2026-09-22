package co.edu.unilibre.interaccion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import co.edu.unilibre.datos.Moto;
import co.edu.unilibre.datos.Parqueadero;
import co.edu.unilibre.datos.TipoPago;

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
    public void registrarEntradaMotoOk2() {
        Moto mt = new Moto("ABC 123", "132432564");
        mt.modificarMarca("Yamaha");
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
	public void registrarEntradaMotoFail2() {
	    Moto mt = new Moto("ABC 123", "132432564");
	    gestor.registrarEntradaMoto(parqueadero, mt);

		Moto mt2 = new Moto("ABC 123", "132432564");
	    boolean res = gestor.registrarEntradaMoto(parqueadero, mt2);
		assertFalse(res);
	}

	@Test
    public void registrarSalidaMotoOk() {
        Moto mt = new Moto("ABC 123", "132432564");
	    gestor.registrarEntradaMoto(parqueadero, mt);

		boolean res = gestor.registrarSalidaMoto(parqueadero, mt.obtenerPlaca(), TipoPago.EFECTIVO);
		assertTrue(res);
    }

    @Test
    public void registrarSalidaMotoFail() {
        Moto mt = new Moto("ABC 123", "132432564");
        boolean res = gestor.registrarSalidaMoto(parqueadero, mt.obtenerPlaca(), TipoPago.EFECTIVO);
		assertFalse(res);
    }

    @Test
    public void registrarSalidaMotoFailDobleSalida() {
        Moto mt = new Moto("ABC 123", "132432564");
        gestor.registrarEntradaMoto(parqueadero, mt);

        gestor.registrarSalidaMoto(parqueadero, mt.obtenerPlaca(), TipoPago.EFECTIVO);

        boolean res = gestor.registrarSalidaMoto(parqueadero, mt.obtenerPlaca(), TipoPago.EFECTIVO);
        assertFalse(res);
    }

    @Test
    public void registrarPagoOk() {
        Moto mt = new Moto("ABC 123", "132432564");
        gestor.registrarEntradaMoto(parqueadero, mt);

        gestor.registrarSalidaMoto(parqueadero, mt.obtenerPlaca(), TipoPago.EFECTIVO);
        assertEquals(1, parqueadero.obtenerPagos().size());
    }

    @Test
    public void generarReporteOK() {
        File reporte = new File("reporte.txt");

        boolean res = gestor.generarReporte(parqueadero);
        assertTrue(res);

        reporte.delete();
    }
}
