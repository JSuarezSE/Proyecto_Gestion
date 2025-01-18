package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Asistencia;
import modelo.Usuario;

public class DocenteDAO {
    private Connection conexion;

    public DocenteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Obtener los estudiantes de un curso específico
    public List<Usuario> obtenerEstudiantesPorCurso(int idCurso) {
        List<Usuario> estudiantes = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido, u.email FROM usuarios u " +
                     "JOIN estudiantes_cursos ec ON u.id_usuario = ec.id_usuario " +
                     "WHERE ec.id_curso = ?";
    
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                Usuario estudiante = new Usuario();
                estudiante.setIdUsuario(rs.getInt("id_usuario"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estudiantes;
    }
    

    // Registrar la asistencia de un estudiante en un curso
    public boolean registrarAsistencia(int idUsuario, int idCurso, Date fecha, String estado) {
        String sql = "INSERT INTO asistencias (id_usuario, id_curso, fecha, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idCurso);
            stmt.setDate(3, new java.sql.Date(fecha.getTime()));
            stmt.setString(4, estado);  // "Presente" o "Ausente"
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar la asistencia: " + e.getMessage());
            return false;
        }
    }

    // Obtener la asistencia de un estudiante
    public List<Asistencia> obtenerAsistenciaPorEstudiante(int idUsuario) {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM asistencias WHERE id_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Asistencia asistencia = new Asistencia(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_curso"),
                        rs.getDate("fecha"),
                        rs.getString("estado")
                );
                asistencias.add(asistencia);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la asistencia: " + e.getMessage());
        }
        return asistencias;
    }
}
