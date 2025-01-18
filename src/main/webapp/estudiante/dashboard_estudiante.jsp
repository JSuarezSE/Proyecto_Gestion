<%@ page import="modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Estudiante</title>
</head>
<body>
    <%
        // Verificar si el usuario está en sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("index.jsp"); // Redirigir al login si no hay usuario
        }
    %>

    <h1>Bienvenido, <%= usuario.getNombre() + " " + usuario.getApellido() %></h1>
    <p>Email: <%= usuario.getEmail() %></p>
    <p>Rol ID: <%= usuario.getIdRole() %></p>

    <form method="post" action="logout.jsp">
        <button type="submit">Cerrar Sesión</button>
    </form>
</body>
</html>
