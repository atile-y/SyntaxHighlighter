<%-- 
    Document   : main
    Created on : 5/06/2014, 02:52:09 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="DataWeb.Course"%>
<%@page import="DataWeb.User" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta name="author" content="Alejandro Alberto Yescas Benitez">
        <meta name="keywords" content="Syntax Highlighter, Code">
        
        <link rel="stylesheet" type="text/css" href="../css/reset.css">
        <link rel="stylesheet" type="text/css" href="../css/base.css">
        <link rel="stylesheet" type="text/css" href="../css/profesor.css">
        <link rel="stylesheet" type="text/css" href="../css/prof_cursos.css">
        <link rel="stylesheet" type="text/css" href="../css/prof_comentarios.css">

        <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="../js/profesor.js"></script>
        <script type="text/javascript" src="../js/prof_cursos.js"></script>
        <script type="text/javascript" src="../js/prof_comentarios.js"></script>

        <!-- Resaltador de sintaxis =D -->
        <script type="text/javascript" src="../js/sh/shCore.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushBash.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushCSharp.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushCpp.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushCss.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushJScript.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushJava.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushPhp.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushPython.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushSql.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushVb.js"></script>
        <script type="text/javascript" src="../js/sh/shBrushXml.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/sh/shCore.css">
        <link rel="stylesheet" type="text/css" href="../css/sh/shThemeDefault.css">
        <!---->
        
        <title>M&oacute;dulo Profesor</title>
        
        <%
        Integer curso_id = (Integer)session.getAttribute("curso_id");
        if( curso_id != null ) { %>
        <script type="text/javascript">
            $(document).ready(function() {
                $("<%= "#curso"+curso_id %>").trigger("click");
            });
        </script>
        <% } %>
    </head>
    <body>
        <%
        User user = (User)session.getAttribute("user");
        if( user == null ) {
            response.sendRedirect("../login.jsp");
            return;
        }
        ServletContext sc = getServletContext();
        String xmlCourses = sc.getRealPath("/WEB-INF/data/courses.xml");
        
        List<Course> cursos = XMLReader.ReadCoursesXML.ReadCourses(user.getID(), xmlCourses);
        session.setAttribute("cursos", cursos);
        %>
        <div id="header">
            <div>
                <div id="logout">
                    <img src="../img/logout.png" alt="logout">
                </div>
                <div id="name">
                    <p>Bienvenido <%= user.getNombreCompleto() %></p>
                </div>
            </div>
        </div>

        <div id="container">
            <div id="menu">
                <div id="menu-cursos">
                    <p>C U R S O S</p>
                </div>
                <% if( cursos.isEmpty() ) { %>
                <div id="no-cursos">
                    <p>No ha agregado ning&uacute;n curso.</p>
                </div>
                <% } else { %>
                <div id="cursos">
                    <% for( int i=0;i<cursos.size();i++ ) {
                        String txt = "<p id=\"curso" + i + "\">";
                        txt += (cursos.get(i).getNombre() + "</p>");
                        out.println(txt);
                    } %>
                </div>
                <% } %>
                <ul id="acciones">
                    <li id="nuevo-curso">Nuevo</li>
                    <li id="borrar-curso">Borrar</li>
                </ul>
                <div id="agregar-curso">
                    <input id="input-curso" size="26">
                    <label class="error">Ingrese un nombre</label>
                    <p id="aceptar-curso">Aceptar</p>
                    <p id="cancelar-curso">Cancelar</p>
                </div>
            </div>
            <div id="content"></div>
        </div>
    </body>
</html>
