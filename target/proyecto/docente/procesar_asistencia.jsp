<%@ page import="controlador.Conexion" %>
<%@ page import="controlador.DocenteDAO" %>
<%@ page import="modelo.Asistencia" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Procesar Asistencia - Docente</title>
</head>
<body>
    <%
        // Verificar si el usuario está en sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("../index.jsp"); // Redirigir al login si no hay usuario
        }

        // Obtener los valores del formulario
        Map<Integer, String> asistencia = new HashMap<>();
        for (String key : request.getParameterMap().keySet()) {
            if (key.startsWith("estado_")) {
                // El nombre del parámetro es "estado_<id_usuario>"
                int idEstudiante = Integer.parseInt(key.substring(7)); // Extraer el id del estudiante
                String estado = request.getParameter(key);
                asistencia.put(idEstudiante, estado);
            }
        }

        // Inicializar conexión a la base de datos
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectar();
        if (conexion != null) {
            DocenteDAO docenteDAO = new DocenteDAO(conexion);

            // Obtener el id del curso (esto puede venir del docente o de una variable de sesión)
            int idCurso = 1; // Este debe ser el id del curso correspondiente

            // Registrar asistencia para cada estudiante
            for (Map.Entry<Integer, String> entry : asistencia.entrySet()) {
                int idEstudiante = entry.getKey();
                String estado = entry.getValue();

                // Obtener la fecha y hora actual usando LocalDateTime
                LocalDateTime now = LocalDateTime.now();

                // Formatear la fecha a una cadena de tipo "yyyy-MM-dd HH:mm:ss" para insertarlo en la base de datos
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaStr = now.format(formatter); // Formato de fecha en cadena

                // Registrar la asistencia en la base de datos
                String sql = "INSERT INTO asistencias (id_usuario, id_curso, fecha, estado) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                    ps.setInt(1, idEstudiante);
                    ps.setInt(2, idCurso);
                    ps.setString(3, fechaStr); // Usar la cadena con la fecha formateada
                    ps.setString(4, estado);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            out.println("<p>Asistencia registrada correctamente.</p>");
        } else {
            out.println("<p>Error al conectar con la base de datos.</p>");
        }
    %>

    <a href="asistencia.jsp">Volver a la gestión de asistencia</a>
</body>
</html>
