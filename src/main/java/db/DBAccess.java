package db;

import data.Artikel;
import data.Bestellung;
import data.Position;
import dbconfig.DBManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sql.SQLStatements;

/**
 *
 * @author Herbert Seewann
 */
public class DBAccess {

    private final DBManager dbm = DBManager.getInstance();

    public static DBAccess getInstance() {
        return DBAccessHolder.INSTANCE;
    }

    private static class DBAccessHolder {
        private static final DBAccess INSTANCE = new DBAccess();
    }
    
    public List<Artikel> getAllArtikel() throws SQLException {
        List<Artikel> artikelListe = new ArrayList<>();
        PreparedStatement pstmt = dbm.createPreparedStatement(SQLStatements.PSTMT_GET_ALL_ARTIKEL.getSql(), Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            artikelListe.add(new Artikel(rs));
        }
        pstmt.close(); 
        return artikelListe;
    }
    
    public List<Bestellung> getAllBestellungen() throws SQLException {
        List<Bestellung> bestellungsListe = new ArrayList<>();
        PreparedStatement pstmt = dbm.createPreparedStatement(SQLStatements.PSTMT_GET_ALL_BESTELLUNGEN.getSql(), Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bestellungsListe.add(new Bestellung(rs));
        }
        pstmt.close(); 
        return bestellungsListe;
    }
    
    public List<Position> getAllPositionenByBestellID(int bestellID) throws SQLException {
        List<Position> positionsListe = new ArrayList<>();
        PreparedStatement pstmt = dbm.createPreparedStatement(SQLStatements.PSTMT_GET_ALL_POSITIONEN_BY_BESTELLID.getSql(), Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, bestellID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            positionsListe.add(new Position(rs));
        }
        pstmt.close(); 
        return positionsListe;
    }
    
    public int addBestellung(LocalDateTime dateTime) throws SQLException { 
        PreparedStatement pstmt = dbm.createPreparedStatement(SQLStatements.PSTMT_ADD_BESTELLUNG.getSql(), Statement.RETURN_GENERATED_KEYS);
        pstmt.setTimestamp(1, Timestamp.valueOf(dateTime));
        pstmt.executeUpdate();
        int bestellID = dbm.readGeneratedKeys(pstmt);
        pstmt.close();
        return bestellID;
    }
    
    public void addPosition(Position p) throws SQLException { 
        PreparedStatement pstmt = dbm.createPreparedStatement(SQLStatements.PSTMT_ADD_POSITION.getSql(), Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, p.getBestellID());
        pstmt.setInt(2, p.getArtikelID());
        pstmt.setInt(3, p.getAnzahl());
        pstmt.executeUpdate();
        pstmt.close();
    }
}