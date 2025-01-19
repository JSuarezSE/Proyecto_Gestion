<%@ page import="modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Docente</title>
    <style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f4f4f9; /* Fondo más claro */
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
        color: #004d99; /* Azul oscuro */
        margin-bottom: 20px;
        font-size: 2.5em; /* Tamaño más grande */
    }

    p {
        font-size: 1.2em; /* Tamaño de fuente más grande */
        margin: 10px 0;
        text-align: center; /* Centrar texto */
    }

    button {
        width: 100%;
        padding: 12px;
        background-color: #004d99; /* Azul oscuro */
        color: #fff;
        border: none;
        border-radius: 5px; /* Bordes más redondeados */
        font-size: 1em;
        cursor: pointer;
        margin-bottom: 10px;
    }

    button:hover {
        background-color: #003366; /* Azul más oscuro al pasar el mouse */
    }

    .footer {
        text-align: center;
        margin-top: 20px;
        font-size: 14px;
        color: #004d99; /* Azul oscuro */
    }

    /* Estilos adicionales para tablas (si las usas) */
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
        background-color: #004d99; /* Azul oscuro */
        color: white;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2; /* Fondo gris claro para filas pares */
    }

    td input[type="number"] {
        width: 80px;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
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
