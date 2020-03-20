package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Herbert Seewann
 */
public class Artikel {
    private int artikelID;
    private String name;
    private double preis;

    public Artikel(int artikelID, String name, double preis) {
        this.artikelID = artikelID;
        this.name = name;
        this.preis = preis;
    }
    
    public Artikel(ResultSet rs) throws SQLException {
        this.artikelID = rs.getInt("artikelID");
        this.name = rs.getString("name");
        this.preis = rs.getDouble("preis");
    }

    public int getArtikelID() {
        return artikelID;
    }

    public void setArtikelID(int artikelID) {
        this.artikelID = artikelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.artikelID;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.preis) ^ (Double.doubleToLongBits(this.preis) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Artikel other = (Artikel) obj;
        if (this.artikelID != other.artikelID) {
            return false;
        }
        if (Double.doubleToLongBits(this.preis) != Double.doubleToLongBits(other.preis)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Artikel{" + "artikelID=" + artikelID + ", name=" + name + ", preis=" + preis + '}';
    }
}
