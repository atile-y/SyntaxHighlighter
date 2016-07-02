<%-- 
    Document   : alum_cursos
    Created on : 10/06/2014, 04:50:48 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="DataWeb.Code" %>
<%@page import="DataWeb.Course" %>
<%@page import="DataWeb.User" %>
<%
List<Course> cursos = (List<Course>)session.getAttribute("cursos");
if( cursos == null ) {
    response.sendRedirect("../login.jsp");
    return;
}
ServletContext sc = getServletContext();
String xmlUsers = sc.getRealPath("/WEB-INF/data/users.xml");
String xmlCodes = sc.getRealPath("/WEB-INF/data/codes.xml");

int curso_id = Integer.parseInt(request.getParameter("id").substring(5));
session.setAttribute("curso_id", curso_id);
Course curso = cursos.get(curso_id);
session.setAttribute("curso", curso);

List<User> usuarios = XMLReader.ReadUsersXML.ReadUsers(curso, xmlUsers);
session.setAttribute("usuarios", usuarios);
List<Code> codigos = XMLReader.ReadCodesXML.ReadCodes(curso, xmlCodes);
session.setAttribute("codigos", codigos);
%>
<p id="titulo-curso"><%= curso.getNombre() %></p>
<p id="codigos-titulo">C &Oacute; D I G O S</p>
<div id="codigos-curso">
    <% for(int i=0;i<codigos.size();i++) { %>
    <div <%= "id=\"codigo"+i+"\"" %>>
        <p class="nombre-codigo codigo-unselected"><%= codigos.get(i).getNombre() %></p>
        <div class="container-codigo">
            <label><%= codigos.get(i).getResaltar() %></label>
            <div <%= "class=\"codigo "+codigos.get(i).getLenguaje()+"\"" %>></div>
            <p class="ver-comentarios">Mostrar Comentarios</p>
            <div style="clear: both;"></div>
            <div class="comentarios-codigo"></div>
            <div class="agregar-comentarios">
                <p class="text">Nuevo Comentario</p>
                <label class="error">Ingresa un comentario</label>
                <textarea class="textarea-comentario" rows="5" cols="50" name="comentario"></textarea>
                <p class="agregar-comentario">Agregar</p>
                <div style="clear: both;"></div>
            </div>
        </div>
    </div>
    <% } %>
</div>
