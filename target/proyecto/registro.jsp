<%@ page import="controlador.UsuarioDAO" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="controlador.Conexion" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Usuario</title>
</head>
<body>
    <h1>Registrar Usuario</h1>
    <form method="post">
        <label>Nombre:</label><input type="text" name="nombre" required><br>
        <label>Apellido:</label><input type="text" name="apellido" required><br>
        <label>Email:</label><input type="email" name="email" required><br>
        <label>Cédula:</label><input type="text" name="cedula" required><br>
        <label>Teléfono:</label><input type="text" name="telefono" required><br>

        <label>Rol:</label><br>
        <input type="radio" name="rol" value="1" required> Estudiante
        <input type="radio" name="rol" value="2" required> Docente<br><br>

        <button type="submit" name="accion" value="registrar">Registrar</button>
    </form>

    <%
        // Inicializar conexión a la base de datos
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectar();

        if (conexion != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);

            // Procesar el formulario
            String accion = request.getParameter("accion");

            if ("registrar".equals(accion)) {
                // Capturar datos del formulario
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String email = request.getParameter("email");
                String cedula = request.getParameter("cedula");
                String telefono = request.getParameter("telefono");
                int rol = Integer.parseInt(request.getParameter("rol"));

                // Crear usuario y registrar
                Usuario nuevoUsuario = new Usuario(nombre, apellido, email, cedula, telefono, rol);
                boolean registroExitoso = usuarioDAO.registrarUsuario(nuevoUsuario);

                if (registroExitoso) {
                    out.println("<p style='color: green;'>Usuario registrado con éxito: " + nuevoUsuario.getNombre() + "</p>");
                } else {
                    out.println("<p style='color: red;'>Error al registrar el usuario.</p>");
                }
            }
        } else {
            out.println("<p style='color: red;'>No se pudo conectar a la base de datos.</p>");
        }
    %>
</body>
</html>
