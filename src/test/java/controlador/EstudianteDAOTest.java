package controlador;

import modelo.Asistencia;
import modelo.Notas;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import static org.junit.Assert.*;

public class EstudianteDAOTest {

    private Connection conexion;
    private EstudianteDAO estudianteDAO;

    @Before
    public void setUp() throws SQLException {
        // Establecer la conexión a la base de datos de prueba
        String url = "jdbc:mysql://localhost:3306/proyecto_gestion_test";
        String usuario = "root";
        String contraseña = "";
        conexion = DriverManager.getConnection(url, usuario, contraseña);
        estudianteDAO = new EstudianteDAO(conexion);
    }

    @Test
    public void testObtenerAsistenciasPorEstudiante() {
        List<Asistencia> asistencias = estudianteDAO.obtenerAsistenciasPorEstudiante(3);

        // Verificar que la lista no sea nula
        assertNotNull("La lista de asistencias no debería ser nula", asistencias);

        // Verificar que la lista no esté vacía
        assertFalse("La lista de asistencias no debería estar vacía", asistencias.isEmpty());

        // Verificar los datos de la primera asistencia
        Asistencia primeraAsistencia = asistencias.get(0);
        assertEquals("El id_usuario de la asistencia debería ser 3", 3, primeraAsistencia.getIdUsuario());
        assertEquals("El estado de la asistencia debería ser 'Tarde'", "Tarde", primeraAsistencia.getEstado());
        System.out.println("¡Éxito al obtener asistencias por estudiante! Se encontraron " + asistencias.size() + " asistencias.");
    }

    @Test
    public void testObtenerNotasPorEstudiante() {
        List<Notas> notas = estudianteDAO.obtenerNotasPorEstudiante(3);

        // Verificar que la lista no sea nula
        assertNotNull("La lista de notas no debería ser nula", notas);

        // Verificar que la lista no esté vacía
        assertFalse("La lista de notas no debería estar vacía", notas.isEmpty());

        // Verificar los datos de la primera nota
        Notas primeraNota = notas.get(0);
        assertEquals("El id_usuario de la nota debería ser 1", 3, primeraNota.getIdUsuario());
        assertEquals("El insumo1 de la nota debería ser 8.5", 8.5, primeraNota.getInsumo1(), 0.01);
        System.out.println("¡Éxito al obtener notas por estudiante! Se encontraron " + notas.size() + " notas.");
    }
}