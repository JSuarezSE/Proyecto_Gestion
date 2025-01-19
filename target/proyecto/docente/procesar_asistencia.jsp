<%@ page import="controlador.Conexion" %>
<%@ page import="controlador.DocenteDAO" %>
<%@ page import="modelo.Asistencia" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    // Verificar si el usuario está en sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("../index.jsp"); // Redirigir al login si no hay usuario
        return;
    }

    // Obtener los valores del formulario
    Map<Integer, String> asistencia = new HashMap<Integer, String>(); // Cambiado para compatibilidad con Java 6
    String fechaAsistencia = request.getParameter("fecha_asistencia");

    // Validar que la fecha no sea nula o vacía
    if (fechaAsistencia == null || fechaAsistencia.trim().isEmpty()) {
        response.sendRedirect("asistencia.jsp?mensaje=La fecha de asistencia es obligatoria");
        return;
    }

    // Convertir la fecha a formato SQL
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date fecha = null;
    try {
        fecha = new Date(dateFormat.parse(fechaAsistencia).getTime());
    } catch (Exception e) {
        response.sendRedirect("asistencia.jsp?mensaje=Error al procesar la fecha: " + e.getMessage());
        return;
    }

    // Inicializar conexión a la base de datos
    Conexion conexionDB = new Conexion();
    Connection conexion = conexionDB.conectar();
    if (conexion != null) {
        DocenteDAO docenteDAO = new DocenteDAO(conexion);

        // Obtener el id del curso (esto puede venir del docente o de una variable de sesión)
        int idCurso = 1; // Este debe ser el id del curso correspondiente

        // Registrar asistencia para cada estudiante
        for (String key : request.getParameterMap().keySet()) {
            if (key.startsWith("estado_")) {
                // El nombre del parámetro es "estado_<id_usuario>"
                int idEstudiante = Integer.parseInt(key.substring(7)); // Extraer el id del estudiante
                String estado = request.getParameter(key);
                asistencia.put(idEstudiante, estado);
            }
        }

        for (Map.Entry<Integer, String> entry : asistencia.entrySet()) {
            int idEstudiante = entry.getKey();
            String estado = entry.getValue();

            // Crear un objeto Asistencia para cada estudiante
            Asistencia nuevaAsistencia = new Asistencia(idEstudiante, idCurso, fecha, estado);

            // Registrar la asistencia en la base de datos
            String sql = "INSERT INTO asistencias (id_usuario, id_curso, fecha, estado) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setInt(1, nuevaAsistencia.getIdUsuario());
                ps.setInt(2, nuevaAsistencia.getIdCurso());
                ps.setDate(3, nuevaAsistencia.getFecha());
                ps.setString(4, nuevaAsistencia.getEstado());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("asistencia.jsp?mensaje=Error al registrar la asistencia: " + e.getMessage());
                return;
            }
        }

        // Redirigir a asistencia.jsp con un mensaje de éxito
      out.println("<script>alert('Asistencia guardada correctamente.'); window.location.href='asistencia.jsp';</script>");
    } else {
        out.println("<script>alert('Error al conectar con la base de datos.'); window.location.href='asistencia.jsp';</script>");
    }
%>