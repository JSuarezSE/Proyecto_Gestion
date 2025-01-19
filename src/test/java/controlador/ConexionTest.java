package controlador;

import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class ConexionTest {

    @Test
    public void testConectar() {
        try {
            Connection conexion = Conexion.conectar();
            assertNotNull("La conexión no debería ser nula", conexion);
            assertFalse("La conexión debería estar abierta", conexion.isClosed());

            // Mensaje para indicar que la base de datos está activa y la conexión es exitosa
            System.out.println("¡La base de datos está activa y la conexión se ha establecido correctamente!");

            conexion.close();
        } catch (SQLException e) {
            fail("Se produjo una excepción al intentar conectar: " + e.getMessage());
        }
    }
}