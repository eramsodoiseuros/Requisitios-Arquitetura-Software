package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementCall<K> {

    /**
     *  Metodo que prepara um statement sql
     * */
    void setParameter(PreparedStatement pst, int id, K value) throws SQLException;
}
