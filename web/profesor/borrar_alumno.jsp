<%-- 
    Document   : borrar_alumno
    Created on : 22/06/2014, 01:08:46 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Course" %>
<%@page import="DataWeb.User" %>
<%
List<User> usuarios = (List<User>)session.getAttribute("usuarios");
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
Course curso = (Course)session.getAttribute("curso");
if( usuarios == null || curso == null || codigos == null ) {
    response.sendRedirect("../login.jsp");
    return;
}
ServletContext sc = getServletContext();
String xmlUsers = sc.getRealPath("/WEB-INF/data/users.xml");
String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");
String xmlComments = sc.getRealPath("/WEB-INF/data/comments.xml");


int usuario_id = Integer.parseInt(request.getParameter("id"));
User usuario = usuarios.get(usuario_id);

List<String> comentarios_ids = new ArrayList<String>();
for(Code c : codigos)
    comentarios_ids.addAll(c.getIDComentarios());

boolean error = false;
error = (error || !XMLWriter.WriteCoursesXML.removeUser(curso.getID(), usuario.getID(), xmlCourses));
error = (error || !XMLWriter.WriteUsersXML.removeCourse(usuario.getID(), curso.getID(), xmlUsers));
error = (error || !XMLWriter.WriteCommentsXML.removeComments(usuario.getID(), comentarios_ids, xmlComments));

if( !error ) {
    out.println("OK");
    return;
}

out.println("Error");
%>
