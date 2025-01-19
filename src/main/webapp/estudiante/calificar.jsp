<%@ page import="controlador.Conexion" %>
<%@ page import="controlador.EstudianteDAO" %>
<%@ page import="modelo.Notas" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Calificaciones</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #4f46e5;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4f46e5;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .btn-back {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px;
            background-color: #4f46e5;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
        }

        .btn-back:hover {
            background-color: #3730a3;
        }
    </style>
</head>
<body>

    <div class="container">
        <%
            // Verificar si el usuario está en sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                response.sendRedirect("../index.jsp"); // Redirigir al login si no hay usuario
            }

            // Obtener las notas del estudiante
            Conexion conexionDB = new Conexion();
            Connection conexion = conexionDB.conectar();
            List<Notas> notas = new ArrayList<Notas>();

            if (conexion != null) {
                EstudianteDAO estudianteDAO = new EstudianteDAO(conexion);
                notas = estudianteDAO.obtenerNotasPorEstudiante(usuario.getIdUsuario());
            }
        %>

        <h1>Mis Calificaciones</h1>

        <table>
            <thead>
                <tr>
                    <th>Curso</th>
                    <th>Insumo 1</th>
                    <th>Insumo 2</th>
                    <th>Insumo 3</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (notas.isEmpty()) {
                        out.println("<tr><td colspan='5'>No hay calificaciones registradas.</td></tr>");
                    } else {
                        for (Notas nota : notas) {
                %>
                <tr>
                    <td><%= nota.getIdCurso() %></td>
                    <td><%= nota.getInsumo1() %></td>
                    <td><%= nota.getInsumo2() %></td>
                    <td><%= nota.getInsumo3() %></td>
                    <td><%= nota.getTotal() %></td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>

        <a href="dashboard_estudiante.jsp" class="btn-back">Volver al Dashboard</a>
    </div>

</body>
</html>