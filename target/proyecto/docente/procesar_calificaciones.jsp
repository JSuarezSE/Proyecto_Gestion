<%@ page import="controlador.Conexion" %>
<%@ page import="controlador.DocenteDAO" %>
<%@ page import="modelo.Notas" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Enumeration" %>

<%
    // Verificar si el usuario está en sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("../index.jsp"); // Redirigir al login si no hay usuario
        return;
    }

    // Inicializar conexión a la base de datos
    Conexion conexionDB = new Conexion();
    Connection conexion = conexionDB.conectar();

    if (conexion == null) {
        out.println("<p>Error al conectar con la base de datos.</p>");
        return;
    }

    // Crear una instancia de DocenteDAO
    DocenteDAO docenteDAO = new DocenteDAO(conexion);

    // Obtener los parámetros del formulario
    Enumeration<String> parametros = request.getParameterNames();

    while (parametros.hasMoreElements()) {
        String nombreParametro = parametros.nextElement();

        // Verificar si el parámetro es un insumo (insumo1, insumo2, insumo3)
        if (nombreParametro.startsWith("insumo")) {
            // Extraer el id del usuario del nombre del parámetro
            int idUsuario = Integer.parseInt(nombreParametro.split("_")[1]);

            // Obtener los valores de los insumos
            double insumo1 = Double.parseDouble(request.getParameter("insumo1_" + idUsuario));
            double insumo2 = Double.parseDouble(request.getParameter("insumo2_" + idUsuario));
            double insumo3 = Double.parseDouble(request.getParameter("insumo3_" + idUsuario));

            // Verificar si la nota ya existe (para decidir si insertar o actualizar)
            boolean existeNota = docenteDAO.existeNota(idUsuario, 1); // Método que debes implementar en DocenteDAO

            if (existeNota) {
                // Obtener el id_nota de la nota existente
                int idNota = docenteDAO.obtenerIdNota(idUsuario, 1); // Método que debes implementar en DocenteDAO

                // Crear un objeto Notas con el id_nota
                Notas nota = new Notas();
                nota.setIdNota(idNota); // Asignar el id_nota
                nota.setIdUsuario(idUsuario);
                nota.setIdCurso(1); // Aquí debes poner el id del curso adecuado
                nota.setInsumo1(insumo1);
                nota.setInsumo2(insumo2);
                nota.setInsumo3(insumo3);

                // Actualizar la nota existente
                docenteDAO.actualizarNota(nota);
            } else {
                // Crear un objeto Notas sin id_nota (para inserción)
                Notas nota = new Notas();
                nota.setIdUsuario(idUsuario);
                nota.setIdCurso(1); // Aquí debes poner el id del curso adecuado
                nota.setInsumo1(insumo1);
                nota.setInsumo2(insumo2);
                nota.setInsumo3(insumo3);

                // Insertar una nueva nota
                docenteDAO.agregarNota(nota);
            }
        }
    }

    // Redirigir a una página de confirmación o de vuelta al formulario
    response.sendRedirect("calificar.jsp?mensaje=Calificaciones guardadas correctamente");
%>