<%@page import="java.util.ArrayList"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="data.Bestellung"%>
<%@page import="java.time.LocalDate"%>
<%@page import="data.Position"%>
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
        
        <%
            String errorMsg = (String) request.getAttribute("errorMsg");
            if(errorMsg!=null) {
        %>
        <strong><p style="color:red; text-align: center"><%=errorMsg%></p></strong>
        <br>
        <%
            }
        %>
        
        <% List<Artikel> artikelListe = (List<Artikel>) application.getAttribute("artikelListe");%>
        <h3>Artikel</h3>
        <form id="hinzuArtikelForm" action="WarenkorbDispatcher" method="POST">
            <select id="artikel" name="hinzuArtikelName">
                <% 
                    for(Artikel a : artikelListe) {
                        String optStr = request.getParameter("hinzuArtikelName") != null ? request.getParameter("hinzuArtikelName") : "CD";
                %>
                <option value="<%=a.getName()%>" <%=optStr.equals(a.getName()) ? "selected" : ""%>><%=a.getName()%></option>
                <%
                    }
                %>
            </select>
            <select id="anzahl" name="hinzuArtikelAnzahl">
                <%
                    for(int i=1; i<=5; i++) {
                        String optStr = request.getParameter("hinzuArtikelAnzahl") != null ? request.getParameter("hinzuArtikelAnzahl") : "1";
                %>
                <option value="<%=i%>" <%=optStr.equals(i+"") ? "selected" : ""%>><%=i%></option>
                <%
                    }
                %>
            </select>
            <button type="submit" for="hinzuArtikelForm" id="cmd" name="cmd" value="hinzufuegen" style="background-color: white">Zum Warenkorb hinzufügen</button>
        </form>
            
        <h3>Warenkorb-Positionen</h3>
        <p><%=LocalDate.now()%></p>
        <% List<Position> positionen = (List<Position>) request.getAttribute("positionsListe") != null ? (List<Position>) request.getAttribute("positionsListe") : new ArrayList<>();%>
        <form id="positionsForm" action="WarenkorbDispatcher" method="POST">
            <table border="2" align="center" style="background-color: greenyellow">
                <tr>
                    <th>Name</th>
                    <th>Preis</th>
                    <th>Anzahl</th>
                    <th>Button</th>
                </tr>
                <%
                    for(Position p : positionen) {
                        Artikel tmp = null;
                        for(Artikel a : artikelListe) {
                            if(a.getArtikelID() == p.getArtikelID()) {
                                tmp = a;
                                break;
                            }
                        }
                %>
                <tr>
                    <td><%=tmp.getName()%></td>
                    <td><%=tmp.getPreis()%></td>
                    <td><%=p.getAnzahl()%></td>
                    <td><button type="submit" for="positionsForm" id="cmd" name="cmd" value="entfernen#<%=tmp.getName()%>" style="background-color: white">Entfernen</button></td>
                </tr>
                <%
                    }
                %>
            </table>
            <p>---------- Summe: <%= request.getAttribute("summe") != null ? request.getAttribute("summe") : "00.00"%> ----------</p>
            <button type="submit" for="positionsForm" id="cmd" name="cmd" value="bestellen" style="background-color: white">Bestellen</button>
        </form>
            
        <h3>Warenkorb-Bestellungen</h3>
        <% List<Bestellung> bestellungen = (List<Bestellung>) request.getAttribute("bestellungsListe") != null ? (List<Bestellung>) request.getAttribute("bestellungsListe") : new ArrayList<>();%>
        <form id="bestellungsForm" action="WarenkorbDispatcher" method="POST">
            <%
                for(Bestellung b : bestellungen) {
            %>
            <label><%=b.getDateTime().format(DateTimeFormatter.ISO_DATE)%></label>
            <label>Hier könnte ihr Preis stehen, dazu müsste man in der Bestellung eine Summe machen.</label>
            <button type="submit" for="bestellungsForm" id="cmd" name="cmd" value="posAnzeigen#<%=b.getBestellID()%>" style="background-color: white">Positionen anzeigen</button>
            <br>
            <%
                }
            %>
        </form>
    </body>
</html>