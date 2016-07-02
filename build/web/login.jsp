<%-- 
    Document   : Login
    Created on : 4/06/2014, 10:20:34 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.invalidate(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta name="author" content="Alejandro Alberto Yescas Benitez">
        <meta name="keywords" content="Syntax Highlighter, Code">
        
        <link rel="stylesheet" type="text/css" href="css/reset.css">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        
        <title>P&aacute;gina de Login</title>
    </head>
    <body>
        <form action="validate_login.jsp" method="post" id="login">
            <div id="usuario">
                <img src="img/log.png" alt="user"><!--
             --><input type="text" name="user">
            </div>
            <div id="password">
                <img src="img/pass.png" alt="pass"><!--
             --><input type="password" name="pass"><!--
             --><input type="submit" value=" ">
            </div>
        </form>
    </body>
</html>
