
package BL;

import DAO.ApostaDAO;
import DAO.ApostadorDAO;
import DAO.MoedaDAO;
import DAO.ResultadoPossivelDAO;
import Encriptacao.Encriptacao;
import Entidades.*;
import Exception.IsNullException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GestaoApostador implements IApostadorFacade {

    public boolean registo(String email, String nome, String user, String pwd, String data) {
        boolean isOK = false;
        String id = email + nome + user;
        String codId = Encriptacao.encrypt(id);

        if(ApostadorDAO.getInstance().searchEmail(email).isEmpty()){
            isOK = true;
            try {
                ApostadorDAO.getInstance().put(new Apostador(email, nome, pwd, user, codId, new SimpleDateFormat("dd/MM/yyyy").parse(data)),codId);
            } catch (ParseException e) {
                System.out.println("Erro ao inserir a data: {" + e + "}.");
            }
        }
        return isOK;
    }

    @Override
    public boolean validaLogin(String email, String password) {
        List<Apostador> apostadores = ApostadorDAO.getInstance().searchEmail(email);
        boolean isOK = false;
        for (Apostador a:apostadores) {
            if (a.getPassword().equals(password))
                return true;
        };
        return isOK;
    }

    @Override
    public boolean validaRegisto(String email) {
        return ApostadorDAO.getInstance().searchEmail(email).isEmpty();
    }

    @Override
    //para remover
    public void logout() {

    }

    @Override
    public void registaAposta(float quantia, float oddFixa, String idResultado, String idAposta, String idA,String moeda) {
        //remover saldo
        ResultadoPossivel rp = ResultadoPossivelDAO.getInstance().get(idResultado);
        if (rp != null && rp.getEstado().equals("aberto")) {
            Aposta aposta = new Aposta(quantia, oddFixa, idResultado, ApostaDAO.getInstance().generateUniqueId(), idA, moeda);
            ApostaDAO.getInstance().put(aposta);
        }

    }

    @Override
    public List<Aposta> consultarHistorico(String idApostador) {
        List<Aposta> apostas = new ArrayList<>(ApostaDAO.getInstance().searchApostador(idApostador));
        return apostas;
    }

    @Override
    public void atualizaConfiguracoesConta(String idApostador, String username,String email, String password) {
        Apostador a = ApostadorDAO.getInstance().get(idApostador);
        a.setUsername(username);
        String password_ = String.valueOf(Encriptacao.encrypt(password));
        a.setPassword(password_);
        //this.apostadores.remove(idApostador);
        ApostadorDAO.getInstance().put(a);

    }

    /**
     * Pede um apostador com base no email. Note-se que a funcao search devolve um set mas so se dá return de 1 apostador por que snao podem haver 2 emails identicos.
     * @param email email do utilizador a ser validado
     * */
    public Apostador getApostador(String email) throws IsNullException{
        List<Apostador> apostadores = ApostadorDAO.getInstance().searchEmail(email);
        if(apostadores.isEmpty()) throw new IsNullException();
        return apostadores.get(0);
    }


    @Override
    public void transferirSaldo(String idApostador, float quantia, String idMoeda) {
        Apostador a = ApostadorDAO.getInstance().get(idApostador);
        Moeda m = MoedaDAO.getInstance().get(idMoeda);
        Float valor = a.getSaldo().getCarteira().get(m);
        if (valor == null)
            valor = 0.0f;
        ApostadorDAO.getInstance().updateCarteira2(idApostador,idMoeda,quantia);
    }

    public static void transferenciaSaldo(String idApostador, float quantia, String idMoeda) {
        /*Apostador a = ApostadorDAO.getInstance().get(idApostador);
        Moeda m = MoedaDAO.getInstance().get(idMoeda);
        Float valor = a.getSaldo().getCarteira().get(m);
        if (valor == null)
            valor = 0.0f;
        ApostadorDAO.getInstance().updateCarteira(idApostador,idMoeda,valor+quantia);*/
        ApostadorDAO.getInstance().updateCarteira2(idApostador,idMoeda,quantia);
    }

    @Override
    //definir o que sao estatisticas da conta.
    public void consultarEstatisticas(String idApostador) {
    }

    @Override
    public void mudarUsername(String email, String new_username, String pwd) {
        try {
            Apostador a = ApostadorDAO.getInstance().get(this.getApostador(email).getIdApostador());
            if (a.getPassword().equals(pwd)){
                a.setUsername(new_username);
                ApostadorDAO.getInstance().put(a,a.getIdApostador());
            }
        } catch (IsNullException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mudarEmail(String email, String new_email, String pwd) {
        try {
            Apostador a = ApostadorDAO.getInstance().get(this.getApostador(email).getIdApostador());
            if (a.getPassword().equals(pwd) && ApostadorDAO.getInstance().searchEmail(new_email).isEmpty()){
                a.setEmail(new_email);
                ApostadorDAO.getInstance().put(a,a.getIdApostador());
            }
        } catch (IsNullException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mudarPassword(String email, String pwd1, String pwd) {
        try {
            Apostador a = ApostadorDAO.getInstance().get(this.getApostador(email).getIdApostador());
            if (a.getPassword().equals(pwd)){
                a.setPassword(pwd1);
                ApostadorDAO.getInstance().put(a,a.getIdApostador());
            }
        } catch (IsNullException e) {
            e.printStackTrace();
        }
    }
}
