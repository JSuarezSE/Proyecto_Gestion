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

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Procesar Asistencia - Docente</title>

    <!-- Enlace a la hoja de estilos de Bootstrap y DatePicker -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #004d99;
            color: white;
            padding: 20px;
            text-align: center;
        }

        header h1 {
            margin: 0;
            font-size: 2.5em;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 40px 0;
        }

        .form-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .form-container h2 {
            color: #004d99;
            text-align: center;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .btn-primary {
            background-color: #004d99;
            border-color: #003366;
        }

        .btn-primary:hover {
            background-color: #003366;
            border-color: #002244;
        }

        .footer {
            background-color: #004d99;
            color: white;
            text-align: center;
            padding: 15px;
            position: absolute;
            width: 100%;
            bottom: 0;
        }

        .footer a {
            color: white;
            text-decoration: none;
        }

        .footer a:hover {
            text-decoration: underline;
        }

        .success-message {
            text-align: center;
            margin-top: 40px;
        }

        .btn-back {
            background-color: #86b3db;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            cursor: pointer;
            font-size: 1.2em;
            border-radius: 5px;
        }

        .btn-back:hover {
            background-color: #215888;
        }
    </style>

    <!-- Scripts de jQuery y DatePicker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function(){
            // Inicializar el datetimepicker
            $('#fecha_asistencia').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true
            });
        });
    </script>
</head>

<body>

    <header>
        <h1>Procesar Asistencia - Docente</h1>
    </header>

    <div class="container">
        <%
            // Verificar si el usuario está en sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                response.sendRedirect("../index.jsp"); // Redirigir al login si no hay usuario
            }

            // Obtener los valores del formulario
            Map<Integer, String> asistencia = new HashMap<Integer, String>(); // Cambiado para compatibilidad con Java 6
            String fechaAsistencia = request.getParameter("fecha_asistencia");

            // Validar que la fecha no sea nula o vacía
            if (fechaAsistencia == null || fechaAsistencia.trim().isEmpty()) {
                out.println("<p>La fecha de asistencia es obligatoria.</p>");
                return;
            }

            // Convertir la fecha a formato SQL
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = null;
            try {
                fecha = new Date(dateFormat.parse(fechaAsistencia).getTime());
            } catch (Exception e) {
                out.println("<p>Error al procesar la fecha: " + e.getMessage() + "</p>");
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
                    }
                }

                // Mostrar mensaje de éxito
                out.println("<div class='success-message'>");
                out.println("<p class='alert alert-success'>Asistencia registrada correctamente.</p>");
                out.println("<a href='asistencia.jsp' class='btn-back'>Volver a la gestion de asistencia</a>");
                out.println("</div>");
            } else {
                out.println("<p class='alert alert-danger'>Error al conectar con la base de datos.</p>");
                out.println("<a href='asistencia.jsp' class='btn-back'>Volver a la gestion de asistencia</a>");
            }
        %>
    </div>

    <footer class="footer">
        <p>&copy; 2025 Proyecto Gestión Suarez. Todos los derechos reservados. <a href="asistencia.jsp">Volver a la gestión de asistencia</a></p>
    </footer>

</body>
</html>
