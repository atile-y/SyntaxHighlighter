<%-- 
    Document   : guardar_codigo
    Created on : 17/06/2014, 06:10:18 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/jsp" pageEncoding="UTF-8"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Course" %>
<%@page import="DataWeb.User" %>

<%
Course curso = (Course)session.getAttribute("curso");
if( curso == null ) {
    response.sendRedirect("../login.jsp");
    return;
}
ServletContext sc = getServletContext();
String xmlCodes = sc.getRealPath("/WEB-INF/data/codes.xml");
String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");

Code codigo = (Code)session.getAttribute("codigo");
if( codigo != null ) {
    codigo.setNombre(request.getParameter("titulo"));
    codigo.setLenguaje(request.getParameter("lang"));
    codigo.setResaltar(request.getParameter("resaltar"));
    codigo.setLineas(Arrays.asList(request.getParameter("code").split("\n")));
    XMLWriter.WriteCodesXML.writeCode(codigo, xmlCodes);
    out.println("OK");
    session.removeAttribute("codigo");
}
else {
    codigo = new Code(
            XMLReader.ReadCodesXML.getNextID(xmlCodes),
            ((User)session.getAttribute("user")).getID(),
            request.getParameter("titulo"),
            request.getParameter("lang"),
            request.getParameter("resaltar"),
            Arrays.asList(request.getParameter("code").split("\n")));

    boolean error = !XMLWriter.WriteCoursesXML.addCode(curso.getID(), codigo.getID(), xmlCourses);
    XMLWriter.WriteCodesXML.writeCode(codigo, xmlCodes);
    if( !error )
        out.println("OK");
    else
        out.println("Error");
}
%>
