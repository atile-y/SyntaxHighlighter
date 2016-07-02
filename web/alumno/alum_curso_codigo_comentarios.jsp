<%-- 
    Document   : alum_curso_codigo_comentarios
    Created on : 14/06/2014, 05:10:02 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Comment" %>
<%@page import="DataWeb.Course" %>
<%@page import="DataWeb.User" %>
<%
Course curso = (Course)session.getAttribute("curso");
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
List<User> usuarios = (List<User>)session.getAttribute("usuarios");
if( curso == null || codigos == null || usuarios == null ) {
    response.sendRedirect("../login.jsp");
    return;
}
ServletContext sc = getServletContext();
String xmlComments = sc.getRealPath("/WEB-INF/data/comments.xml");

int codigo_id = Integer.parseInt(request.getParameter("id").substring(6));
Code codigo = codigos.get(codigo_id);

List<Comment> comentarios = XMLReader.ReadCommentsXML.ReadComments(codigo, xmlComments);

for(int i=0;i<comentarios.size();i++) {
%>
<div <%= "id=\"comentario"+i+"\"" %> class="comentario">
    <%
    String idUsuario = comentarios.get(i).getIDUsuario();
    String autor = "An&oacute;nimo";
    for(int j=0;j<usuarios.size();j++)
        if( usuarios.get(j).getID().equals(idUsuario) )
            autor = usuarios.get(j).getNombreCompleto();
    String fecha = comentarios.get(i).getFechaMod().replace('T', ' ');
    out.println("<p class=\"autor-comentario\">" + autor + "</p>");
    out.println("<p class=\"fecha-comentario\">" + fecha + "</p>");
    int calf = comentarios.get(i).getCalificacion();
    out.println("<div class=\"rate0\"></div><!--");
    for(int j=1;j<=calf;j++)
        out.println("--><img class=\"rate"+j+"\" alt=\"on\" src=\"../img/rat_on.png\"><!--");
    for(int j=1;j<=5-calf;j++)
        out.println("--><img class=\"rate"+(j+calf)+"\" alt=\"off\" src=\"../img/rat_off.png\"><!--");
    out.println("-->");

    String lineas[] = comentarios.get(i).getTexto().split("\n");
    for( int j=1 ; j<lineas.length-1 ; j++  ) {
        String par = lineas[j].length() > 12 ? lineas[j].substring(12) : "&nbsp;";
        out.println("<p>" + par + "</p>");
    } %>
</div>
<% }
if( comentarios.size() == 0 ) { %>
<p class="no-comentarios">Sin Comentarios</p>
<% } %>
