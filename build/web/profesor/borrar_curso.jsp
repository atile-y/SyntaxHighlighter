<%-- 
    Document   : borrar_curso
    Created on : 22/06/2014, 07:56:33 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Course" %>
<%@page import="DataWeb.User" %>
<%
User usuario = (User)session.getAttribute("user");
Course curso = (Course)session.getAttribute("curso");
List<User> usuarios = (List<User>)session.getAttribute("usuarios");
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
if( usuario == null || curso == null || usuarios == null || codigos == null ) {
    out.println("Error");
    return;
}
ServletContext sc = getServletContext();
String xmlCodes = sc.getRealPath("/WEB-INF/data/codes.xml");
String xmlComments = sc.getRealPath("/WEB-INF/data/comments.xml");
String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");
String xmlUsers = sc.getRealPath("/WEB-INF/data/users.xml");

boolean error = false;
for(User u : usuarios)
    error = !XMLWriter.WriteUsersXML.removeCourse(u.getID(), curso.getID(), xmlUsers);
for(Code c : codigos) {
    error = !XMLWriter.WriteCodesXML.deleteCode(c, xmlCodes);
    error = !XMLWriter.WriteCommentsXML.removeComments(c.getIDComentarios(), xmlComments);
}
error = !XMLWriter.WriteCoursesXML.deleteCourse(curso.getID(), xmlCourses);

if( !error )
    out.println("OK");
else
    out.println("Error");
%>
