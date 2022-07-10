package Entidades;

import DAO.Dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Moeda implements Dados<Moeda> {
    private String nome;
    private String token;
    private float ratio;
    private float imposto;

    public Moeda(String nome, String token, float ratio, float imposto) {
        this.nome = nome;
        this.token = token;
        this.ratio = ratio;
        this.imposto = imposto;
    }

    public Moeda(){
        this.nome = "Euro";
        this.token = "€";
        this.ratio = 1;
        this.imposto = (float) 0.0005;
    }

    public Moeda(List<String> l) {
        this.nome = l.get(0);
        this.token = l.get(1);
        this.ratio = Float.parseFloat(l.get(2));
        this.imposto = Float.parseFloat(l.get(3));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public float getImposto() {
        return imposto;
    }

    public void setImposto(float imposto) {
        this.imposto = imposto;
    }

    public String toString() {
        return "Moeda{"+ nome + '\'' + token + '\'' +
                ", ratio=" + ratio +
                ", imposto=" + imposto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moeda moeda = (Moeda) o;
        return nome.equals(moeda.nome) && token.equals(moeda.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, token);
    }

    public Dados<Moeda> fromRow(final List<String> l) {
        return new Moeda(l);
    }

    public List<String> toRow() {
        List<String> r = new ArrayList<>();
        r.add(this.nome);
        r.add(this.token);
        r.add(String.valueOf(this.ratio));
        r.add(String.valueOf(this.imposto));
        return r;
    }

    public float converterTo(Moeda m, float valor){
        return (this.ratio * valor * (1-imposto)) * m.ratio;
    }

    public float ratioConversao(Moeda m){
        return this.ratio * (1-imposto) * m.ratio;
    }
}
