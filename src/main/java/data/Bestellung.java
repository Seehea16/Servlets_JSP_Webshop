package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Herbert Seewann
 */
public class Bestellung {
    private int bestellID;
    private LocalDateTime dateTime;

    public Bestellung(int bestellID, LocalDateTime dateTime) {
        this.bestellID = bestellID;
        this.dateTime = dateTime;
    }
    
    public Bestellung(ResultSet rs) throws SQLException {
        this.bestellID = rs.getInt("bestellID");
        this.dateTime = rs.getTimestamp("dateTime").toLocalDateTime();
    }

    public int getBestellID() {
        return bestellID;
    }

    public void setBestellID(int bestellID) {
        this.bestellID = bestellID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.bestellID;
        hash = 47 * hash + Objects.hashCode(this.dateTime);
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
        final Bestellung other = (Bestellung) obj;
        if (this.bestellID != other.bestellID) {
            return false;
        }
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bestellung{" + "bestellID=" + bestellID + ", dateTime=" + dateTime + '}';
    }
}
