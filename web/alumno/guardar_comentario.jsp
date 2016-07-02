<%-- 
    Document   : guardar_comentario
    Created on : 23/06/2014, 10:18:52 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Comment" %>
<%@page import="DataWeb.User" %>
<%
User usuario = (User)session.getAttribute("user");
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
if( usuario == null || codigos == null ) {
    out.println("Error");
    return;
}
ServletContext sc = getServletContext();
String xmlCodes = sc.getRealPath("/WEB-INF/data/codes.xml");
String xmlComments = sc.getRealPath("/WEB-INF/data/comments.xml");

int codigo_id = Integer.parseInt(request.getParameter("codigo_id"));
Code codigo = codigos.get(codigo_id);
Date fecha = new Date();
SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

Comment comentario = new Comment(
        XMLReader.ReadCommentsXML.getNextID(xmlComments),
        usuario.getID(),
        request.getParameter("text"),
        0,
        formatDate.format(fecha)+"T"+formatTime.format(fecha));

boolean error = false;
error = (error || !XMLWriter.WriteCodesXML.addComment(codigo.getID(), comentario.getID(), xmlCodes));
error = (error || !XMLWriter.WriteCommentsXML.addComment(comentario, xmlComments));
if( !error )
    out.println("OK");
else
    out.println("Error");
%>
