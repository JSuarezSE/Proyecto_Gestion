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
        assertEquals("Debería haber 6 estudiantes en el curso", 6, estudiantes.size());
        assertEquals("El nombre del estudiante debería ser Juan", "Juan", estudiantes.get(0).getNombre());
        System.out.println("¡Éxito al obtener estudiantes por curso! Se encontraron " + estudiantes.size() + " estudiantes.");
    }
    
    @Test
    public void testRegistrarAsistencia() {
        boolean resultado = docenteDAO.registrarAsistencia(5, 1, new Date(System.currentTimeMillis()), "Presente");
        assertTrue("La asistencia debería haberse registrado correctamente", resultado);
        System.out.println("¡Éxito al registrar la asistencia! La asistencia se registró correctamente.");
    }
    
    @Test
    public void testObtenerAsistenciaPorEstudiante() {
        List<Asistencia> asistencias = docenteDAO.obtenerAsistenciaPorEstudiante(3);
        assertNotNull("La lista de asistencias no debería ser nula", asistencias);
        assertEquals("Debería haber 1 registro de asistencia", 1, asistencias.size());
        assertEquals("El estado de la asistencia debería ser 'Presente'", "Presente", asistencias.get(0).getEstado());
        System.out.println("¡Éxito al obtener la asistencia por estudiante! Se encontraron " + asistencias.size() + " registros de asistencia.");
    }
    
    @Test
    public void testAgregarNota() {
        Notas nota = new Notas(1, 1, 1, 8.5, 7.0, 9.0);
        boolean resultado = docenteDAO.agregarNota(nota);
        assertTrue("La nota debería haberse agregado correctamente", resultado);
        System.out.println("¡Éxito al agregar la nota! La nota se agregó correctamente.");
    }
    
    @Test
    public void testActualizarNota() {
        Notas nota = new Notas(1, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);
        int idNota = docenteDAO.obtenerIdNota(1, 1);
        Notas notaActualizada = new Notas(idNota, 1, 1, 9.0, 8.0, 10.0);
        boolean resultado = docenteDAO.actualizarNota(notaActualizada);
        assertTrue("La nota debería haberse actualizado correctamente", resultado);
        System.out.println("¡Éxito al actualizar la nota! La nota se actualizó correctamente.");
    }
    
    @Test
    public void testEliminarNota() {
        Notas nota = new Notas(1, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);
        int idNota = docenteDAO.obtenerIdNota(1, 1);
        boolean resultado = docenteDAO.eliminarNota(idNota);
        assertTrue("La nota debería haberse eliminado correctamente", resultado);
        System.out.println("¡Éxito al eliminar la nota! La nota se eliminó correctamente.");
    }
    
    @Test
    public void testListarNotasPorCurso() {
        List<Notas> notas = docenteDAO.listarNotasPorCurso(1);
        assertNotNull("La lista de notas no debería ser nula", notas);
        assertEquals("Debería haber 3 notas en el curso", 3, notas.size());
        assertEquals("El insumo1 de la nota debería ser 8.5", 8.5, notas.get(0).getInsumo1(), 0.01);
        System.out.println("¡Éxito al listar las notas por curso! Se encontraron " + notas.size() + " notas.");
    }
    
    @Test
    public void testExisteNota() {
        boolean existe = docenteDAO.existeNota(3, 1);
        assertTrue("La nota debería existir en la base de datos", existe);
        System.out.println("¡Éxito al verificar la existencia de la nota! La nota existe en la base de datos.");
    }
    
    @Test
    public void testObtenerIdNota() {
        Notas nota = new Notas(2, 1, 1, 8.5, 7.0, 9.0);
        docenteDAO.agregarNota(nota);
        int idNota = docenteDAO.obtenerIdNota(1, 1);
        assertTrue("El ID de la nota debería ser mayor que 0", idNota > 0);
        System.out.println("¡Éxito al obtener el ID de la nota! El ID obtenido es: " + idNota);
    } }