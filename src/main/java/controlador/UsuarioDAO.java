package controlador;

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para registrar un nuevo usuario con rol
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, cedula, telefono, id_role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getCedula());
            stmt.setString(5, usuario.getTelefono());
            stmt.setInt(6, usuario.getIdRole()); // Asignar el rol correctamente
            return stmt.executeUpdate() > 0;
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
                Usuario usuario = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("cedula"),
                    rs.getString("telefono"),
                    rs.getInt("id_role")
                );
                usuario.setIdUsuario(rs.getInt("id_usuario")); // Asignar ID del usuario
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
          // Si se encuentra un registro, se crea un objeto Usuario con los datos
          Usuario usuario = new Usuario();
         
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
