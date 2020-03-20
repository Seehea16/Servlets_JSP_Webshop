package bl;

import data.Artikel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Herbert Seewann
 */
public class WarenkorbModel {
    
    private final List<Artikel> artikelListe;
    
    private WarenkorbModel() {
        this.artikelListe = new ArrayList<>();
        this.artikelListe.add(new Artikel(1, "CD", 3.50));
        this.artikelListe.add(new Artikel(2, "DVD", 15.00));
        this.artikelListe.add(new Artikel(3, "Fussball", 49.99));
        this.artikelListe.add(new Artikel(4, "Haarbuerste", 3.20));
        this.artikelListe.add(new Artikel(5, "Fernseher", 273.89));
    }
    
    public static WarenkorbModel getInstance() {
        return WarenkorbModelHolder.INSTANCE;
    }
    
    private static class WarenkorbModelHolder {

        private static final WarenkorbModel INSTANCE = new WarenkorbModel();
    }
    
    public List<Artikel> fetchAllArtikel() {
        return this.artikelListe;
    }
}
