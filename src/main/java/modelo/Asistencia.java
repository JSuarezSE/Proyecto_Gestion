package modelo;

import java.sql.Date; 

public class Asistencia {
    private int idAsistencia;
    private int idUsuario;  // Este es el id del estudiante
    private int idCurso;    // El id del curso al que pertenece el estudiante
    private Date fecha;
    private String estado;  // Puede ser "Presente" o "Ausente"

    // Constructor

    public Asistencia(int idUsuario, int idCurso, Date fecha, String estado) {
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
