package DAO;

import Entidades.Evento;
import java.util.*;

public class EventoDAO extends DataAcessObject<String, Evento> {
    private static EventoDAO singleton = new EventoDAO();

    public EventoDAO() {
        super(new Evento(), "Evento", Arrays.asList("idEvento", "desporto", "nome", "estado", "data"));
    }

    public static EventoDAO getInstance(){
        return EventoDAO.singleton;
    }

    public ArrayList<Evento> values(){
        return (ArrayList<Evento>) super.values();
    }

    public Evento get(final String key) {
        return super.get(key);
    }

    public Evento put(final Evento value) {
        return super.put(value, value.getIdEvento());
    }

    public Evento remove(final String key) {
        return super.remove(key);
    }

    public List<Evento> search(final String value) {
        return super.search(value, 0).stream().toList();
    }

    public List<Evento> searchDesporto(final String value) {
        return super.search(value, 1).stream().toList();
    }

    public List<Evento> searchNome(final String value) {
        return super.search(value, 2).stream().toList();
    }

    public List<Evento> searchEstado(final String value) {
        return super.search(value, 3).stream().toList();
    }

    public String generateUniqueId(){
        String s = "" + (this.size() +1);
        return s;
    }

}
