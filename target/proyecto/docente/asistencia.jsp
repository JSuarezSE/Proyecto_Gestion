<%@ page import="controlador.Conexion" %> 
<%@ page import="controlador.DocenteDAO" %> 
<%@ page import="modelo.Usuario" %> 
<%@ page import="java.sql.Connection" %> 
<%@ page import="java.util.List" %> 

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion de Asistencia - Docente</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- Incluyendo jQuery y jQuery UI para el Datepicker -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

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
        }
        h2 {
            color: #004d99;
            text-align: center;
        }
        .welcome-info {
            margin: 20px 0;
            text-align: center;
        }
        .welcome-info p {
            font-size: 1.2em;
        }
        .btn {
            background-color: #004d99;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            font-size: 1em;
        }
        .btn:hover {
            background-color: #003366;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 15px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #004d99;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        td input[type="radio"] {
            margin-right: 10px;
        }
        footer {
            background-color: #004d99;
            color: white;
            text-align: center;
            padding: 15px;
            position: absolute;
            width: 100%;
            bottom: 0;
        }
    </style>
</head>
<body>

    <header>
        <h1>Gestion de Asistencia - Docente</h1>
    </header>

    <div class="container">
        <%
            // Verificar si el usuario está en sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                response.sendRedirect("../index.jsp"); // Redirigir al login si no hay usuario
            }
        %>

        <div class="welcome-info">
            <h2>Buenas, <%= usuario.getNombre() + " " + usuario.getApellido() %></h2>
            <p>Rol ID: <%= usuario.getIdRole() %></p>
        </div>

        <form method="post" action="dashboard_docente.jsp" style="text-align: center;">
            <button type="submit" class="btn">Volver al Dashboard</button>
        </form>

        <h2>Registrar Asistencia</h2>

        <%
            // Inicializar conexión a la base de datos
            Conexion conexionDB = new Conexion();
            Connection conexion = conexionDB.conectar();
            List<Usuario> estudiantes = null;

            if (conexion != null) {
                DocenteDAO docenteDAO = new DocenteDAO(conexion);
                int idCurso = 1; // Aquí debes poner el id del curso adecuado
                estudiantes = docenteDAO.obtenerEstudiantesPorCurso(idCurso);
            } else {
                out.println("<p>Error al conectar con la base de datos.</p>");
            }

            if (estudiantes != null && !estudiantes.isEmpty()) {
        %>

        <form method="post" action="procesar_asistencia.jsp">
            <table>
                <thead>
                    <tr>
                        <th>Nombre del Estudiante</th>
                        <th>Asistencia</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Mostrar los estudiantes en la tabla
                        for (Usuario estudiante : estudiantes) {
                    %>
                    <tr>
                        <td><%= estudiante.getNombre() + " " + estudiante.getApellido() %></td>
                        <td>
                            <label><input type="radio" name="estado_<%= estudiante.getIdUsuario() %>" value="Presente" required> Presente</label>
                            <label><input type="radio" name="estado_<%= estudiante.getIdUsuario() %>" value="Ausente" required> Ausente</label>
                            <label><input type="radio" name="estado_<%= estudiante.getIdUsuario() %>" value="Tarde" required> Tarde</label>
                        </td>
                        <input type="hidden" name="estudiante_<%= estudiante.getIdUsuario() %>" value="<%= estudiante.getIdUsuario() %>">
                    </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>

            <div style="text-align: center; margin-top: 20px;">
                <label for="fecha_asistencia">Fecha de Asistencia:</label>
                <input type="text" id="fecha_asistencia" name="fecha_asistencia" required>
                <script>
                    $(document).ready(function() {
                        // Inicializar el DatePicker
                        $("#fecha_asistencia").datepicker({
                            dateFormat: 'yy-mm-dd', // Formato de fecha: YYYY-MM-DD
                            changeMonth: true,
                            changeYear: true
                        });
                    });
                </script>
                <button type="submit" class="btn" name="accion" value="registrarAsistencia">Registrar Asistencia</button>
            </div>
        </form>

        <%
            } else {
                out.println("<p>No hay estudiantes registrados en este curso.</p>");
            }
        %>
    </div>

    <footer>
        <p>&copy; 2025 Proyecto Gestion Suarez. Todos los derechos reservados.</p>
    </footer>

</body>
</html>
