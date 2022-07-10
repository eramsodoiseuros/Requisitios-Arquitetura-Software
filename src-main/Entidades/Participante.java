package Entidades;

import DAO.Dados;

import java.util.*;

public class Participante implements Dados<Participante> {
    private String idParticipante;
    private boolean rank;
    private int pontuacao;

    public Participante(String idParticipante, boolean rank, int pontuacao) {
        this.idParticipante = idParticipante;
        this.rank = rank;
        this.pontuacao = pontuacao;
    }

    public Participante() {

    }

    public Participante(List<String> l) {
        this.idParticipante = l.get(0);
        //this.pontuacao = Integer.parseInt(l.get(1));
        //this.rank = Boolean.parseBoolean(l.get(2));
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public boolean isRank() {
        return rank;
    }

    public void setRank(boolean rank) {
        this.rank = rank;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public String toString() {
        return "Entidades.Participante{" +
                "idParticipante='" + idParticipante + '\'' +
                ", rank=" + rank +
                ", pontuacao=" + pontuacao +
                '}';
    }

    public Dados<Participante> fromRow(final List<String> l) {
        return new Participante(l);
    }

    public List<String> toRow() {
        List<String> r = new ArrayList<>();
        r.add(this.idParticipante);
        //r.add(String.valueOf(this.pontuacao));
        //true -> 1 false -> 0
        //r.add(String.valueOf(rank ? 1 : 0));
        return r;
    }
}
