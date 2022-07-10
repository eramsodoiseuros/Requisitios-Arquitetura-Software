package DAO;

import Entidades.ResultadoPossivel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ResultadoPossivelDAO extends DataAcessObject<String, ResultadoPossivel> {
    private static ResultadoPossivelDAO singleton = new ResultadoPossivelDAO();

    public ResultadoPossivelDAO() {
        super(new ResultadoPossivel(), "ResultadoPossivel", Arrays.asList("idResultadoPossivel", "odd", "tipoAposta",  "descricao","ganhou", "estado","participante", "idEvento"));
    }

    public static ResultadoPossivelDAO getInstance(){
        return ResultadoPossivelDAO.singleton;
    }

    public ArrayList<ResultadoPossivel> values(){
        return (ArrayList<ResultadoPossivel>) super.values();
    }

    public ResultadoPossivel get(final String key) {
        return super.get(key);
    }

    public ResultadoPossivel put(final ResultadoPossivel value) {
        return super.put(value, value.getIdResultado());
    }

    public ResultadoPossivel remove(final String key) {
        return super.remove(key);
    }

    public List<ResultadoPossivel> search(final String value) {
        return super.search(value, 0).stream().toList();
    }

    public List<ResultadoPossivel> searchTipo(final String value) {
        return super.search(value, 2).stream().toList();
    }

    public List<ResultadoPossivel> searchDescricao(final String value) {
        return super.search(value, 3).stream().toList();
    }

    public List<ResultadoPossivel> searchEstado(final String value) {
        return super.search(value, 5).stream().toList();
    }

    public List<ResultadoPossivel> searchEvento(final String value) {
        return super.search(value, 7).stream().toList();
    }

    public void addEventosResultados(String idEvento, String idResultado){
        //pointless - resultados possiveis tem evento
        /*
        try {
            Connection connection = BaseDados.getConnection();
            String stm = "DELETE FROM eventoResultados WHERE Evento = '" + idEvento+"' AND participante = '" + idParticipante+"';" ;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
            stm = "INSERT INTO eventoResultados VALUES(\n" +
                    "'"+idEvento+"', '"+ idResultado+"');";
            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        ;
    }


}
