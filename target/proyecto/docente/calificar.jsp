<%@ page import="controlador.Conexion" %>
<%@ page import="controlador.DocenteDAO" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="modelo.Notas" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calificar Estudiantes - Docente</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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
        td input[type="number"] {
            width: 80px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
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
        <h1>Calificar Estudiantes - Docente</h1>
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

        <h2>Calificar Estudiantes</h2>

        <%
            // Inicializar conexión a la base de datos
            Conexion conexionDB = new Conexion();
            Connection conexion = conexionDB.conectar();
            List<Usuario> estudiantes = null;
            List<Notas> notasEstudiantes = null;

            if (conexion != null) {
                DocenteDAO docenteDAO = new DocenteDAO(conexion);
                int idCurso = 1; // Aquí debes poner el id del curso adecuado
                estudiantes = docenteDAO.obtenerEstudiantesPorCurso(idCurso);
                notasEstudiantes = docenteDAO.listarNotasPorCurso(idCurso); // Obtener las notas de los estudiantes
            } else {
                out.println("<p>Error al conectar con la base de datos.</p>");
            }

            if (estudiantes != null && !estudiantes.isEmpty()) {
        %>

        <form method="post" action="procesar_calificaciones.jsp">
            <table>
                <thead>
                    <tr>
                        <th>Nombre del Estudiante</th>
                        <th>Insumo 1</th>
                        <th>Insumo 2</th>
                        <th>Insumo 3</th>
                        <th>Total</th> <!-- Nueva columna para el total -->
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Mostrar los estudiantes en la tabla
                        for (Usuario estudiante : estudiantes) {
                            Notas notaEstudiante = null;
                            if (notasEstudiantes != null) {
                                // Buscar la nota del estudiante actual
                                for (Notas nota : notasEstudiantes) {
                                    if (nota.getIdUsuario() == estudiante.getIdUsuario()) {
                                        notaEstudiante = nota;
                                        break;
                                    }
                                }
                            }
                    %>
                    <tr>
                        <td><%= estudiante.getNombre() + " " + estudiante.getApellido() %></td>
                        <td>
                            <input type="number" name="insumo1_<%= estudiante.getIdUsuario() %>" 
                                   step="0.01" min="0" max="100" 
                                   value="<%= (notaEstudiante != null) ? notaEstudiante.getInsumo1() : 0 %>" required>
                        </td>
                        <td>
                            <input type="number" name="insumo2_<%= estudiante.getIdUsuario() %>" 
                                   step="0.01" min="0" max="100" 
                                   value="<%= (notaEstudiante != null) ? notaEstudiante.getInsumo2() : 0 %>" required>
                        </td>
                        <td>
                            <input type="number" name="insumo3_<%= estudiante.getIdUsuario() %>" 
                                   step="0.01" min="0" max="100" 
                                   value="<%= (notaEstudiante != null) ? notaEstudiante.getInsumo3() : 0 %>" required>
                        </td>
                        <td>
                            <%= (notaEstudiante != null) ? String.format("%.2f", notaEstudiante.getTotal()) : 0.00 %>
                        </td>
                        <input type="hidden" name="estudiante_<%= estudiante.getIdUsuario() %>" value="<%= estudiante.getIdUsuario() %>">
                    </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>

            <div style="text-align: center; margin-top: 20px;">
                <button type="submit" class="btn" name="accion" value="registrarCalificaciones">Registrar Calificaciones</button>
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