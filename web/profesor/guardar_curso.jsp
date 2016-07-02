<%-- 
    Document   : nuevo_curso
    Created on : 22/06/2014, 06:32:13 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DataWeb.Course" %>
<%@page import="DataWeb.User" %>
<%
User usuario = (User)session.getAttribute("user");
if( usuario == null ) {
    out.println("Error");
    return;
}
ServletContext sc = getServletContext();
String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");

if( request.getParameter("id") == null ) {
    Course curso = new Course(
            XMLReader.ReadCoursesXML.getNextID(xmlCourses),
            usuario.getID(),
            request.getParameter("nombre"));
    
    XMLWriter.WriteCoursesXML.addCourse(curso, xmlCourses);
    session.removeAttribute("curso_id");
    out.println("OK");
    return;
}
else {
    
}

out.println("Error");
%>
