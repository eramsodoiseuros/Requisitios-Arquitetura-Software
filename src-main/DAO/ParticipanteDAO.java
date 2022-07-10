package DAO;

import Entidades.Moeda;
import Entidades.Participante;
import Entidades.Saldo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ParticipanteDAO extends DataAcessObject<String, Participante> {
    private static ParticipanteDAO singleton = new ParticipanteDAO();

    public ParticipanteDAO() {
        super(new Participante(), "Participante", Arrays.asList("idParticipante"));
    }

    public static ParticipanteDAO getInstance(){
        return ParticipanteDAO.singleton;
    }

    public ArrayList<Participante> values(){
        return (ArrayList<Participante>) super.values();
    }

    public Participante get(final String key) {
        return super.get(key);
    }

    public Participante put(final Participante value) {
        return super.put(value, value.getIdParticipante());
    }

    public Participante remove(final String key) {
        return super.remove(key);
    }

    public List<Participante> search(final String value) {
        return super.search(value, 0).stream().toList();
    }

    public void addEventosParticipantes(String idEvento, String idParticipante,Integer pontuacao){
        try {
        Connection connection = BaseDados.getConnection();
        String stm = "DELETE FROM eventoParticipantes WHERE Evento = '" + idEvento+"' AND participante = '" + idParticipante+"';" ;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
        stm = "INSERT INTO eventoParticipantes VALUES(" +
                "'"+idEvento+"', '"+ idParticipante+"', '"+ pontuacao+"');";
            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
    Particpante 1 -> 2
    Participante 3 -> 4
    P1 2 - 4 P3
     */
    public List<Participante> getResultadoEvento(String idEvento){
        List<Participante> resultados = new ArrayList<>();
        try {
            Connection connection = BaseDados.getConnection();
            String stm = "SELECT * FROM eventoParticipantes WHERE evento = '"+idEvento+"';";
            PreparedStatement pst = connection.prepareStatement(stm);
            ResultSet rs = pst.executeQuery(stm);
            while (rs.next())
            {
                //String idApostador = rs.getString("apostador");
                String idParticipante = rs.getString("participante");
                Integer pontuacao = rs.getInt("pontuacao");
                //rank nao interessa
                resultados.add(new Participante(idParticipante,false,pontuacao));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultados;
    }

}
