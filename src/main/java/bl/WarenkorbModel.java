package bl;

import data.Artikel;
import data.Bestellung;
import data.Position;
import db.DBAccess;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Herbert Seewann
 */
public class WarenkorbModel {
    
    private final DBAccess dba;
    private final List<Position> aktuellePositionsListe;
    
    private WarenkorbModel() {
        dba = DBAccess.getInstance();
        
        this.aktuellePositionsListe = new ArrayList<>();
    }
    
    public static WarenkorbModel getInstance() {
        return WarenkorbModelHolder.INSTANCE;
    }
    
    private static class WarenkorbModelHolder {

        private static final WarenkorbModel INSTANCE = new WarenkorbModel();
    }
    
    public List<Artikel> getAllArtikel() throws SQLException {
        return this.dba.getAllArtikel();
    }
    
    public List<Bestellung> getAllBestellungen() throws SQLException {
        return this.dba.getAllBestellungen();
    }
    
    public void addPosition(Position p) {
        this.aktuellePositionsListe.add(p);
    }
    
    public void deletePosition(Position p) {
        this.aktuellePositionsListe.remove(p);
    }
    
    public void addBestellung(LocalDateTime dateTime) throws SQLException{
        int bestellID = this.dba.addBestellung(dateTime);
        for(Position p : this.aktuellePositionsListe) {
            p.setBestellID(bestellID);
            this.dba.addPosition(p);
        }
        this.aktuellePositionsListe.clear();
    }
    
    public void setAktuellePositionsListe(int bestellID) throws SQLException {
        this.aktuellePositionsListe.clear();
        for(Position p : this.dba.getAllPositionenByBestellID(bestellID)) {
            this.aktuellePositionsListe.add(p);
        }
    }
    
    public List<Position> getAktuellePositionsListe() {
        return this.aktuellePositionsListe;
    }
    
    public int getArtikelIDByName(String name) throws SQLException {
        for(Artikel a : this.dba.getAllArtikel()) {
            if(a.getName().equals(name)) {
                return a.getArtikelID();
            }
        }
        return -1;
    }
    
    public double getAktuelleSumme() throws SQLException {
        double sum = 0;
        for(Position p : aktuellePositionsListe) {
            for(Artikel a : this.getAllArtikel()) {
                if(a.getArtikelID() == p.getArtikelID()) {
                    sum += a.getPreis() * p.getAnzahl();
                    break;
                }
            }
        }
        return sum;
    }
}
