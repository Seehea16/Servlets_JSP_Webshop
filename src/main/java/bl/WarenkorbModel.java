package bl;

import data.Artikel;
import data.Bestellung;
import data.Position;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Herbert Seewann
 */
public class WarenkorbModel {
    
    private final List<Artikel> artikelListe;
    private final List<Position> aktuellePositionsListe;
    private final List<Position> positionsTestListe;
    private final List<Bestellung> bestellungsListe;
    private int bestellungsIDCounter;
    
    private WarenkorbModel() {
        this.artikelListe = new ArrayList<>();
        this.artikelListe.add(new Artikel(1, "CD", 3.50));
        this.artikelListe.add(new Artikel(2, "DVD", 15.00));
        this.artikelListe.add(new Artikel(3, "Fussball", 49.99));
        this.artikelListe.add(new Artikel(4, "Haarbuerste", 3.20));
        this.artikelListe.add(new Artikel(5, "Fernseher", 273.89));
        
        this.positionsTestListe = new ArrayList<>();
        this.aktuellePositionsListe = new ArrayList<>();
        this.bestellungsListe = new ArrayList<>();
        this.bestellungsIDCounter = getLastBestellungsID()+1;
    }
    
    public static WarenkorbModel getInstance() {
        return WarenkorbModelHolder.INSTANCE;
    }
    
    private static class WarenkorbModelHolder {

        private static final WarenkorbModel INSTANCE = new WarenkorbModel();
    }
    
    private int getLastBestellungsID() {
        int min = 0;
        for(Bestellung b : bestellungsListe) {
            if(b.getBestellID() > min) {
                min = b.getBestellID();
            }
        }
        return min;
    }
    
    public List<Artikel> getAllArtikel() {
        return this.artikelListe;
    }
    
    public void addPosition(Position p) {
        this.aktuellePositionsListe.add(p);
    }
    
    public void deletePosition(Position p) {
        this.aktuellePositionsListe.remove(p);
    }
    
    public void addBestellung() {
        this.bestellungsListe.add(new Bestellung(this.bestellungsIDCounter++, LocalDateTime.now()));
        for(Position p : this.aktuellePositionsListe) {
            p.setBestellID(bestellungsIDCounter);
            this.positionsTestListe.add(p);
        }
    }
    
    public void setAktuellePositionsListe(Bestellung b) {
        this.aktuellePositionsListe.clear();
        for(Position p : this.positionsTestListe) {
            if(p.getBestellID() == b.getBestellID()) {
                this.aktuellePositionsListe.add(p);
            }
        }
    }
    
    public List<Position> getAktuellePositionsListe() {
        return this.aktuellePositionsListe;
    }
    
    public void clearAktuellePositionsListe() {
        this.aktuellePositionsListe.clear();
    }
    
    public int getArtikelIndexByName(String name) {
        for(Artikel a : artikelListe) {
            if(a.getName().equals(name)) {
                return a.getArtikelID();
            }
        }
        return -1;
    }
}
