package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.Map.entry;

public abstract class DataAcessObject<K, O> {
    private String table;
    private List<String> columns;
    private Dados<K> token;

    private DataAcessObject() {
    }

    public DataAcessObject(final O token, final String table, final List<String> columns) {
        this.token = (Dados<K>) token;
        this.table = table;
        this.columns = columns;
    }

    private Map<String, StatementCall<K>> sets = Map.ofEntries(
            entry("java.lang.String", (pst, id, value) -> pst.setString(id, (String) value)),
            entry("java.lang.Integer", (pst, id, value) -> pst.setInt(id, (Integer) value)),
            entry("java.lang.Double", (pst, id, value) -> pst.setDouble(id, (Double) value)));

    private void setValue(final PreparedStatement pst, final int id, final Object value) throws SQLException {
        this.sets.get(value.getClass().getName()).setParameter(pst, id, (K) value);
    }

    /**
     * @return
     */
    public int size() {
        Connection connection = BaseDados.getConnection();
        int r = 0;

        try {
            PreparedStatement pst = connection.prepareStatement("SELECT COUNT(*) FROM " + this.table);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
                r = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        Connection connection = BaseDados.getConnection();
        boolean r = true;

        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM " + this.table);
            ResultSet rs = pst.executeQuery();
            r = !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @param key
     * @return
     */
    public boolean containsKey(final K... key) {
        Connection connection = BaseDados.getConnection();
        boolean r = false;

        try {
            String stm = "SELECT * FROM " + this.table + " WHERE ";
            int x = 0;
            for (K ignored : key) {
                if (x != 0)
                    stm += " AND ";

                stm += this.columns.get(x++) + " = ? ";
            }

            PreparedStatement pst = connection.prepareStatement(stm);
            x = 1;
            for (K k : key) {
                this.setValue(pst, x++, k);
            }
            ResultSet rs = pst.executeQuery();
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @param value
     * @param index
     * @return
     */
    public Set<O> search(final K value, final int... index) {
        Set<O> result = new HashSet<>();
        Connection connection = BaseDados.getConnection();

        try {
            String stm = "SELECT * FROM " + this.table + " WHERE ";

            for (int i = 0; i < index.length; i++) {
                if (i != 0)
                    stm += " OR ";

                stm += this.columns.get(index[i]) + " LIKE ? ";
            }

            PreparedStatement pst = connection.prepareStatement(stm);

            for (int i = 1; i <= index.length; i++) {
               // this.setValue(pst, i, "%" + value.toString() + "%");
                this.setValue(pst, i, value.toString());
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int length = rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<>(length);

                for (int i = 1; i <= length; i++)
                    row.add(rs.getString(i));

                result.add((O) token.fromRow(row));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param key
     * @return
     */
    public List<O> find(final K... key) {
        Connection connection = BaseDados.getConnection();
        List<O> result = new ArrayList<>();

        try {
            String stm = "SELECT * FROM " + this.table + " WHERE ";
            int x = 0;
            for (K ignored : key) {
                if (x != 0)
                    stm += " AND ";

                stm += this.columns.get(x++) + " = ? ";
            }

            PreparedStatement pst = connection.prepareStatement(stm);
            x = 1;
            for (K k : key) {
                this.setValue(pst, x++, k);
            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int length = rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<>(length);

                for (int i = 1; i <= length; i++)
                    row.add(rs.getString(i));

                result.add((O) token.fromRow(row));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *
     * @return
     */
    public Collection<O> values() {
        Connection connection = BaseDados.getConnection();
        Collection<O> result = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM " + this.table);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int length = rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<>(length);

                for (int i = 1; i <= length; i++)
                    row.add(rs.getString(i));

                result.add((O) token.fromRow(row));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param key
     * @return
     */
    public O get(final K... key) {
        Connection connection = BaseDados.getConnection();
        O o = null;
        try {
            String stm = "SELECT * FROM " + this.table + " WHERE ";
            int x = 0;
            for (K ignored : key) {
                if (x != 0)
                    stm += " AND ";

                stm += this.columns.get(x++) + " = ? ";
            }

            PreparedStatement pst = connection.prepareStatement(stm);
            x = 1;
            for (K k : key) {
                this.setValue(pst, x++, k);
            }
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int length = rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<>(length);

                for (int i = 1; i <= length; i++)
                    row.add(rs.getString(i));

                o = (O) token.fromRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return o;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public O put(final O value, final K... key) {
        Connection connection = BaseDados.getConnection();
        try {
            O result = this.get(key);
            String stm = "DELETE FROM " + this.table + " WHERE ";
            int x = 0;
            for (K ignored : key) {
                if (x != 0)
                    stm += " AND ";

                stm += this.columns.get(x++) + " = ? ";
            }

            PreparedStatement pst = connection.prepareStatement(stm);
            x = 1;
            for (K k : key) {
                this.setValue(pst, x++, k);
            }
            pst.executeUpdate();

            stm = "INSERT INTO " + this.table + " VALUES (";

            int i;
            Dados<K> Dados = (Dados<K>) value;
            List<String> row = Dados.toRow();
            for (i = 0; i < row.size() - 1; i++) {
                stm += " '" + row.get(i) + "', ";
            }
            stm += "'" + row.get(i) + "' ) ";

            pst = connection.prepareStatement(stm);
            System.out.println(stm);
            pst.executeUpdate();
            connection.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    public O remove(final K... key) {
        Connection connection = BaseDados.getConnection();
        O o = null;

        try {
            o = this.get(key);
            if (o != null) {
                String stm = "DELETE FROM " + this.table + " WHERE ";
                int x = 0;
                for (Object ignored : key) {
                    if (x != 0)
                        stm += " AND ";

                    stm += this.columns.get(x++) + " = ? ";
                }

                PreparedStatement pst = connection.prepareStatement(stm);
                x = 1;
                for (Object k : key) {
                    this.setValue(pst, x++, k);
                }
                pst.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return o;
    }

    /**
     *
     */
    public void clear() {
        Connection connection = BaseDados.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("TRUNCATE TABLE " + this.table);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String generateUniqueId(){
        String s = "" + (this.size() +1);
        return s;
    }

}

