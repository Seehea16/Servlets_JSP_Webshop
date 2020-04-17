package servlet;

import bl.WarenkorbModel;
import data.Position;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Herbert Seewann
 */
@WebServlet(name = "WarenkorbDispatcher", urlPatterns = {"/WarenkorbDispatcher"})
public class WarenkorbDispatcher extends HttpServlet {

    private WarenkorbModel model;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        this.model = WarenkorbModel.getInstance();
        try {
            config.getServletContext().setAttribute("artikelListe", model.getAllArtikel());
        } catch (SQLException ex) {
            config.getServletContext().setAttribute("errorMsg", "Artikel konnten nicht eingelesen werden!");
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("jsp/warenkorbView.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("positionsListe", this.model.getAktuellePositionsListe());
            request.setAttribute("bestellungsListe", this.model.getAllBestellungen());
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Fehler im doGet()");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cmd = request.getParameter("cmd");
            if(cmd.startsWith("entfernen#")) {
                doEntfernen(request, response, cmd.substring(10));
            } else if(cmd.startsWith("posAnzeigen#")) {
                doPositionenAnzeigen(request, response, cmd.substring(12));
            } else if(cmd.equals("hinzufuegen")) {
                doHinzufuegen(request, response);
            } else if(cmd.equals("bestellen")) {
                doBestellen(request, response, LocalDateTime.now());
            } else {
                throw new Exception(cmd);
            }
        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Kommando falsch: " + ex.getMessage());
        } finally {
            processRequest(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void doEntfernen(HttpServletRequest request, HttpServletResponse response, String artikelName) {
        try {
            String aName = artikelName;
            int aID = this.model.getArtikelIDByName(aName);
            for(Position p : this.model.getAktuellePositionsListe()) {
                if(p.getArtikelID() == aID) {
                    this.model.deletePosition(p);
                    request.setAttribute("summe", this.model.getAktuelleSumme());
                    request.setAttribute("bestellungsListe", this.model.getAllBestellungen());
                    request.setAttribute("positionsListe", this.model.getAktuellePositionsListe());
                    return;
                }
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", "Fehler beim Entfernen!");
        }
    }

    private void doHinzufuegen(HttpServletRequest request, HttpServletResponse response) {
        try {
            String aName = request.getParameter("hinzuArtikelName");
            int aID = this.model.getArtikelIDByName(aName);
            for(Position p : this.model.getAktuellePositionsListe()) {
                if(p.getArtikelID() == aID) {
                    request.setAttribute("errorMsg", "Artikel bereits vorhanden!");
                    request.setAttribute("bestellungsListe", this.model.getAllBestellungen());
                    request.setAttribute("positionsListe", this.model.getAktuellePositionsListe());
                    request.setAttribute("summe", this.model.getAktuelleSumme());
                    return;
                }
            }
            model.addPosition(new Position(0, aID, Integer.parseInt(request.getParameter("hinzuArtikelAnzahl"))));
            request.setAttribute("bestellungsListe", this.model.getAllBestellungen());
            request.setAttribute("positionsListe", this.model.getAktuellePositionsListe());
            request.setAttribute("summe", this.model.getAktuelleSumme());
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", "Fehler beim Hinzufügen!");
        }
    }

    private void doBestellen(HttpServletRequest request, HttpServletResponse response, LocalDateTime dateTime) {
        try {
            this.model.addBestellung(dateTime);
            request.setAttribute("bestellungsListe", this.model.getAllBestellungen());
            request.setAttribute("positionsListe", this.model.getAktuellePositionsListe());
            request.setAttribute("summe", this.model.getAktuelleSumme());
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", "Fehler beim Bestellen!");
        }
    }

    private void doPositionenAnzeigen(HttpServletRequest request, HttpServletResponse response, String bestellIDString) {
        try {
            this.model.setAktuellePositionsListe(Integer.parseInt(bestellIDString));
            request.setAttribute("bestellungsListe", this.model.getAllBestellungen());
            request.setAttribute("positionsListe", this.model.getAktuellePositionsListe());
            request.setAttribute("summe", this.model.getAktuelleSumme());
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", "Fehler beim Positionen anzeigen!" + ex.getMessage());
        }
    }
}