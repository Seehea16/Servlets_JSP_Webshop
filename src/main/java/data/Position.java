package data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Herbert Seewann
 */
public class Position {
    private int bestellID, artikelID, anzahl;

    public Position(int bestellID, int artikelID, int anzahl) {
        this.bestellID = bestellID;
        this.artikelID = artikelID;
        this.anzahl = anzahl;
    }
    
    public Position(ResultSet rs) throws SQLException {
        this.bestellID = rs.getInt("bestellID");
        this.artikelID = rs.getInt("artikelID");
        this.anzahl = rs.getInt("anzahl");
    }

    public int getBestellID() {
        return bestellID;
    }

    public void setBestellID(int bestellID) {
        this.bestellID = bestellID;
    }

    public int getArtikelID() {
        return artikelID;
    }

    public void setArtikelID(int artikelID) {
        this.artikelID = artikelID;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.bestellID;
        hash = 19 * hash + this.artikelID;
        hash = 19 * hash + this.anzahl;
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
        final Position other = (Position) obj;
        if (this.bestellID != other.bestellID) {
            return false;
        }
        if (this.artikelID != other.artikelID) {
            return false;
        }
        if (this.anzahl != other.anzahl) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position{" + "bestellID=" + bestellID + ", artikelID=" + artikelID + ", anzahl=" + anzahl + '}';
    }
}
