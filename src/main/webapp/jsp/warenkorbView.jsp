<%@page import="java.util.List"%>
<%@page import="data.Artikel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Webshop</title>
        <style>
            h1 {
                color: green;
            }
            
            h3 {
                background-color: blue;
                color: white;
            }
        </style>
    </head>
    <body style="background-color: lightblue; text-align: center">
        <h1>Webshop</h1>
        <br>
        
        <% List<Artikel> artikelListe = (List<Artikel>) application.getAttribute("artikelListe");%>
        <h3>Artikel</h3>
        <form id="artikelForm" action="WarenkorbDispatcher" method="POST">
            <select id="artikel" name="artikel">
                <% 
                    for(Artikel a : artikelListe) {
                        String optStr = request.getParameter("artikel") != null ? request.getParameter("artikel") : "CD";
                %>
                <option value="<%=a.getName()%>" <%=optStr.equals(a.getName()) ? "selected" : ""%>><%=a.getName()%></option>
                <%
                    }
                %>
            </select>
            <select id="anzahl" name="anzahl">
                <%
                    for(int i=1; i<=5; i++) {
                        String optStr = request.getParameter("anzahl") != null ? request.getParameter("anzahl") : "1";
                %>
                <option value="<%=i%>" <%=optStr.equals(i+"") ? "selected" : ""%>><%=i%></option>
                <%
                    }
                %>
            </select>
            <button type="submit" for="artikelForm" id="cmd" name="cmd" value="hinzufuegen">Zum Warenkorb hinzufügen</button>
        </form>
    </body>
</html>
