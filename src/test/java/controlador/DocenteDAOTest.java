package controlador;
import java.sql.Statement;
import modelo.Asistencia;
import modelo.Notas;
import java.sql.ResultSet;
import modelo.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import static org.junit.Assert.*;

public class DocenteDAOTest {

    private Connection conexion;
    private DocenteDAO docenteDAO;

    @Before
    public void setUp() throws SQLException {
        // Establecer la conexión a la base de datos de prueba
        String url = "jdbc:mysql://localhost:3306/proyecto_gestion_test";
        String usuario = "root";
        String contraseña = "";
        conexion = DriverManager.getConnection(url, usuario, contraseña);
        docenteDAO = new DocenteDAO(conexion);

        // Verificar que los datos de prueba existan
        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM usuarios WHERE id_usuario = 1");
            rs.next();
            if (rs.getInt(1) == 0) {
                throw new RuntimeException("Los datos de prueba no están configurados correctamente.");
            }
        }
    }

    @After
    public void tearDown() throws SQLException {
        // No es necesario borrar datos si se reutilizan
        // Cerrar la conexión
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }

    @Test
    public void testObtenerEstudiantesPorCurso() {
        List<Usuario> estudiantes = docenteDAO.obtenerEstudiantesPorCurso(1);
        assertNotNull("La lista de estudiantes no debería ser nula", estudiantes);
        assertEquals("Debería haber 1 estudiante en el curso", 1, estudiantes.size());
        assertEquals("El nombre del estudiante debería ser Juan", "Juan", estudiantes.get(0).getNombre());
    }

    @Test
    public void testRegistrarAsistencia() {
        boolean resultado = docenteDAO.registrarAsistencia(1, 1, new Date(System.currentTimeMillis()), "Presente");
        assertTrue("La asistencia debería haberse registrado correctamente", resultado);
    }

    @Test
    public void testObtenerAsistenciaPorEstudiante() {
        docenteDAO.registrarAsistencia(1, 1, new Date(System.currentTimeMillis()), "Presente");
        List<Asistencia> asistencias = docenteDAO.obtenerAsistenciaPorEstudiante(1);
        assertNotNull("La lista de asistencias no debería ser nula", asistencias);
        assertEquals("Debería haber 1 registro de asistencia", 1, asistencias.size());
        assertEquals("El estado de la asistencia debería ser 'Presente'", "Presente", asistencias.get(0).getEstado());
    }

    @Test
    public void testAgregarNota() {
        Notas nota = new Notas(1, 1, 1, 8.5, 7.0, 9.0);
        boolean resultado = docenteDAO.agregarNota(nota);
        assertTrue("La nota debería haberse agregado correctamente", resultado);
    }

    @Test
    public void testActualizarNota() {
        Notas nota = new Notas(1, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);

        int idNota = docenteDAO.obtenerIdNota(1, 1);
        Notas notaActualizada = new Notas(idNota, 1, 1, 9.0, 8.0, 10.0);
        boolean resultado = docenteDAO.actualizarNota(notaActualizada);
        assertTrue("La nota debería haberse actualizado correctamente", resultado);
    }

    @Test
    public void testEliminarNota() {
        Notas nota = new Notas(1, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);

        int idNota = docenteDAO.obtenerIdNota(1, 1);
        boolean resultado = docenteDAO.eliminarNota(idNota);
        assertTrue("La nota debería haberse eliminado correctamente", resultado);
    }

    @Test
    public void testListarNotasPorCurso() {
        Notas nota = new Notas(2, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);

        List<Notas> notas = docenteDAO.listarNotasPorCurso(1);
        assertNotNull("La lista de notas no debería ser nula", notas);
        assertEquals("Debería haber 1 nota en el curso", 1, notas.size());
        assertEquals("El insumo1 de la nota debería ser 8.5", 8.5, notas.get(0).getInsumo1(), 0.01);
    }

    @Test
    public void testExisteNota() {
        boolean existe = docenteDAO.existeNota(2, 1);
        assertTrue("La nota debería existir en la base de datos", existe);
    }

    @Test
    public void testObtenerIdNota() {
        Notas nota = new Notas(2, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);

        int idNota = docenteDAO.obtenerIdNota(1, 1);
        assertTrue("El ID de la nota debería ser mayor que 0", idNota > 0);
    }
}