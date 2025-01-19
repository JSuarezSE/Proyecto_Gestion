package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Asistencia;
import modelo.Notas;

public class EstudianteDAO {

    private Connection conexion;

    public EstudianteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene las asistencias de un estudiante por su idUsuario.
     *
     * @param idUsuario El id del estudiante.
     * @return Una lista de objetos Asistencia.
     */
    public List<Asistencia> obtenerAsistenciasPorEstudiante(int idUsuario) {
      List<Asistencia> asistencias = new ArrayList<>();
      String sql = "SELECT id_asistencia, id_curso, fecha, estado FROM asistencias WHERE id_usuario = ?";
  
      try (PreparedStatement ps = conexion.prepareStatement(sql)) {
          ps.setInt(1, idUsuario);
          ResultSet rs = ps.executeQuery();
  
          while (rs.next()) {
              Asistencia asistencia = new Asistencia(
                  idUsuario,
                  rs.getInt("id_curso"),
                  rs.getDate("fecha"),
                  rs.getString("estado")
              );
              asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
              asistencias.add(asistencia);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
      
      return asistencias;
  }
  public List<Notas> obtenerNotasPorEstudiante(int idUsuario) {
    List<Notas> notas = new ArrayList<>();
    String sql = "SELECT id_nota, id_curso, insumo1, insumo2, insumo3, total FROM notas WHERE id_usuario = ?";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Notas nota = new Notas(
                rs.getInt("id_nota"),
                idUsuario,
                rs.getInt("id_curso"),
                rs.getDouble("insumo1"),
                rs.getDouble("insumo2"),
                rs.getDouble("insumo3")
            );
            notas.add(nota);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return notas;
}
}