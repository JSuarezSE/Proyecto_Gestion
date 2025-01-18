/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2025-01-18 16:22:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.Connection;
import controlador.Conexion;
import controlador.UsuarioDAO;
import modelo.Usuario;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html lang=\"es\">\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <title>Inicio de Sesion</title>\n");
      out.write("    <style>\n");
      out.write("        body {\n");
      out.write("            font-family: 'Arial', sans-serif;\n");
      out.write("            background-color: #f4f7fc;\n");
      out.write("            margin: 0;\n");
      out.write("            padding: 0;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .container {\n");
      out.write("            display: flex;\n");
      out.write("            justify-content: center;\n");
      out.write("            align-items: center;\n");
      out.write("            height: 100vh;\n");
      out.write("            background-color: #e0e7ff;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .login-form {\n");
      out.write("            background-color: #fff;\n");
      out.write("            padding: 40px;\n");
      out.write("            border-radius: 8px;\n");
      out.write("            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n");
      out.write("            width: 100%;\n");
      out.write("            max-width: 400px;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        h1 {\n");
      out.write("            text-align: center;\n");
      out.write("            color: #4f46e5;\n");
      out.write("            margin-bottom: 20px;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        label {\n");
      out.write("            font-weight: bold;\n");
      out.write("            color: #333;\n");
      out.write("            margin-bottom: 8px;\n");
      out.write("            display: block;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        input[type=\"email\"], input[type=\"text\"] {\n");
      out.write("            width: 100%;\n");
      out.write("            padding: 10px;\n");
      out.write("            margin-bottom: 20px;\n");
      out.write("            border: 1px solid #ddd;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            font-size: 16px;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        button[type=\"submit\"] {\n");
      out.write("            width: 100%;\n");
      out.write("            padding: 12px;\n");
      out.write("            background-color: #4f46e5;\n");
      out.write("            color: #fff;\n");
      out.write("            border: none;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            font-size: 16px;\n");
      out.write("            cursor: pointer;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        button[type=\"submit\"]:hover {\n");
      out.write("            background-color: #3730a3;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .footer {\n");
      out.write("            text-align: center;\n");
      out.write("            margin-top: 20px;\n");
      out.write("            font-size: 14px;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .footer a {\n");
      out.write("            color: #4f46e5;\n");
      out.write("            text-decoration: none;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .footer a:hover {\n");
      out.write("            text-decoration: underline;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("    </style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<div class=\"container\">\n");
      out.write("    <form class=\"login-form\" method=\"post\">\n");
      out.write("        <h1>Iniciar Sesion</h1>\n");
      out.write("        <label for=\"email\">Email:</label>\n");
      out.write("        <input type=\"email\" name=\"email\" id=\"email\" required placeholder=\"Introduce tu correo electronico\">\n");
      out.write("\n");
      out.write("        <label for=\"cedula\">Cedula:</label>\n");
      out.write("        <input type=\"text\" name=\"cedula\" id=\"cedula\" required placeholder=\"Introduce tu cedula\">\n");
      out.write("\n");
      out.write("        <button type=\"submit\" name=\"accion\" value=\"login\">Iniciar Sesion</button>\n");
      out.write("\n");
      out.write("        <div class=\"footer\">\n");
      out.write("            <p> Â¿No tienes cuenta? <a href=\"#\">Registrarse</a></p>\n");
      out.write("        </div>\n");
      out.write("    </form>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("    ");

        // Inicializar conexiÃ³n a la base de datos
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectar();

        if (conexion != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);

            // Procesar la acciÃ³n del formulario
            String accion = request.getParameter("accion");

            if ("login".equals(accion)) {
                // Capturar datos del formulario
                String email = request.getParameter("email");
                String cedula = request.getParameter("cedula");

                // Validar login
                Usuario usuario = usuarioDAO.validarLogin(email, cedula);

                if (usuario != null) {
                     // Guardar usuario en sesiÃ³n
                session.setAttribute("usuario", usuario);

                // Redirigir dependiendo del rol
                if (usuario.getIdRole() == 1) { // Rol de estudiante
                    response.sendRedirect("dashboard_estudiante.jsp");
                } else if (usuario.getIdRole() == 2) { // Rol de docente
                    response.sendRedirect("dashboard_docente.jsp");
                } else {
                    out.println("<p style='color: red;'>Rol no reconocido.</p>");
                }
            } else {
                out.println("<p style='color: red;'>Credenciales incorrectas. IntÃ©ntelo de nuevo.</p>");
            }
        }
        } else {
            out.println("<p style='color: red;'>No se pudo conectar a la base de datos.</p>");
        }
    
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
