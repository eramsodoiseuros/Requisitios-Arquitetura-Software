package BL;

import DAO.*;
import Entidades.*;

import java.util.HashMap;
import java.util.List;

public class Gestor implements IGestorFacade{

    @Override
    public void atualizarOdds(String idResultado, Float odd) {
        String id = idResultado.split(" - ")[0];
        ResultadoPossivel r = ResultadoPossivelDAO.getInstance().get(id);
        r.setOdd(odd);
        ResultadoPossivelDAO.getInstance().put(r);
    }

    @Override
    public void atualizarEstadoApostas(String idEvento, String tipoAposta, String estado) {
        List<ResultadoPossivel> resultadoPossivels = ResultadoPossivelDAO.
                getInstance().searchEvento(idEvento)
                .stream().filter(y -> y.getTipoAposta().equals(tipoAposta) || tipoAposta.equals("todas")).toList();
        HashMap<String,Double> apostadores = new HashMap<>();
        for(ResultadoPossivel r : resultadoPossivels) {
            if (!r.getEstado().equals("fechado")) {
                r.setEstado(estado);
                ResultadoPossivelDAO.getInstance().put(r);
                //atualizar carteiras dos apostadores que fizeram apostas sobre este resultado - apenas se for mudado para fechado ou suspenso
                if (estado.equals("fechado")){
                    atualizarCarteira(r);
                //adicionar apostadores a lista de apostadores a serem atualizados
                    List<Aposta> apostas = ApostaDAO.getInstance().searchResultado(r.getIdResultado());
                    for (Aposta aposta : apostas) {
                        Double count = apostadores.computeIfPresent(aposta.getIdApostador(), (k, v) -> v + 1);
                        //nao existe ainda no map
                        if (count == null) {
                            apostadores.put(aposta.getIdApostador(), 0.0);
                        }
                    }
                }
            if (!estado.equals("aberto"))
                Subject.getInstance().notifyApostadores(apostadores);
            }
        }
    }

    @Override
    public void atualizarResultadoEvento(String idEvento, String resultado) {

    }

    @Override
    public void atualizarResultadoPossivel(String idResultado, boolean ganhou) {
        String id = idResultado.split(" - ")[0];
        ResultadoPossivel r = ResultadoPossivelDAO.getInstance().get(id);
        r.setGanhou(ganhou);
        ResultadoPossivelDAO.getInstance().put(r);
    }

    public Evento getEvento(String idEvento){
        return EventoDAO.getInstance().get(idEvento);
    }

    @Override
    public void adicionarEvento(Evento e,List<String> participantes) {
        EventoDAO.getInstance().put(e);
        for(String participante : participantes){
            e.getParticipantes().addEventosParticipantes(e.getIdEvento(),participante,0);
        }
    }

    @Override
    //INACABADO -> falta observer
    public void finalizarEvento(String idEvento, String estado) {
        Evento e = EventoDAO.getInstance().get(idEvento);
        e.setEstado(estado);
        EventoDAO.getInstance().remove(e.getIdEvento());
        EventoDAO.getInstance().put(e,e.getIdEvento());
        this.atualizarEstadoApostas(idEvento,"todas",estado);
        /*
        Evento e = EventoDAO.getInstance().get(idEvento);
        List<ResultadoPossivel> resultadoPossivels = ResultadoPossivelDAO.getInstance().searchEvento(idEvento);
        //apostadores que deverao ser atualizados
        //id apostador, numero de apostas atualizadas
        HashMap<String,Double> apostadores = new HashMap<>();
        //atualizar os estados da aposta todos para fechado
        for(ResultadoPossivel r : resultadoPossivels){
            r.setEstado("fechado");
            ResultadoPossivelDAO.getInstance().put(r);
            //atualizar carteiras dos apostadores que fizeram apostas sobre este resultado
            atualizarCarteira(r);
            //adicionar apostadores a lista de apostadores a serem atualizados
            List<Aposta> apostas = ApostaDAO.getInstance().searchResultado(r.getIdResultado());
            for (Aposta aposta: apostas){
                Double count = apostadores.computeIfPresent(aposta.getIdApostador(),(k,v) -> v+1);
                //nao existe ainda no map
                if (count == null){
                    apostadores.put(aposta.getIdApostador(),0.0);
                }
            }
        }
        apostadores.forEach((k,v) -> System.out.println("Apostador: " + k + "\nvalue: " +v));
        Subject.getInstance().notifyApostadores(apostadores);
        //resultados.forEach(y -> y.);*/
    }

    @Override
    public void abrirEvento(String idEvento, String estado) {
            Evento e = EventoDAO.getInstance().get(idEvento);
            e.setEstado(estado);
            EventoDAO.getInstance().remove(e.getIdEvento());
            EventoDAO.getInstance().put(e,e.getIdEvento());
            this.atualizarEstadoApostas(idEvento,"todas",estado);
    }

    //atualizar a carteira dos apostadores q apostaram neste resultado possivel
    //se o resultado posivel nao se realizou nao da nada
    public void atualizarCarteira(ResultadoPossivel rp){
        List<Aposta> apostas = ApostaDAO.getInstance().searchResultado(rp.getIdResultado());
        for (Aposta a : apostas){
            String idApostador = a.getIdApostador();
            float multiplier = rp.isGanhou()? a.getOddFixa() : 0.0f;
            float quantia = a.getQuantia() * multiplier;
            ApostadorDAO.getInstance().updateCarteira2(idApostador,a.getIdMoeda(),quantia);
        }
    }

    @Override
    public List<Evento> listarEventos() {
        return EventoDAO.getInstance().values();
    }
}
