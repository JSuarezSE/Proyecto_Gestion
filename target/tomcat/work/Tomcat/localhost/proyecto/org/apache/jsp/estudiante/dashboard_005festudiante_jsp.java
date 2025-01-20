/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2025-01-20 00:13:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.estudiante;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import modelo.Usuario;

public final class dashboard_005festudiante_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Dashboard Docente</title>\r\n");
      out.write("    <style>\r\n");
      out.write("        body {\r\n");
      out.write("            font-family: 'Arial', sans-serif;\r\n");
      out.write("            background-color: #f4f7fc;\r\n");
      out.write("            margin: 0;\r\n");
      out.write("            padding: 0;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            height: 100vh;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        .dashboard-container {\r\n");
      out.write("            background-color: #fff;\r\n");
      out.write("            padding: 40px;\r\n");
      out.write("            border-radius: 8px;\r\n");
      out.write("            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            max-width: 500px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        h1 {\r\n");
      out.write("            text-align: center;\r\n");
      out.write("            color: #4f46e5;\r\n");
      out.write("            margin-bottom: 20px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        p {\r\n");
      out.write("            font-size: 18px;\r\n");
      out.write("            margin: 10px 0;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        button {\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            padding: 12px;\r\n");
      out.write("            background-color: #4f46e5;\r\n");
      out.write("            color: #fff;\r\n");
      out.write("            border: none;\r\n");
      out.write("            border-radius: 4px;\r\n");
      out.write("            font-size: 16px;\r\n");
      out.write("            cursor: pointer;\r\n");
      out.write("            margin-bottom: 10px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        button:hover {\r\n");
      out.write("            background-color: #3730a3;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        .footer {\r\n");
      out.write("            text-align: center;\r\n");
      out.write("            margin-top: 20px;\r\n");
      out.write("            font-size: 14px;\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("    <div class=\"dashboard-container\">\r\n");
      out.write("        ");

            // Verificar si el usuario estÃ¡ en sesiÃ³n
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                response.sendRedirect("index.jsp"); // Redirigir al login si no hay usuario
            }
        
      out.write("\r\n");
      out.write("\r\n");
      out.write("        <h1>Buenas, ");
      out.print( usuario.getNombre() + " " + usuario.getApellido() );
      out.write("</h1>\r\n");
      out.write("        <p>Email: ");
      out.print( usuario.getEmail() );
      out.write("</p>\r\n");
      out.write("        <p>Rol ID: ");
      out.print( usuario.getIdRole() );
      out.write("</p>\r\n");
      out.write("\r\n");
      out.write("        <form method=\"post\" action=\"asistencia.jsp\">\r\n");
      out.write("            <button type=\"submit\">Revisar Asistencia</button>\r\n");
      out.write("        </form>\r\n");
      out.write("\r\n");
      out.write("        <form method=\"post\" action=\"calificar.jsp\">\r\n");
      out.write("            <button type=\"submit\">Revisar Calificaciones</button>\r\n");
      out.write("        </form>\r\n");
      out.write("\r\n");
      out.write("        <form method=\"post\" action=\"logout.jsp\">\r\n");
      out.write("            <button type=\"submit\">Cerrar Sesion</button>\r\n");
      out.write("        </form>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
