<%-- 
    Document   : validate_login
    Created on : 7/06/2014, 09:49:08 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="DataWeb.User"%>
<%@page import="DataWeb.Course" %>

<%
ServletContext sc = getServletContext();
User user = XMLReader.ReadUsersXML.ReadUser(
        request.getParameter("user"),
        request.getParameter("pass"),
        sc.getRealPath("/WEB-INF/data/users.xml"));

if( user == null ) {
    response.sendRedirect("login.jsp");
    return;
}

session.invalidate();
session = request.getSession();

session.setAttribute("user", user);

if( user.getCategory().equals("profesor") ) {
    response.sendRedirect("profesor/");
    return;
}
else if( user.getCategory().equals("alumno") ) {
    response.sendRedirect("alumno/");
    return;
}

response.sendRedirect("login.jsp");
%>
