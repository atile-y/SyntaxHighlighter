<%-- 
    Document   : act_alumno
    Created on : 21/06/2014, 07:07:54 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.User" %>
<%@page import="DataWeb.Course" %>
<%
List<User> usuarios = (List<User>)session.getAttribute("usuarios");
Course curso = (Course)session.getAttribute("curso");
if( usuarios == null || curso == null ) {
    out.println("Error");
    return;
}
ServletContext sc = getServletContext();
String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");
String xmlUsers = sc.getRealPath("/WEB-INF/data/users.xml");

if( request.getParameter("id") == null ) {
    String nombre = request.getParameter("nombres");
    String apPat = request.getParameter("apPat");
    String apMat = request.getParameter("apMat");
    for(User u : usuarios)
        if( u.getApPaterno().toLowerCase().equals(apPat.toLowerCase())
         && u.getApMaterno().toLowerCase().equals(apMat.toLowerCase())
         && u.getNombres().toLowerCase().equals(nombre.toLowerCase()) ) {
            out.println("OK");
            return;
        }
    String user = nombre.toLowerCase().substring(0, 1) +
                  apPat.toLowerCase() + 
                  apMat.toLowerCase().substring(0, 1);
    User usuario = new User(
            XMLReader.ReadUsersXML.getNextID(xmlUsers),
            user,
            "123456",
            nombre,
            apPat,
            apMat,
            "alumno",
            curso.getID());
    User us = XMLReader.ReadUsersXML.findUserByFullName(usuario, xmlUsers);
    if( us != null ) {
        boolean error = false;
        error = (error || !XMLWriter.WriteCoursesXML.addUser(curso.getID(), us.getID(), xmlCourses));
        error = (error || !XMLWriter.WriteUsersXML.addCourse(us.getID(), curso.getID(), xmlUsers));
        if( error )
            out.println("Error");
        else
            out.println("OK");
        return;
    }
    else {
        boolean error = false;
        error = (error || !XMLWriter.WriteCoursesXML.addUser(curso.getID(), usuario.getID(), xmlCourses));
        error = (error || !XMLWriter.WriteUsersXML.addUser(usuario, xmlUsers));
        if( error )
            out.println("Error");
        else {
            out.println("Usuario creado:");
            out.println("  * User: "+usuario.getUsuario());
            out.println("  * Pass: "+usuario.getPassword());
        }
        return;
    }
}
else {
    int usuario_id = Integer.parseInt(request.getParameter("id"));
    User usuario = usuarios.get(usuario_id);
    usuario.setNombres(request.getParameter("nombres"));
    usuario.setApPaterno(request.getParameter("apPat"));
    usuario.setApMaterno(request.getParameter("apMat"));

    if( XMLWriter.WriteUsersXML.changeName(usuario, xmlUsers) ) {
        out.println("OK");
        return;
    }
}

out.println("Error");
%>
