<%@ page import="controlador.UsuarioDAO" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="controlador.Conexion" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Usuario</title>
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

        .form-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px; /* Limitar el ancho máximo del formulario */
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

        input{
            width: 100%;
            max-width: auto; 
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        input[type="radio"] {
            width: auto;
            margin-right: 10px;
        }

        button {
            width: 100%;
            max-width:auto; 
            padding: 12px;
            background-color: #4f46e5;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
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

        .back-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #ddd;
            color: #333;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            text-align: center;
        }

        .back-button:hover {
            background-color: #bbb;
        }
    </style>  
</head>
<body>
    <div class="form-container">
        <h1>Registrar Usuario</h1>

        <form method="post">
            <label>Nombre:</label><input type="text" name="nombre" required><br>
            <label>Apellido:</label><input type="text" name="apellido" required><br>
            <label>Email:</label><input type="email" name="email" required><br>
            <label>Cedula:</label><input type="text" name="cedula" required><br>
            <label>Telefono:</label><input type="text" name="telefono" required><br>

            <label>Rol:</label><br>
            <input type="radio" name="rol" value="1" required> Estudiante
            <input type="radio" name="rol" value="2" required> Docente<br><br>

            <button type="submit" name="accion" value="registrar">Registrar</button>
        </form>
        <br>
        <!-- Botón para regresar a la página de inicio -->
        <a href="index.jsp">
            <button type="button">Regresar al Inicio</button>
        </a>

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

                    // Crear usuario usando el constructor vacío y setters
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setNombre(nombre);
                    nuevoUsuario.setApellido(apellido);
                    nuevoUsuario.setEmail(email);
                    nuevoUsuario.setCedula(cedula);
                    nuevoUsuario.setTelefono(telefono);
                    nuevoUsuario.setIdRole(rol);

                    // Registrar el usuario
                    boolean registroExitoso = usuarioDAO.registrarUsuario(nuevoUsuario);

                    if (registroExitoso) {
                        out.println("<p style='color: green;'>Usuario registrado con exito: " + nuevoUsuario.getNombre() + "</p>");
                    } else {
                        out.println("<p style='color: red;'>Error al registrar el usuario.</p>");
                    }
                }
            } else {
                out.println("<p style='color: red;'>No se pudo conectar a la base de datos.</p>");
            }
        %>
    </div>
</body>
</html>
