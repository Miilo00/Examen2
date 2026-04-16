package co.edu.poli.Parcial_Vera.tests.integracion;

import org.junit.jupiter.api.Test;

import co.edu.poli.Parcial_Vera.modelo.Asegurado;
import co.edu.poli.Parcial_Vera.servicios.DAOAsegurado;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestDAOAsegurado {

	@Test
	void readAll_noDebeRetornarNull() throws Exception {
		DAOAsegurado dao = new DAOAsegurado();

		List<Asegurado> lista = dao.readall();

		assertNotNull(lista);
	}

	@Test
	void readAll_listaInicializada() throws Exception {
		DAOAsegurado dao = new DAOAsegurado();

		List<Asegurado> lista = dao.readall();

		assertTrue(lista.size() >= 0);
	}

	@Test
	void readAll_objetosValidos() throws Exception {
		DAOAsegurado dao = new DAOAsegurado();

		List<Asegurado> lista = dao.readall();

		if (!lista.isEmpty()) {
			Asegurado a = lista.get(0);

			assertNotNull(a.getId());
			assertNotNull(a.getNombre());
		}
	}
}