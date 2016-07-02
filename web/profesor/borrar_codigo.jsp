<%-- 
    Document   : borrar-codigo
    Created on : 19/06/2014, 12:45:30 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Course" %>
<%
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
Course curso = (Course)session.getAttribute("curso");
if( codigos == null || curso == null ) {
    response.sendRedirect("../login.jsp");
    return;
}
ServletContext sc = getServletContext();
String xmlCodes = sc.getRealPath("/WEB-INF/data/codes.xml");
String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");
String xmlComments = sc.getRealPath("/WEB-INF/data/comments.xml");

int codigo_id = Integer.parseInt(request.getParameter("codigo_id"));
Code codigo = codigos.get(codigo_id);

boolean error = false;
error = (error || !XMLWriter.WriteCoursesXML.removeCode(curso.getID(), codigo.getID(), xmlCourses));
error = (error || !XMLWriter.WriteCommentsXML.removeComments(codigo.getIDComentarios(), xmlComments));
error = (error || !XMLWriter.WriteCodesXML.deleteCode(codigo, xmlCodes));

if( !error ) {
    out.println("OK");
    return;
}

out.println("Error");
%>
