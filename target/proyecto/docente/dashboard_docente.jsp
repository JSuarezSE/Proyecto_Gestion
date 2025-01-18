<%@ page import="modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Docente</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .dashboard-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }

        h1 {
            text-align: center;
            color: #4f46e5;
            margin-bottom: 20px;
        }

        p {
            font-size: 18px;
            margin: 10px 0;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #4f46e5;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            margin-bottom: 10px;
        }

        button:hover {
            background-color: #3730a3;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <div class="dashboard-container">
        <%
            // Verificar si el usuario está en sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                response.sendRedirect("index.jsp"); // Redirigir al login si no hay usuario
            }
        %>

        <h1>Buenas, <%= usuario.getNombre() + " " + usuario.getApellido() %></h1>
        <p>Email: <%= usuario.getEmail() %></p>
        <p>Rol ID: <%= usuario.getIdRole() %></p>

        <form method="post" action="asistencia.jsp">
            <button type="submit">Asistencia de Estudiantes</button>
        </form>

        <form method="post" action="calificar.jsp">
            <button type="submit">Calificar Estudiantes</button>
        </form>

        <form method="post" action="logout.jsp">
            <button type="submit">Cerrar Sesion</button>
        </form>
    </div>

</body>
</html>
