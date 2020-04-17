package sql;

/**
 *
 * @author Herbert Seewann
 */
public enum SQLStatements {
    PSTMT_GET_ALL_ARTIKEL("SELECT * FROM artikel;"),
    PSTMT_GET_ALL_BESTELLUNGEN("SELECT * FROM bestellung;"),
    PSTMT_GET_ALL_POSITIONEN_BY_BESTELLID("SELECT * FROM position WHERE bestellID = ?;"),
    PSTMT_ADD_BESTELLUNG("INSERT INTO bestellung (dateTime) VALUES (?);"),
    PSTMT_ADD_POSITION("INSERT INTO position (bestellID,artikelID,anzahl) VALUES (?,?,?);");

    private String sql;

    private SQLStatements(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}