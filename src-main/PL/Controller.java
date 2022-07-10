package PL;

import BL.*;
import Entidades.*;
import GUI.ApostadorObserver;
import Exception.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller implements IController{

    public IRasBetFacade rasbet;
    public ApostadorObserver ao;
    private String moeda;
    private String moeda_from;
    private String moeda_to;

    //id do apostador que fez login . -1 qd nao fez.
    public String apostador_sessao;
    //id do gestor que fez login . -1 qd nao fez.
    public String gestor_sessao;

    public Controller() {
        this.rasbet = new RasBetModel();
        this.apostador_sessao = "-1";
        this.gestor_sessao = "-1";
        this.moeda = "Euro";
    }

    @Override
    public ApostadorObserver iniitObserver(String email) {
        try {
            return new ApostadorObserver(this.rasbet.getApostador(email));
        } catch (IsNullException e) {
            e.printStackTrace();
        } return new ApostadorObserver();
    }

    @Override
    public List<String> getEventos() {
        return this.rasbet.listarEventos().stream().map(Evento::toString).collect(Collectors.toList());
    }

    @Override
    //<Nome - Estado-Id>
    public List<String> getResultadosPossiveis(String e) {
        String idEvento = e.split(" - ")[0];
        List<String> ret = this.rasbet.listarResultadosPossiveis(idEvento.substring(1))
                .stream().filter(y -> y.getEstado().equals("aberto"))
                .map(y ->y.getIdResultado()+ " - " + y.getDescricao() + " - " + y.getEstado() + " - " + y.getOdd()).collect(Collectors.toList());
        return ret;
    }

    public List<String> getResultadoPossivel(String e) {
        String id = e.split(" - ")[0];
        List<String> rp = this.rasbet.getRP(id.substring(1)).toRow();
        return rp;
    }

    @Override
    public List<String> getApostas(String emailApostador) {
        return this.rasbet.listarApostas(emailApostador);
    }

    @Override
    public boolean atualizarOdd(String idResultado, String odd) {
        boolean r;
        try {
            System.out.println(idResultado);
            Float f = Float.parseFloat(odd);
            this.rasbet.atualizarOdd(idResultado, f);
            r = true;
            System.out.println("hello");
        } catch (Exception e){
            r = false;
            System.out.println(e);
        }
        return r;
    }

    @Override
    public void registarAposta(String emailApostador, Float quantia, String idResultado, String moeda) {
        this.rasbet.registaAposta(emailApostador,idResultado,quantia, this.moeda);
    }

    @Override
    public void finalizarEvento(String idEvento) {

    }

    @Override
    public void finalizarEvento_(String idEvento, String estado) {
        this.rasbet.finalizarEvento(idEvento,"");
    }

    @Override
    public List<String> consultarEstatisticas(String idApostador) {
        try {
            return this.rasbet.consultarEstatisticas(idApostador);
        } catch (IsNullException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> getResultadoEvento(String idEvento) {
        return this.rasbet.getResultadoEvento(idEvento)
                .stream().map(y -> y.getIdParticipante() + ": " + y.getPontuacao())
                .collect(Collectors.toList());
    }

    @Override
    public boolean verificaData(String data){
        boolean isOK = false;
        try {
            Date start = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            Date stop = new Date();

            long diffInMillies = Math.abs(stop.getTime() - start.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(diff >= 6575){
                isOK = true;
            }
        } catch (Exception e){
            System.out.println("Erro ao inserir a data: {" + e + "}.");
        }
        return isOK;
    }

    @Override
    public boolean validaData(String data) {
        boolean isOK = false;
        try {
            Date inserida = new SimpleDateFormat("HH.mm.dd.MM.yyyy").parse(data);
            Date agora = new Date();
            if(inserida.after(agora)){
                isOK = true;
            }
        } catch (Exception e){
            System.out.println("Erro ao inserir a data: {" + e + "}.");
        }
        return isOK;
    }

    @Override
    public boolean validaDateTime(String data) {
        boolean isOK = false;
        try {
            LocalDateTime inserida = LocalDateTime.parse(data,DateTimeFormatter.ofPattern("HH.mm.dd.MM.yyyy"));
            LocalDateTime agora = LocalDateTime.now();
            if(inserida.isAfter(agora)){
                isOK = true;
            }
        } catch (Exception e){
            System.out.println("Erro ao inserir a data: {" + e + "}.");
        }
        return isOK;
    }

    @Override
    public boolean validarMoeda(String nome, String token, Float ratio, Float imposto) {
        return rasbet.validarMoeda(nome, token, ratio, imposto);
    }

    @Override
    public void logoutUtilizador(String codID) {
        this.rasbet.logout(codID);
    }

    @Override
    public boolean mudarUsername(String codID, String user, String pwd) {
        boolean isOk = false;
        if(this.rasbet.login(codID, pwd)){
            this.rasbet.mudarUsername(codID, user, pwd);
            isOk = true;
        }
        return isOk;
    }

    @Override
    public boolean mudarEmail(String codID, String email, String pwd) {
        boolean isOk = false;
        if(this.rasbet.login(codID, pwd)){
            this.rasbet.mudarEmail(codID, email, pwd);
            isOk = true;
        }
        return isOk;
    }

    @Override
    public boolean mudarPassword(String codID, String pwd1, String pwd) {
        boolean isOk = false;
        if(this.rasbet.login(codID, pwd)){
            this.rasbet.mudarPassword(codID, pwd1, pwd);
            isOk = true;
        }
        return isOk;
    }

    @Override
    public List<String> getMoedasDisponiveis() {
        return this.rasbet.getMoedas().stream().map(Moeda::getNome).collect(Collectors.toList());
    }

    @Override
    public boolean setMoeda(String moeda) {
        if(rasbet.containsMoeda(moeda)){
            this.moeda = moeda;
            return true;
        }
        return false;
    }

    @Override
    public boolean setMoeda_from(String moeda) {
        if(rasbet.containsMoeda(moeda)){
            this.moeda_from = moeda;
            return true;
        }
        return false;
    }

    @Override
    public boolean setMoeda_to(String moeda) {
        if(rasbet.containsMoeda(moeda)){
            this.moeda_to = moeda;
            return true;
        }
        return false;
    }

    @Override
    public String getMoeda(){
        return this.moeda;
    }

    private Function<Evento,String> eventFormatter(String choice){
        if (choice.equals("id_nome_estado"))
            return (y -> y.getIdEvento() + " - " +y.getNome() + " - "+y.getEstado());
        if (choice.equals("id"))
            return (Evento::getIdEvento);
        return null;
    }

    @Override
    public List<String> getListaEventos(String formatter) {
        return this.rasbet.listarEventos().stream().map(Objects.requireNonNull(eventFormatter(formatter))).collect(Collectors.toList());
    }

    @Override
    public List<String> getListaEventosAtivos(String formatter) {
        return this.rasbet.listarEventos().stream().filter(y -> y.getEstado().equals("aberto")).map(Objects.requireNonNull(eventFormatter(formatter))).collect(Collectors.toList());
    }

    @Override
    public void adicionarEvento(String nome, String data, String equipa1, String equipa2, String desporto) {
        List<String> participantes = new ArrayList<>();
        participantes.add(equipa1);
        participantes.add(equipa2);
        if(validaDateTime(data)) rasbet.adicionarEvento(nome,participantes,LocalDateTime.parse(data, DateTimeFormatter.ofPattern("HH.mm.dd.MM.yyyy")),desporto,"aberto");
    }

    @Override
    public void adicionarParticipante(String participante) {
        this.rasbet.addParticipante(participante);
    }

    @Override
    public void cancelarEvento(String s) {
        String []eventParams = s.split(" - ");
        this.rasbet.finalizarEvento(eventParams[0].substring(1),"fechado");
    }

    @Override
    public void supenderEvento(String s) {
        String []eventParams = s.split(" - ");
        this.rasbet.finalizarEvento(eventParams[0].substring(1),"suspenso");
    }

    @Override
    public void ativarEvento(String s) {
        String []eventParams = s.split(" - ");
        this.atualizarEvento(eventParams[0].substring(1),"aberto");
    }

    @Override
    public List<String> getSaldo(String email) {
        List<String> res = new ArrayList<>();
        try {
            rasbet.getApostador(email).getSaldo().getCarteira().forEach((k, v) -> res.add(k.getNome() + " " + k.getToken() + " : " + v));
            final Float[] total = {0.0f};
            rasbet.getApostador(email).getSaldo().getCarteira().forEach((k, v) -> total[0] += k.getRatio() * v);
            res.add("Total :" + total[0]);

        } catch (IsNullException e) {
            e.printStackTrace();
        }
        return res;
    }

    public Map<String, Float> getCarteira(String email) {
       return this.rasbet.getCarteira(email);
    }

    @Override
    public List<String> getMoedas(String email) {
        return this.rasbet.getMoedas(email).stream().map(Moeda::getNome).toList();
    }

    @Override
    public List<String> getMoedasSaldo(String email) {
        List<String> formatada = new ArrayList<>();
        Map<String,Float> moedas = this.rasbet.getCarteira(email);

        for (String s : moedas.keySet()){
            formatada.add(s + " (disponivel :" + moedas.get(s)+")" );
        }

        return formatada;
    }

    @Override
    public String getToken(String s) {
        return rasbet.getToken(s);
    }

    @Override
    public boolean conversao(String email, Float quantia) {
        boolean isOk = false;
        float nova_quantia = rasbet.conversao(moeda_from,moeda_to,quantia);
        if(rasbet.validaTransferencia(email,quantia,moeda_from)){
            transferirSaldo(email, -quantia, moeda_from);
            transferirSaldo(email, nova_quantia, moeda_to);
            isOk = true;
        }
        return isOk;
    }

    @Override
    public List<String> getDesportos() {
        return this.rasbet.listarEventos().stream().map(Evento::getDesporto).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getEventobyDesporto(String desporto) {
        String d = desporto.substring(1,desporto.length()-1);
        return this.rasbet.listarEventos().stream().filter(y -> y.getDesporto().equals(d))
                .map(y -> y.getIdEvento() + " - " +y.getNome() + " - " + y.getEstado()).collect(Collectors.toList());
    }

    @Override
    public void mudarNomeEvento(String s, String val) {
        String []eventParams = s.split(" - ");
        this.rasbet.mudarNomeEvento(eventParams[0],val);
    }

    @Override
    public boolean mudarDataEvento(String s, String val) {
        boolean isOk = this.validaDateTime(val);
        if (isOk){
            String []eventParams = s.split(" - ");
            this.rasbet.mudarDataEvento(eventParams[0],val);
        }
        return isOk;
    }

    @Override
    public boolean validaRegisto(String email, String nome, String user, String pwd, String data) {
        return this.rasbet.registo(email,nome,user,pwd,data);
    }

    @Override
    public boolean verificarLoginApostador(String email, String password) {
        return this.rasbet.login(email, password);
    }

    @Override
    public boolean transferirSaldo(String emailApostador, Float quantia, String moeda) {
       this.rasbet.transferirSaldo(emailApostador,quantia,moeda);
       return true;
    }

    @Override
    public boolean levantarSaldo(String emailApostador, Float quantia, String moeda) {
        boolean b = this.rasbet.validaTransferencia(emailApostador,quantia,moeda);
        if(b)
            this.rasbet.transferirSaldo(emailApostador,-quantia,moeda);
        return b;
    }

    @Override
    public boolean verificarLoginGestor(String email, String password) {
        return email.equals(password);
    }

    @Override
    public void adicionarEvento(String nome, List<String> participantes, LocalDateTime data, String desporto, String estado, List<String> resultadosPossiveis) {
        //this.rasbet.adicionarEvento(nome,participantes,data,desporto,estado,resultadosPossiveis);
    }

    @Override
    public boolean adicionarResultadoPossivel(String descricao, float odd, String tipoAposta, String evento,String participante) {
        System.out.println(evento);
        String e = evento.split(" - ")[0];
        return this.rasbet.addResultadoPossivel(descricao,false,odd,"aberto","",tipoAposta, e.substring(1) ,participante);
    }

    @Override
    public void atualizarEvento(String idEvento, String novo_estado) {
         this.rasbet.atualizarEstado(idEvento,novo_estado);
    }

    public void atualizarEventoTipo(String idEvento, String novo_estado, String tipoAposta) {
        this.rasbet.atualizarEstadoApostas(idEvento,novo_estado,tipoAposta);
    }

    @Override
    public void atualizarResultadoEvento(String idEvento, String participante, int pontuacao) {
        this.rasbet.alterarResultadoEvento(participante,idEvento,pontuacao);
    }

    @Override
    public void atualizarResultado(String evento, String idResultado, boolean ganhou) {
        String id = evento.split(" - ")[0];
        this.rasbet.atualizarResultadoPossivel(idResultado,ganhou);
        this.rasbet.finalizarEvento(id.substring(1),"fechado");
    }

    @Override
    public String getNomeApostador(String email) throws IsNullException {
        return rasbet.getApostador(email).getUsername();
    }
}
