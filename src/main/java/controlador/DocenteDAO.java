package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Asistencia;
import modelo.Notas;
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

     // Método para agregar una nueva nota
    public boolean agregarNota(Notas nota) {
        String sql = "INSERT INTO notas (id_usuario, id_curso, insumo1, insumo2, insumo3) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, nota.getIdUsuario());
            ps.setInt(2, nota.getIdCurso());
            ps.setDouble(3, nota.getInsumo1());
            ps.setDouble(4, nota.getInsumo2());
            ps.setDouble(5, nota.getInsumo3());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para actualizar una nota existente
    public boolean actualizarNota(Notas nota) {
        String sql = "UPDATE notas SET insumo1 = ?, insumo2 = ?, insumo3 = ? WHERE id_nota = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, nota.getInsumo1());
            ps.setDouble(2, nota.getInsumo2());
            ps.setDouble(3, nota.getInsumo3());
            ps.setInt(4, nota.getIdNota());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para eliminar una nota
    public boolean eliminarNota(int idNota) {
        String sql = "DELETE FROM notas WHERE id_nota = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idNota);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para listar todas las notas de un curso
    public List<Notas> listarNotasPorCurso(int idCurso) {
        List<Notas> lista = new ArrayList<>();
        String sql = "SELECT * FROM notas WHERE id_curso = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notas nota = new Notas(
                    rs.getInt("id_nota"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_curso"),
                    rs.getDouble("insumo1"),
                    rs.getDouble("insumo2"),
                    rs.getDouble("insumo3")
                );
                lista.add(nota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean existeNota(int idUsuario, int idCurso) {
        String sql = "SELECT COUNT(*) FROM notas WHERE id_usuario = ? AND id_curso = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idCurso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int obtenerIdNota(int idUsuario, int idCurso) {
        String sql = "SELECT id_nota FROM notas WHERE id_usuario = ? AND id_curso = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);  // id_usuario
            ps.setInt(2, idCurso);    // id_curso
    
            // Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
    
            // Verificar si se encontró un resultado
            if (rs.next()) {
                return rs.getInt("id_nota");  // Retornar el id_nota
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejo de errores
        }
        return -1;  // Retornar -1 si no se encuentra la nota
    }
}
