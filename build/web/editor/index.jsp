<%-- 
    Document   : editor
    Created on : 15/06/2014, 06:58:30 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="DataWeb.Code"%>
<%@page import="DataWeb.User"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta name="author" content="Alejandro Alberto Yescas Benitez">
        <meta name="keywords" content="Syntax Highlighter, Code">
        
        <link rel="stylesheet" type="text/css" href="../css/reset.css">
        <link rel="stylesheet" type="text/css" href="../css/base.css">
        <link rel="stylesheet" type="text/css" href="../css/editor.css">

        <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="../js/editor.js"></script>

        <title>Editor</title>
    </head>
    <body>
        <%
        User user = (User)session.getAttribute("user");
        if( user == null ) {
            response.sendRedirect("../login.jsp");
            return;
        }
        ServletContext sc = getServletContext();
        String xmlLanguages = sc.getRealPath("/WEB-INF/data/schema/codesSchema.xsd");

        List<String> lenguajes = XMLReader.ReadXMLFile.ReadLanguages(xmlLanguages);

        String modif = request.getParameter("modificar");
        Code codigo = null;
        boolean mod = false;
        String id = "";
        if( modif != null ) {
            mod = true;
            List<Code> codigos = (List<Code>)session.getAttribute("codigos");
            if( codigos == null ) {
                response.sendRedirect("../login.jsp");
                return;
            }
            int codigo_id = Integer.parseInt(request.getParameter("codigo-id"));
            codigo = codigos.get(codigo_id);
            session.setAttribute("codigo", codigo);
        }
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
            <div id="titulo-codigo">
                <p>Nombre:</p>
                <% if( mod ) { %>
                <input id="titulo" type="text" name="titulo" size="70" value="<%= codigo.getNombre() %>">
                <% } else { %>
                <input id="titulo" type="text" name="titulo" size="70">
                <% } %>
                <label class="error">Ingrese un nombre</label>
            </div>
            <div id="lenguaje-codigo">
                <p>Lenguaje:</p>
                <select id="lenguaje" name="lenguaje">
                    <% for( String s : lenguajes ) {
                        if( mod && s.equals(codigo.getLenguaje()) ) { %>
                    <option value="<%= s %>" selected="selected"><%= s %></option>
                    <% } else { %>
                    <option value="<%= s %>"><%= s %></option>
                    <% } } %>
                </select>
            </div>
            <div id="code-codigo">
                <textarea id="codigo" rows="15" cols="80" name="codigo" wrap="off"><%
                    if( mod )
                        for( String l : codigo.getLineas() )
                            out.println(l);
                %></textarea>
                <div id="sugerencias"></div>
                <label class="error">Ingrese c&oacute;digo</label>
            </div>
            <div id="resaltar-codigo">
                <p>L&iacute;neas resaltadas:</p>
                <% if( mod ) { %>
                <input id="resaltar" type="text" name="resaltar" size="10" value="<%= codigo.getResaltar() %>">
                <% } else { %>
                <input id="resaltar" type="text" name="resaltar" size="10">
                <% } %>
            </div>
            <div id="acciones-codigo">
                <a id="cancelar" href="<%= "../"+user.getCategory()+"/" %>">Cancelar</a>
                <a id="guardar" href="<%= "../"+user.getCategory()+"/" %>">Guardar</a>
            </div>
        </div>
    </body>
</html>
