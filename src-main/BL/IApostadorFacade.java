package BL;

import Entidades.Aposta;
import Entidades.Apostador;
import Exception.IsNullException;

import java.util.List;

public interface IApostadorFacade {

    /**
     *  Metodo que
     * @param
     * @return
     * */
    boolean registo(String email, String nome, String user, String pwd, String data);

    /**
     *  Metodo que
     * @param
     * @return
     * */
    boolean validaLogin(String email, String password);

    /**
     *  Metodo que
     * @param
     * @return
     * */
    boolean validaRegisto(String email);

    /**
     *  Metodo que
     * @param
     * */
    void logout();

    /**
     *  Metodo que
     * @param
     * @return
     * */
    Apostador getApostador(String email) throws IsNullException;

    /**
     *  Metodo que
     * @param
     * */
    void registaAposta(float quantia, float oddFixa, String idEvento, String idAposta, String idA,String moeda);

    /**
     *  Metodo que
     * @param
     * */
    List<Aposta> consultarHistorico(String idApostador);

    /**
     *  Metodo que
     * @param
     * */
    void atualizaConfiguracoesConta(String idApostador, String username, String email, String password);

    /**
     *  Metodo que
     * @param
     * */
    void transferirSaldo(String idApostador, float quantia, String idMoeda);

    /**
     *  Metodo que
     * @param
     * */
    void consultarEstatisticas(String idApostador);

    /**
     *  Metodo que muda o Username de um Utilizador na Base de Dados
     * @param email do Utilizador
     * @param new_username do Utilizador
     * @param pwd do Utilizador
     * */
    void mudarUsername(String email,String new_username, String pwd);

    /**
     *  Metodo que muda o email de um Utilizador na Base de Dados
     * @param email do Utilizador
     * @param new_email do Utilizador
     * @param pwd do Utilizador
     * */
    void mudarEmail(String email, String new_email, String pwd);

    /**
     *  Metodo que muda a password de um Utilizador na Base de Dados
     * @param email do Utilizador
     * @param pwd1 nova password do Utilizador
     * @param pwd do Utilizador
     * */
    void mudarPassword(String email, String pwd1, String pwd);
}
