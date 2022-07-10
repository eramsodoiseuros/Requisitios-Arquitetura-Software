package Entidades;

import DAO.Dados;
import DAO.ParticipanteDAO;
import DAO.ResultadoPossivelDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Evento implements Dados<Evento> {

    private String idEvento;
    private String desporto;
    private String nome;
    private String estado;
    private String resultadoFinal;
    private LocalDateTime data;
    private ResultadoPossivelDAO resultadosPossiveis;
    private ParticipanteDAO participantes;

    public Evento(String estado, String desporto, String idEvento, String nome, String date) {
        this.estado = estado;
        this.desporto = desporto;
        this.idEvento = idEvento;
        this.nome = nome;
        this.data = LocalDateTime.parse(date);//LocalDateTime.now();
        this.resultadosPossiveis = ResultadoPossivelDAO.getInstance();
        this.participantes = ParticipanteDAO.getInstance();
        this.resultadoFinal = "undefined";
    }

    public Evento(String idEvento, String desporto, String nome, String estado, String resultadoFinal, String data) {
        this.idEvento = idEvento;
        this.desporto = desporto;
        this.nome = nome;
        this.estado = estado;
        this.resultadoFinal = resultadoFinal;
        this.data = LocalDateTime.parse(data);;
    }

    public Evento() {

    }

    public Evento(List<String> l) {
        this.idEvento = l.get(0);
        this.desporto = l.get(1);
        this.nome = l.get(2);
        this.estado = l.get(3);
        this.data = LocalDateTime.parse(l.get(4));
        this.resultadosPossiveis = ResultadoPossivelDAO.getInstance();
        this.participantes = ParticipanteDAO.getInstance();
    }

    public ResultadoPossivelDAO getResultadosPossiveis() {
        return resultadosPossiveis;
    }

    public ParticipanteDAO getParticipantes() {
        return participantes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDesporto() {
        return desporto;
    }

    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Entidades.Evento{" +
                ", nome='" + nome + '\'' +
                ", idEvento=" + idEvento +
                ", desporto='" + desporto + '\'' +
                ", data=" + data +
                "estado='" + estado + '\'' +
                '}';
    }

    public Dados<Evento> fromRow(final List<String> l) {
        return new Evento(l);
    }

    public List<String> toRow() {
        List<String> r = new ArrayList<>();
        r.add(this.idEvento);
        r.add(this.desporto);
        r.add(this.nome);
        r.add(this.estado);
        r.add(String.valueOf(this.data));
        return r;
    }
}
