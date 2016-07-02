<%-- 
    Document   : prof_cursos
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
<form id="form-mod" style="display: hidden" action="../editor/" method="POST">
    <input type="hidden" id="modificar" name="modificar" value="true">
    <input type="hidden" id="codigo-id" name="codigo-id" value="">
</form>

<p id="titulo-curso"><%= curso.getNombre() %></p>
<p id="alumnos-titulo">A L U M N O S</p>
<div id="alumnos-curso">
    <% for(int i=0;i<usuarios.size();i++) { %>
    <div <%= "id=\"alumno"+i+"\"" %>>
        <div class="nomod-alumno">
            <p class="apPat-alumno"><%= usuarios.get(i).getApPaterno() %></p>
            <p class="apMat-alumno"><%= usuarios.get(i).getApMaterno() %></p>
            <p class="nombre-alumno"><%= usuarios.get(i).getNombres() %></p>
            <p class="borrar-alumno">Borrar</p>
            <p class="modificar-alumno">Modificar</p>
            <div style="clear: both;"></div>
        </div>
        <div class="mod-alumno">
            <input class="apPat" type="text" size="9">
            <input class="apMat" type="text" size="9">
            <input class="nombre" type="text" size="14">
            <p class="cancelar-alumno">Cancelar</p>
            <p class="aceptar-alumno">Aceptar</p>
            <div style="clear: both;"></div>
            <label class="apPat-label">Apellido Paterno</label>
            <label class="apMat-label">Apellido Materno</label>
            <label class="nombre-label">Nombre(s)</label>
        </div>
    </div>
    <% } %>
</div>
<div id="acciones-alumno">
    <input id="nuevo-apPat" type="text" size="9">
    <input id="nuevo-apMat" type="text" size="9">
    <input id="nuevo-nombre" type="text" size="14">
    <p id="agregar-alumno">Agregar Alumno</p>
    <div style="clear: both;"></div>
    <label id="nuevo-apPat-label">Apellido Paterno</label>
    <label id="nuevo-apMat-label">Apellido Materno</label>
    <label id="nuevo-nombre-label">Nombre(s)</label>
</div>
<p id="codigos-titulo">C &Oacute; D I G O S</p>
<div id="codigos-curso">
    <% for(int i=0;i<codigos.size();i++) { %>
    <div <%= "id=\"codigo"+i+"\"" %>>
        <p class="nombre-codigo codigo-unselected"><%= codigos.get(i).getNombre() %></p>
        <div class="container-codigo">
            <label><%= codigos.get(i).getResaltar() %></label>
            <div <%= "class=\"codigo "+codigos.get(i).getLenguaje()+"\"" %>></div>
            <p class="borrar-codigo">Borrar</p>
            <p class="modificar-codigo">Modificar</p>
            <p class="ver-comentarios">Mostrar Comentarios</p>
            <div style="clear: both;"></div>
            <div class="comentarios-codigo"></div>
        </div>
    </div>
    <% } %>
</div>
<div id="codigos-acciones">
    <a href="../editor/">Agregar C&oacute;digo</a>
    <div style="clear: both;"></div>
</div>
