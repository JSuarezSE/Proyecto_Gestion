<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="controlador.Conexion" %>
<html>
<head>
    <title>Verificación de conexión</title>
</head>
<body>
    <h2>Verificación de conexión a la base de datos</h2>
    <%
        Connection conexion = null;
        try {
            conexion = Conexion.conectar();
            out.println("<p style='color: green;'>Conexión exitosa a la base de datos.</p>");
        } catch (SQLException e) {
            out.println("<p style='color: red;'>Error al conectar a la base de datos: " + e.getMessage() + "</p>");
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                    out.println("<p>Conexión cerrada correctamente.</p>");
                } catch (SQLException e) {
                    out.println("<p style='color: orange;'>Error al cerrar la conexión: " + e.getMessage() + "</p>");
                }
            }
        }
    %>
</body>
</html>
