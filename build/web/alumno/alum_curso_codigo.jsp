<%-- 
    Document   : alum_curso_codigo
    Created on : 13/06/2014, 02:34:10 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Course" %>
<%
Course curso = (Course)session.getAttribute("curso");
List<Code> codigos = (List<Code>)session.getAttribute("codigos");
if( curso == null || codigos == null ) {
    response.sendRedirect("../login.jsp");
    return;
}

int codigo_id = Integer.parseInt(request.getParameter("id").substring(6));
Code codigo = codigos.get(codigo_id);

for( String line : codigo.getLineas() )
    out.println(line);
%>
