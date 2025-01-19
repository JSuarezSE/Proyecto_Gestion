<%
    session.invalidate(); // Invalidar sesión
    response.sendRedirect("../index.jsp"); // Redirigir al login
%>
