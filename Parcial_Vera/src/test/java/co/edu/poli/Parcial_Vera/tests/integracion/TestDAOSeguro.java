package co.edu.poli.Parcial_Vera.tests.integracion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.Parcial_Vera.modelo.Seguro;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVida;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVehiculo;
import co.edu.poli.Parcial_Vera.modelo.Asegurado;
import co.edu.poli.Parcial_Vera.servicios.DAOSeguro;

public class TestDAOSeguro {

    DAOSeguro dao = new DAOSeguro();

    @Test
    void create_vida_y_readone() throws Exception {

        String id = String.valueOf(System.currentTimeMillis());

        Asegurado a = new Asegurado(1, "Test");

        SeguroDeVida s = new SeguroDeVida(
                id,
                "2025-12-25",
                "Activo",
                a,
                "Juan"
        );

        String result = dao.create(s);

        assertTrue(result.contains("guardada"));

        Seguro t = dao.readone(id);

        assertNotNull(t);
        assertTrue(t instanceof SeguroDeVida);
    }

    @Test
    void create_vehiculo_y_readone() throws Exception {

        String id = String.valueOf(System.currentTimeMillis() + 1);

        Asegurado a = new Asegurado(1, "Test");

        SeguroDeVehiculo s = new SeguroDeVehiculo(
                id,
                "2025-12-25",
                "Activo",
                a,
                "Toyota"
        );

        String result = dao.create(s);

        assertTrue(result.contains("guardada"));

        Seguro t = dao.readone(id);

        assertNotNull(t);
        assertTrue(t instanceof SeguroDeVehiculo);
    }

    @Test
    void readone_noExiste() throws Exception {

        Seguro t = dao.readone("000000");

        assertNull(t);
    }
}