package co.edu.poli.Examen_Vera.tests.unitaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.Parcial_Vera.modelo.Seguro;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVida;
import co.edu.poli.Parcial_Vera.modelo.Asegurado;

public class TestSeguro {

	@Test
	void bloquear_cambiaEstado() {
		Asegurado a = new Asegurado(1, "Test");

		Seguro s = new SeguroDeVida("123", "2025-12-25", "Activo", a, "Juan");

		String mensaje = s.bloquear();

		assertEquals("Inactivo", s.getEstado());
		assertTrue(mensaje.contains("BLOQUEADO"));
	}

	@Test
	void activar_cambiaEstado() {
		Asegurado a = new Asegurado(1, "Test");

		Seguro s = new SeguroDeVida("123", "2025-12-25", "Inactivo", a, "Juan");

		String mensaje = s.activar();

		assertEquals("Activo", s.getEstado());
		assertTrue(mensaje.contains("ACTIVADO"));
	}

	@Test
	void getters_retornaValoresCorrectos() {
		Asegurado a = new Asegurado(1, "Test");

		Seguro s = new SeguroDeVida("123", "2025-12-25", "Activo", a, "Juan");

		assertEquals("123", s.getNumero());
		assertEquals("2025-12-25", s.getFechaExpedicion());
		assertEquals("Activo", s.getEstado());
		assertEquals(a, s.getAsegurado());
	}

	@Test
	void setters_modificanValores() {
		Asegurado a = new Asegurado(1, "Test");
		Asegurado nuevo = new Asegurado(1, "Test");

		Seguro s = new SeguroDeVida("123", "2025-12-25", "Activo", a, "Juan");

		s.setNumero("999");
		s.setFechaExpedicion("2030-01-01");
		s.setEstado("Inactivo");
		s.setAsegurado(nuevo);

		assertEquals("999", s.getNumero());
		assertEquals("2030-01-01", s.getFechaExpedicion());
		assertEquals("Inactivo", s.getEstado());
		assertEquals(nuevo, s.getAsegurado());
	}

	@Test
	void toString_contieneDatos() {
		Asegurado a = new Asegurado(1, "Test");
		Seguro s = new SeguroDeVida("123", "2025-12-25", "Activo", a, "Juan");

		String texto = s.toString();

		assertTrue(texto.contains("123"));
		assertTrue(texto.contains("2025-12-25"));
	}
}