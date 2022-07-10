package BL;

import Entidades.*;
import Exception.IsNullException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IRasBetFacade {

    /**
     *  Metodo que
     * @param
     * */
    boolean registo(String email, String nome, String user, String pwd, String data);

    /**
     *  Metodo que
     * @param
     * */
    void logout(String idApostador);

    /**
     *  Metodo que
     * @param
     * */
    boolean login(String email, String password);

    /**
     *  Metodo que
     * @param
     * */
    List<Evento> listarEventos();

    /**
     *  Metodo que
     * @param
     * */
    List<ResultadoPossivel> listarResultadosPossiveis(String idEvento);

    /**
     *  Metodo que
     * @param
     * @return*/
    List<String> listarApostas(String emailApostador);

    /**
     *  Metodo que
     * @param
     * */
    List<Participante> getResultadoEvento(String idEvento);

    /**
     *  Metodo que
     * @param
     * */
    public List<String> getResultadoPossivel(String descricao);

    /**
     * Pede um apostador com base no email. Note-se que a funcao search devolve um set mas so se dá return de 1 apostador por que snao podem haver 2 emails identicos.
     * @param email email do utilizador a ser validado
     * */
    Apostador getApostador(String email) throws IsNullException;


    /**
     *  Metodo que
     * @return
     * */
    void transferirSaldo(String emailApostador, float quantia, String idMoeda);

    /**
     * Valida se uma moeda pode ou não ser inserida no sistema
     * @param nome nome da moeda
     * @param token token representativo da moeda
     * @param imposto imposto aplicado ao cambio desta moeda
     * @param ratio ratio da moeda em relação ao euro
     * @return Retorna se a moeda pode ou não ser inserida no sistema.
     * */
    boolean validarMoeda(String nome, String token, Float ratio, Float imposto);


    /**
     * Devolve as moedas que um utilisador pode usar
     * @param email email do utilisador
     * @return moedas que um utilisador pode usar
     */
    List<Moeda> getMoedas(String email);


    /**
     * Finaliza o evento, pondo todos os resultados possiveis com o estado "fechado"
     * @param idEvento id do evento a fechar
     * @param estado estado final
     * */
    void finalizarEvento(String idEvento, String estado);

    /**
     *  Metodo que
     * @return
     * */
    boolean validaTransferencia(String email, Float quantia, String moeda);

    String getToken(String moeda);

    float conversao(String moeda_from, String moeda_to, float quantia);

    /**
     *  Metodo que
     * @param
     * */
    boolean registaAposta(String emailApostador, String idResultado, float quantia, String moeda);

    /**
     *  Metodo que
     * @param
     * */
    List<String> consultarEstatisticas(String emailApostador) throws IsNullException;

    /**
     *  Metodo que
     * @param
     * @return
     * */
    List<Aposta> consultarHistorico(String idApostador);

    List<Moeda> getMoedas();

    Map<String, Float> getCarteira(String email);

    ResultadoPossivel getRP(String id);

    /**
     * Indica se um resultado possivel se cumpriu ou nao
     * @param idResultado id do resultado possivel
     * @param ganhou valor booleano que indica se o resultado possivel se cumpriu ou nao
     * */
    void atualizarResultadoPossivel(String idResultado, boolean ganhou);

    /**
     * Atualiza a odd de um resultado
     * @param idResultado id do resultado possivel
     * @param odd odd nova
     * */
    void atualizarOdd(String idResultado, Float odd);

    /**
     * Atualiza todas as apostas de um evento de um certo tipo
     * @param eventoId id do resultado possivel
     * @param tipo tipo da aposta a ser alterada
     * @param estado Novo estado das apostas.
     * */
    void atualizarEstadoApostas(String eventoId, String estado, String tipo);

    /**
     * Altera certos parametros da conta do apostador
     * @param idApostador id do apostador
     * @param username novo username
     * @param email novo email
     * @param password nova password
     * */
    void alterarConfigConta(String idApostador,String username, String email, String password);

    /**
     *  Metodo que adiciona Resultados Possiveis na Base de Dados
     * @param
     * */
    boolean addResultadoPossivel(ResultadoPossivel p);

    /**
     *  Metodo que adiciona Resultados Possiveis na Base de Dados
     * @param descricao do Resultado
     * @param ganhou se ganhou
     * @param odd do Resultado
     * @param estado do Resultado
     * @param idResultado do Resultado
     * @param tipoAposta do Resultado
     * @param idEvento do Resultado
     * @param participante que envolve o Resultado
     * */
    boolean addResultadoPossivel(String descricao, boolean ganhou, float odd, String estado, String idResultado, String tipoAposta, String idEvento,String participante);

    /**
     *  Metodo que adiciona Participantes à Base de Dados
     * @param participante a inserir
     * */
    void addParticipante(Participante participante);

    /**
     *  Metodo que adiciona Participantes à Base de Dados
     * @param participante a inserir
     * */
    void addParticipante(String participante);

    /**
     *  Metodo que adiciona um Evento à Base de Dados
     * @param nome do Evento
     * @param participantes do Evento
     * @param data do Evento
     * @param desporto do Evento
     * @param estado do Evento
     * */
    void adicionarEvento(String nome, List<String> participantes, LocalDateTime data, String desporto, String estado);

    /**Atualizar resultado do evento-pela pontuacao dos participantes
     *
     */
    void alterarResultadoEvento(String idParticipante,String idEvento, Integer pontuacao);

    /**
     *  Metodo que
     * @param
     * */
    void addObserver(String oserverId);

    /**
     *  Metodo que
     * @param
     * */
    void removeObserver(String emailObserver);
    
    /**
     *  Metodo que verifica se a Base de Dados contêm a Moeda
     * @param moeda nome da Moeda
     * @return return true se contem
     * */
    boolean containsMoeda(String moeda);

    /**
     *  Metodo que muda o Nome de um Evento na Base de Dados
     * @param idEvento id do Evento
     * @param val novo nome do Evento
     * */
    void mudarNomeEvento(String idEvento, String val);

    /**
     *  Metodo que muda a Data de um Evento na Base de Dados
     * @param idEvento id do Evento
     * @param val data em formato String
     * */
    void mudarDataEvento(String idEvento, String val);

    void atualizarEstado(String idEvento, String novo_estado);

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
