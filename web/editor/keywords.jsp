<%-- 
    Document   : keywords
    Created on : 16/06/2014, 03:08:25 PM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>

<%
ServletContext sc = getServletContext();

String lang = request.getParameter("lang");
String word = request.getParameter("word");
String fword = request.getParameter("fword");
String xmlKeywords = sc.getRealPath("/WEB-INF/data/"+lang+"Keywords.xml");

List<String> keywords = XMLReader.ReadXMLFile.ReadKeywords(lang, xmlKeywords);

boolean find = false;
for(String s : keywords) {
    if( lang.equals("SQL") && s.equals(fword.toLowerCase()) )
        find = true;
    else if( s.equals(fword) )
        find = true;
}

if( !find ) {
    for(String s : keywords) {
        if( lang.equals("SQL") && s.startsWith(word.toLowerCase()) && !s.equals(word.toLowerCase()) ) {
            int mayus = 0;
            for(int i=0;i<word.length();i++)
                if( word.charAt(i) >= 'A' && word.charAt(i) <= 'Z' )
                    mayus++;
            if( mayus >= word.length() / 2 )
                out.println("<p>" + s.toUpperCase() + "</p>");
            else
                out.println("<p>" + s + "</p>");
        }
        else if( s.startsWith(word) && !s.equals(word) ) {
            out.println("<p>" + s + "</p>");
        }
    }
}
%>