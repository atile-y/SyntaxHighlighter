<%-- 
    Document   : calificar_comentario
    Created on : 18/06/2014, 05:21:37 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
if( codigos == null ) {
    response.sendRedirect("../login.jsp");
    return;
}
ServletContext sc = getServletContext();
String xmlComment = sc.getRealPath("/WEB-INF/data/comments.xml");

int codigo_id = Integer.parseInt(request.getParameter("codigo_id"));
int comentario_id = Integer.parseInt(request.getParameter("comentario_id"));
int calificacion = Integer.parseInt(request.getParameter("rating"));

String com_id = XMLReader.ReadCommentsXML.ReadComments(codigos.get(codigo_id), xmlComment).get(comentario_id).getID();

if( XMLWriter.WriteCommentsXML.rateComment(com_id, calificacion, xmlComment) )
    out.println("OK");
else
    out.println("Error");
%>
