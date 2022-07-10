package Entidades;

import DAO.Dados;
import DAO.ParticipanteDAO;
import DAO.ResultadoPossivelDAO;

import java.util.*;

public class ResultadoPossivel implements Dados<ResultadoPossivel>{

    private String idResultado;
    private String descricao;
    private boolean ganhou;
    private float odd;
    private String estado;
    private String tipoAposta;
    private String idEvento;
    private String participante;

    public ResultadoPossivel(String descricao, boolean ganhou, float odd, String estado, String idResultado, String tipoAposta, String idEvento, String participante) {
        this.descricao = descricao;
        this.ganhou = ganhou;
        this.odd = odd;
        this.estado = estado;
        this.idResultado = idResultado;
        this.tipoAposta = tipoAposta;
        this.idEvento = idEvento;
        this.participante = participante;
    }

    public ResultadoPossivel(){

    }

    public ResultadoPossivel(List<String> l){
        this.idResultado = l.get(0);
        this.odd = Float.parseFloat(l.get(1));
        this.tipoAposta = l.get(2);
        this.descricao = l.get(3);
        this.ganhou = Boolean.parseBoolean(l.get(4));
        this.estado = l.get(5);
        this.participante = l.get(6);
        this.idEvento = l.get(7);
    }



    public String getParticipante() {
        return participante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isGanhou() {
        return ganhou;
    }

    public void setGanhou(boolean ganhou) {
        this.ganhou = ganhou;
    }

    public float getOdd() {
        return odd;
    }

    public void setOdd(float odd) {
        this.odd = odd;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(String idResultado) {
        this.idResultado = idResultado;
    }

    public String getTipoAposta() {
        return tipoAposta;
    }

    public void setTipoAposta(String tipoAposta) {
        this.tipoAposta = tipoAposta;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    @Override
    public String toString() {
        return "descricao:'" + descricao +
                "\nodd:" + odd +
                "\nestado: '" + estado  +
                "\nidentificador: " + idResultado;
    }

    public Dados<ResultadoPossivel> fromRow(final List<String> l) {
        return new ResultadoPossivel(l);
    }

    public List<String> toRow() {
        List<String> r = new ArrayList<>();
        r.add(this.idResultado);
        r.add(String.valueOf(this.odd));
        r.add(String.valueOf(this.tipoAposta));
        r.add(this.descricao);//3 1 0 descricao odd idresultado
        r.add(String.valueOf(this.ganhou));
        r.add(this.estado);
        r.add(this.participante);
        r.add(this.idEvento);
        return r;
    }
}
