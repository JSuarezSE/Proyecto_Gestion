<%@ page import="java.sql.Connection" %>
<%@ page import="controlador.Conexion" %>
<%@ page import="controlador.UsuarioDAO" %>
<%@ page import="modelo.Usuario" %>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de Sesion</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #e0e7ff;
        }

        .login-form {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        h1 {
            text-align: center;
            color: #4f46e5;
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            color: #333;
            margin-bottom: 8px;
            display: block;
        }

        input[type="email"], input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #4f46e5;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: #3730a3;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
        }

        .footer a {
            color: #4f46e5;
            text-decoration: none;
        }

        .footer a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>

<div class="container">
    <form class="login-form" method="post">
        <h1>Iniciar Sesion</h1>
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required placeholder="Introduce tu correo electronico">

        <label for="cedula">Clave:</label>
        <input type="text" name="cedula" id="cedula" required placeholder="Introduce tu cedula">

        <button type="submit" name="accion" value="login">Iniciar Sesion</button>

        <div class="footer">
            <p> ¿No tienes cuenta? <a href="registro.jsp">Registrarse</a></p>
        </div>
    </form>
</div>

    <%
        // Inicializar conexión a la base de datos
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectar();

        if (conexion != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);

            // Procesar la acción del formulario
            String accion = request.getParameter("accion");

            if ("login".equals(accion)) {
                // Capturar datos del formulario
                String email = request.getParameter("email");
                String cedula = request.getParameter("cedula");

                // Validar login
                Usuario usuario = usuarioDAO.validarLogin(email, cedula);

                if (usuario != null) {
                     // Guardar usuario en sesión
                session.setAttribute("usuario", usuario);

                // Redirigir dependiendo del rol
                if (usuario.getIdRole() == 1) { // Rol de estudiante
                    response.sendRedirect("estudiante/dashboard_estudiante.jsp");
                } else if (usuario.getIdRole() == 2) { // Rol de docente
                    response.sendRedirect("docente/dashboard_docente.jsp");
                } else {
                    out.println("script>alert('Rol no reconocido.');</script>");
                }
            } else {
               out.println("<script>alert('Credenciales incorrectas. Inténtelo de nuevo.');</script>");
            }
        }
        } else {
            out.println("<script>alert('No se pudo conectar a la base de datos.');</script>");
        }
    %>
</body>
</html>
