package controlador;

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para registrar un nuevo usuario con rol
    public boolean registrarUsuario(Usuario usuario) {
        String sqlUsuario = "INSERT INTO usuarios (nombre, apellido, email, cedula, telefono, id_role) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlEstudianteCurso = "INSERT INTO estudiantes_cursos (id_usuario, id_curso) VALUES (?, ?)";
    
        try (PreparedStatement stmtUsuario = conexion.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtEstudianteCurso = conexion.prepareStatement(sqlEstudianteCurso)) {
    
            // Insertar el usuario en la tabla usuarios
            stmtUsuario.setString(1, usuario.getNombre());
            stmtUsuario.setString(2, usuario.getApellido());
            stmtUsuario.setString(3, usuario.getEmail());
            stmtUsuario.setString(4, usuario.getCedula());
            stmtUsuario.setString(5, usuario.getTelefono());
            stmtUsuario.setInt(6, usuario.getIdRole());
    
            int filasAfectadas = stmtUsuario.executeUpdate();
    
            if (filasAfectadas > 0) {
                // Obtener el ID generado para el usuario recién insertado
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);
    
                    // Solo asignar curso si el usuario es un estudiante (id_role = 1)
                    if (usuario.getIdRole() == 1) {
                        stmtEstudianteCurso.setInt(1, idUsuario);
                        stmtEstudianteCurso.setInt(2, 1); // Asignar el id_curso = 1
                        stmtEstudianteCurso.executeUpdate();
                    }
    
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Usar el constructor correcto
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),  // idUsuario
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("cedula"),
                    rs.getString("telefono"),
                    rs.getInt("id_role")
                );
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
        }
        return null;
    }

    // Método para validar el login de un usuario
    public Usuario validarLogin(String email, String cedula) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND cedula = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, cedula);
    
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Crear un objeto Usuario con los datos
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));  // Asignar el idUsuario
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setIdRole(rs.getInt("id_role")); // Obtener el rol del usuario
                return usuario; // Retornar el usuario autenticado
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el login: " + e.getMessage());
        }
        return null; // Retornar null si las credenciales no coinciden
    }
}
