package DAO;

import Entidades.Aposta;
import Entidades.ResultadoPossivel;

import java.util.*;

public class ApostaDAO extends DataAcessObject<String, Aposta> {
    private static ApostaDAO singleton = new ApostaDAO();

    public ApostaDAO() {
        super(new Aposta(), "Aposta", Arrays.asList("idAposta", "quantia", "odd", "idResultado", "idApostador","moeda"));
    }

    public static ApostaDAO getInstance(){
        return ApostaDAO.singleton;
    }

    public ArrayList<Aposta> values(){
        return (ArrayList<Aposta>) super.values();
    }

    public Aposta get(final String key) {
        return super.get(key);
    }

    public Aposta put(final Aposta value) {
        return super.put(value, value.getIdAposta());
    }

    public Aposta remove(final String key) {
        return super.remove(key);
    }

    public List<Aposta> search(final String value) {
        return super.search(value, 0).stream().toList();
    }

    public List<Aposta> searchResultado(final String value) {
        return super.search(value, 3).stream().toList();
    }


    public List<Aposta> searchApostador(final String value) {
        return super.search(value, 4).stream().toList();
    }

    public String generateUniqueId(){
        String s = "" + (this.size() +1);
        return s;
    }


}
