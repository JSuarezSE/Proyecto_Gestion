package controlador;

import modelo.Usuario;
import org.junit.Before;
import org.junit.Test;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.Assert.*; 

public class UsuarioDAOTest { // Asegúrate de que el nombre de la clase coincida con el archivo

    private Connection conexion;
    private UsuarioDAO usuarioDAO;

    @Before
    public void setUp() throws SQLException {
        // Establecer la conexión a la base de datos de prueba
        String url = "jdbc:mysql://localhost:3306/proyecto_gestion_test";
        String usuario = "root";
        String contraseña = "";
        conexion = DriverManager.getConnection(url, usuario, contraseña);
        usuarioDAO = new UsuarioDAO(conexion);
    }

  //  @Test 
  //  public void testRegistrarUsuario() {
  //      Usuario usuario = new Usuario(0, "Juan", "Perez", "juan@example.com", "12345678", "555-1234", 1);
 //       boolean resultado = usuarioDAO.registrarUsuario(usuario);
 //       assertTrue("El usuario debería haberse registrado correctamente", resultado); // Cambio en el orden de los parámetros
  //  }

    @Test 
    public void testObtenerUsuarioPorId() {
        // Suponemos que el ID generado es 2 (puede variar según la base de datos)
        Usuario usuarioObtenido = usuarioDAO.obtenerUsuarioPorId(2);
        assertNotNull("El usuario debería existir en la base de datos", usuarioObtenido); // Verifica que el usuario no sea nulo
        // Mensaje de éxito al obtener el usuario por ID
        System.out.println("¡Éxito al obtener usuario por ID! Usuario encontrado: " +
                        usuarioObtenido.getNombre() + " " + usuarioObtenido.getApellido());
        // Verificamos que los datos del usuario obtenido sean correctos
        assertEquals("El nombre del usuario debería ser Maria", "Maria", usuarioObtenido.getNombre());
    }

    @Test 
    public void testValidarLogin() {
        // Validamos el login con las credenciales correctas
        Usuario usuarioAutenticado = usuarioDAO.validarLogin("carlos@example.com", "11223344");
        assertNotNull("El login debería ser válido", usuarioAutenticado); // Cambio en el orden de los parámetros
        System.out.println("¡Login exitoso! Bienvenido, " + usuarioAutenticado.getNombre() + " " + usuarioAutenticado.getApellido());
        assertEquals("El nombre del usuario autenticado debería ser Carlos", "Carlos", usuarioAutenticado.getNombre()); // Cambio en el orden de los parámetros

        // Validamos el login con credenciales incorrectas
        Usuario usuarioNoAutenticado = usuarioDAO.validarLogin("carlos@example.com", "wrongpassword");
        assertNull("El login debería fallar con credenciales incorrectas", usuarioNoAutenticado); // Cambio en el orden de los parámetros
    }
}