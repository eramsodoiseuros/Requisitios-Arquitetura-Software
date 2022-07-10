package BL;

import DAO.*;
import Entidades.*;
import GUI.ApostadorObserver;
import Exception.IsNullException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RasBetModel implements IRasBetFacade {
    public IGestorFacade gestor;
    public IApostadorFacade apostadorFacade;

    public RasBetModel() {
        this.gestor = new Gestor();
        this.apostadorFacade = new GestaoApostador();
    }

    @Override
    public List<String> listarApostas(String emailApostador) {
        List<Aposta> ret = null;
        try {
            Apostador a = this.getApostador(emailApostador);
            ret = ApostaDAO.getInstance().searchApostador(a.getIdApostador());
        } catch (IsNullException e) {
            e.printStackTrace();
        }
        List<String> lista = new ArrayList<>();


        for(Aposta s : ret){
            String idR = s.getIdResultado();

            ResultadoPossivel r = ResultadoPossivelDAO.getInstance().get(idR);
            String descricao = r.getDescricao();
            String idE = r.getIdEvento();
            String evento = EventoDAO.getInstance().get(idE).getNome();
            if(r.isGanhou()) lista.add("[ id " + s.getIdAposta() +" - "+evento+" - "+ descricao + " - ganhou: " + s.getOddFixa()*s.getQuantia() + " em " + s.getIdMoeda() + "]");
            else lista.add("[ id " + s.getIdAposta() +" - "+evento+" - "+ descricao + " - perdeu: " + s.getQuantia() + " em " + s.getIdMoeda() + " com uma odd de "+s.getOddFixa()+ "]");
        }
        return  lista;
    }

    @Override
    public String getToken(String moeda){
        return MoedaDAO.getInstance().get(moeda).getToken();
    }

    @Override
    public float conversao(String moeda_from, String moeda_to, float quantia){
        Moeda m1 = MoedaDAO.getInstance().get(moeda_from);
        Moeda m2 = MoedaDAO.getInstance().get(moeda_to);

        return m1.converterTo(m2,quantia);
    }

    //feito
    //nota: GestaoApostador chama um metodo da ApostaDAO para gerar id da aposta unico
    public boolean registaAposta(String emailApostador, String idResultado, float quantia, String moeda) {
        //remover saldo da conta do utilisador
        boolean res = false;
        Apostador a = null;
        try {
            a = this.getApostador(emailApostador);
        } catch (IsNullException e) {
            e.printStackTrace();
        }
        //fazer o levantamento
        Moeda m = MoedaDAO.getInstance().get(moeda);
        ResultadoPossivel r = ResultadoPossivelDAO.getInstance().get(idResultado);
        if (a != null && m != null && r != null && r.getEstado().equals("aberto")) {
            this.apostadorFacade.transferirSaldo(a.getIdApostador(), -quantia, moeda);
            //adicionar aposta
            this.apostadorFacade.registaAposta(quantia, r.getOdd(), idResultado, "", a.getIdApostador(), moeda);
            //adicionar apostqador aos observers
            Subject.getInstance().addApostador(new ApostadorObserver(a));
            res = true;
        }
        return res;
    }

    public boolean containsMoeda(String s){
        return MoedaDAO.getInstance().containsKey(s);
    }

    @Override
    public void mudarNomeEvento(String s, String val) {
        Evento e = EventoDAO.getInstance().get(s.substring(1));
        e.setNome(val);
        EventoDAO.getInstance().put(e,e.getIdEvento());
    }

    @Override
    public void mudarDataEvento(String s, String val) {
        Evento e = EventoDAO.getInstance().get(s.substring(1));
        e.setData(LocalDateTime.parse(val));
        EventoDAO.getInstance().put(e,e.getIdEvento());
    }

    @Override
    public boolean registo(String email, String nome, String user, String pwd, String data) {
        return apostadorFacade.registo(email,nome,user,pwd,data);
    }

    @Override
    public void logout(String idApostador) {
        apostadorFacade.logout();
    }

    @Override
    public boolean login(String email, String password) {
        return apostadorFacade.validaLogin(email, password);
    }

    @Override
    //feito
    public List<Evento> listarEventos() {
        return this.gestor.listarEventos();
    }

    @Override
    public List<ResultadoPossivel> listarResultadosPossiveis(String idEvento) {
        return ResultadoPossivelDAO.getInstance().searchEvento(idEvento);
    }

    @Override
    public List<Participante> getResultadoEvento(String idEvento) {
        return ParticipanteDAO.getInstance().getResultadoEvento(idEvento);
    }

    @Override
    public List<String> getResultadoPossivel(String descricao) {
        List<ResultadoPossivel> l = ResultadoPossivelDAO.getInstance().searchDescricao(descricao);
        ResultadoPossivel p = l.get(0);
        //3 1 0 descricao odd idresultado
        List<String> res = p.toRow();
        List<String> ret = new ArrayList<>();
        ret.add("Descricao: " + res.get(3));
        ret.add("Odd: " + res.get(1));
        ret.add("Id: " + res.get(0));
        return ret;
    }

    @Override
    public Apostador getApostador(String email) throws IsNullException {
        Apostador a = this.apostadorFacade.getApostador(email);
        if (a != null) {
            Saldo s = ApostadorDAO.getInstance().getSaldo(a.getIdApostador());
            a.setSaldo(s);
        }
        return a;
    }

    @Override
    public void transferirSaldo(String emailApostador, float quantia, String idMoeda) {
        try {
            Apostador a = this.getApostador(emailApostador);
            this.apostadorFacade.transferirSaldo(a.getIdApostador(),quantia,idMoeda);
        } catch (IsNullException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validarMoeda(String nome, String token, Float ratio, Float imposto) {
        boolean isOK = false;
        if(!MoedaDAO.getInstance().containsKey(nome)){
            MoedaDAO.getInstance().put(new Moeda(nome, token, ratio, imposto));
            isOK = true;
        }
        return isOK;
    }

    @Override
    public List<Moeda> getMoedas(String email) {
        try {
            Apostador a = this.getApostador(email);
            return a.getSaldo().getCarteira().keySet().stream().collect(Collectors.toList());
        } catch (IsNullException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void finalizarEvento(String idEvento, String resultado) {
        System.out.println(idEvento);
        this.gestor.finalizarEvento(idEvento,resultado);
    }

    @Override
    public boolean validaTransferencia(String email, Float quantia, String moeda) {
        Apostador b = null;
        try {
            b = this.apostadorFacade.getApostador(email);
        } catch (IsNullException e) {
            e.printStackTrace();
        }
        boolean res = false;
        Apostador a;
        Moeda m = MoedaDAO.getInstance().get(moeda);
        if (b != null && m != null) {
            a = ApostadorDAO.getInstance().get(b.getIdApostador());
            res = a.getSaldo().isItOkay(m, quantia);
        }
        return res;
    }

    //definir
    @Override
    public List<String> consultarEstatisticas(String emailApostador) throws IsNullException {
        String idApostador = this.getApostador(emailApostador).getIdApostador();
        List<Aposta> apostas = this.consultarHistorico(idApostador);
        HashMap<String,Double> participantes = new HashMap<>();
        List<String> metricas = new ArrayList<>();
        float ganhos = 0;
        float totalApostado = 0;
        int nApostas = 0;
        for(Aposta a : apostas){
            ResultadoPossivel rp = ResultadoPossivelDAO.getInstance().get(a.getIdResultado());
            Double p = participantes.computeIfPresent(a.getIdApostador(),(k,v) -> v= v+1);
            if (p == null){
                participantes.put(a.getIdApostador(),1.0);
            }
            ganhos = ganhos + (!rp.getEstado().equals("aberto") && rp.isGanhou() ? a.getOddFixa()*a.getQuantia() : 0);
            totalApostado+= a.getQuantia();
            nApostas++;
        }
        metricas.add("Numero de apostas: " + nApostas);
        metricas.add("Total apostado: " + totalApostado );
        metricas.add("Valor recolhido em apostas: " + ganhos);
        metricas.add("Taxa de retorno: " +ganhos/totalApostado);
        return metricas;
    }

    @Override
    public List<Aposta> consultarHistorico(String idApostador) {
        return this.apostadorFacade.consultarHistorico(idApostador);
    }

    @Override
    public List<Moeda> getMoedas() {
        return MoedaDAO.getInstance().values();
    }

    @Override
    public Map<String, Float> getCarteira(String email) {
        HashMap<String,Float> res = new HashMap<>();
        try {
            Apostador a = this.getApostador(email);
            Saldo s = ApostadorDAO.getInstance().getSaldo(a.getIdApostador());
            s.getCarteira().forEach((k,v) -> res.put(k.getNome(),v));
        } catch (IsNullException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ResultadoPossivel getRP(String id) {
        return ResultadoPossivelDAO.getInstance().get(id);
    }


    public void atualizarResultadoPossivel(String idResultado, boolean ganhou) {
        this.gestor.atualizarResultadoPossivel(idResultado,ganhou);
    }

    @Override
    public void atualizarOdd(String idResultado, Float odd) {
        this.gestor.atualizarOdds(idResultado,odd);
    }


    @Override
    public void atualizarEstadoApostas(String eventoId, String estado, String tipo) {
        this.gestor.atualizarEstadoApostas(eventoId,tipo,estado);
    }

    public void atualizarEstado(String eventoId, String estado) {
        this.gestor.abrirEvento(eventoId,estado);
    }

    @Override
    public void mudarUsername(String email, String new_username, String pwd) {
        apostadorFacade.mudarUsername(email, new_username, pwd);
    }

    @Override
    public void mudarEmail(String email, String new_email, String pwd) {
        apostadorFacade.mudarEmail(email, new_email, pwd);
    }

    @Override
    public void mudarPassword(String email, String pwd1, String pwd) {
        apostadorFacade.mudarEmail(email, pwd1, pwd);
    }


    @Override
    public void alterarConfigConta(String idApostador,String username, String email,  String password) {
        this.apostadorFacade.atualizaConfiguracoesConta(idApostador,username,email,password);
    }

    @Override
    public boolean addResultadoPossivel(ResultadoPossivel p) {
        p.setIdResultado(ResultadoPossivelDAO.getInstance().generateUniqueId());
        ResultadoPossivel a = ResultadoPossivelDAO.getInstance().put(p);
        return true;
    }

    public boolean addResultadoPossivel(String descricao, boolean ganhou, float odd, String estado, String idResultado, String tipoAposta, String idEvento,String participante) {
        return this.addResultadoPossivel(new ResultadoPossivel(descricao,ganhou,odd,estado,ResultadoPossivelDAO.getInstance().generateUniqueId(), tipoAposta,idEvento,participante));
    }

    @Override
    public void addParticipante(Participante p) {
        ParticipanteDAO.getInstance().put(p);
    }

    @Override
    public void addParticipante(String participante) {
        //rank e pontuacao n interessam
        this.addParticipante(new Participante(participante,false,0));
    }

    @Override
    //ver cena de ides unicos
    public void adicionarEvento(String nome, List<String> participantes, LocalDateTime data, String desporto,String estado) {
        Evento e = new Evento(estado,desporto, EventoDAO.getInstance().generateUniqueId(), nome,data.toString());
        this.gestor.adicionarEvento(e,participantes);
    }

    @Override
    public void alterarResultadoEvento(String idParticipante, String idEvento, Integer pontuacao) {
        ParticipanteDAO.getInstance().addEventosParticipantes(idEvento,idParticipante,pontuacao);
    }

    @Override
    public void addObserver(String idObserver) {
        try {
            Apostador a = this.getApostador(idObserver);
            ApostadorObserver ao = new ApostadorObserver(a);
            Subject.getInstance().addApostador(ao);
        } catch (IsNullException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeObserver(String emailObserver) {
        try {
            Apostador a = this.getApostador(emailObserver);
            ApostadorObserver ao = new ApostadorObserver(a);
            Subject.getInstance().removeApostador(ao);
        } catch (IsNullException e) {
            e.printStackTrace();
        }
    }
}
