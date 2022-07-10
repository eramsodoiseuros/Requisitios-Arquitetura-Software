package DAO;

import Entidades.Apostador;
import Entidades.Moeda;
import Entidades.Saldo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ApostadorDAO extends DataAcessObject<String, Apostador>{
    private static ApostadorDAO singleton = new ApostadorDAO();

    public ApostadorDAO() {
        super(new Apostador(), "Apostador", Arrays.asList("idApostador", "email", "username", "password", "nome", "dataNascimento"));
    }

    public static ApostadorDAO getInstance(){
        return ApostadorDAO.singleton;
    }

    public ArrayList<Apostador> values(){
        return (ArrayList<Apostador>) super.values();
    }

    public Apostador get(final String key) {
        Apostador ret = super.get(key);
        Saldo s = this.getSaldo(key);
        ret.setSaldo(s);
        return ret;
    }

    public Apostador put(final Apostador value) {
        for(Map.Entry<Moeda,Float> valor : value.getSaldo().getCarteira().entrySet()){
            updateCarteira2(value.getIdApostador(),valor.getKey().getNome(),valor.getValue());
        }
        return super.put(value, value.getIdApostador());
    }

    public Apostador remove(final String key) {
        return super.remove(key);
    }

    public List<Apostador> search(final String value) {
        return super.search(value, 0).stream().toList();
    }

    public List<Apostador> searchEmail(final String value) {
        return super.search(value, 1).stream().toList();
    }

    public List<Apostador> searchUsername(final String value) {
        return super.search(value, 2).stream().toList();
    }
/*
    public void updateCarteira(String idApostador, String nomeMoeda, Float quantia){
        try {
            Connection connection = BaseDados.getConnection();
            String stm = "DELETE FROM Carteira WHERE apostador = '"
                    + idApostador+"' AND moeda = '" + nomeMoeda
                    +"';" ;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
            stm = "INSERT INTO Carteira VALUES(" +
                    "'"+idApostador+"', '"+ nomeMoeda+"', '"+ quantia+"');";
            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public void updateCarteira2(String idApostador, String nomeMoeda, Float quantia){
        try {
            Connection connection = BaseDados.getConnection();

            String stm = "INSERT INTO Carteira (id,apostador,moeda,valor) VALUES(" +
                    "'"+idApostador+nomeMoeda+"'," + "'"+idApostador+"', '"+ nomeMoeda+"', "+ quantia+")\n" +
                    "ON DUPLICATE KEY UPDATE valor = valor + " + quantia +";";
            PreparedStatement pst = connection.prepareStatement(stm);
            System.out.println(stm);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public Saldo getSaldo(String idApostador){
        Saldo s = new Saldo(idApostador);
        try {
            Connection connection = BaseDados.getConnection();
            String stm = "SELECT * FROM Carteira WHERE apostador = '"+idApostador+"';";
            PreparedStatement pst = connection.prepareStatement(stm);
            ResultSet rs = pst.executeQuery(stm);
            while (rs.next())
            {
                //String idApostador = rs.getString("apostador");
                String idMoeda = rs.getString("moeda");
                float valor = rs.getFloat("valor");
                Moeda m = MoedaDAO.getInstance().get(idMoeda);
                s.adicionarSaldo(m,valor);
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return s;
    }


}
