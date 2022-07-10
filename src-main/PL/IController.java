package PL;

import Exception.*;
import GUI.ApostadorObserver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IController {

    ApostadorObserver iniitObserver(String email);

    /**
     *  BACKUP
     * @return Lista de Eventos
     * */
    List<String> getEventos();

    /**
     *  Metodo que Lista todos os Resultados possiveis no formato "id - descricao - estado"
     * @param idEvento o Evento de onde se retiram os resultados possiveis
     * @return Lista formatada
     * */
    List<String> getResultadosPossiveis (String idEvento);

    /**
     *  Metodo que retorna as Apostas no formato "id - nome - odd - resultado"
     * @param emailApostador o Apostador
     * @return Lista formatada
     * */
    List<String> getApostas(String emailApostador);

    /**
     *  Metodo que permite Atualizar a odd de um Resultado Possivel
     * @param idResultado String formatada "id - descricao - estado"
     * @param odd String representante da odd
     * @return se a odd for um float retorna true
     * */
    boolean atualizarOdd(String idResultado, String odd);

    /**
     *  Metodo que permite registar uma Aposta no sistema
     * @param emailApostador email do Apostador
     * @param quantia quantia a apostar
     * @param idResultado id do Resultado Possivel
     * @param moeda id da Moeda
     * */
    void registarAposta(String emailApostador, Float quantia, String idResultado, String moeda);

    /**
     *  Metodo que permite finalizar um evento
     * @param idEvento String formatada "id - nome - estado"
     * */
    void finalizarEvento(String idEvento);

    void finalizarEvento_(String idEvento, String estado);

    /**
     * Metodo que permite consultar estatisticas de um Utilizador
     * @param idApostador id do Apostador
     * @return Lista de estatisticas
     */
    List<String> consultarEstatisticas(String idApostador);

    /**
     *  Metodo que retorna uma lista com o Resultado dos Participantes do evento
     * @return Lista de Strings formatada com "participante - pontuação"
     * */
    List<String> getResultadoEvento(String idEvento);

    /**
     *  Metodo que permite validar o registo de um Utilizador
     * @param email Email do Utilizador
     * @param nome  Nome do Utilizador
     * @param user Username do Utilizador
     * @param pwd Password do Utilizador
     * @param data Data de Nascimento
     * @return retorna true caso o email não esteja a ser usado
     * */
    boolean validaRegisto(String email, String nome, String user, String pwd, String data);

    /**
     *  Metodo que verifica as credenciais de Log In
     * @param email Email do Utilizador
     * @param password Password do Utilizador
     * @return retorna true caso as credenciais sejam validas
     * */
    boolean verificarLoginApostador(String email, String password);

    /**
     *  Metodo que permite depositar saldo na conta de um Utilizador
     * @param emailApostador email do Utilizador
     * @param quantia quantia a depositar
     * @param moeda moeda em que a quantia vai ser depositada
     * @return return true se ...
     * */
    boolean transferirSaldo(String emailApostador, Float quantia, String moeda);

    /**
     *  Metodo que permite realizar levantamentos de saldo
     * @param idApostador email do Utilizador
     * @param quantia quantia a levantar
     * @param moeda moeda a levantar
     * @return return true se ...
     * */
    boolean levantarSaldo(String idApostador, Float quantia, String moeda);

    /**
     *  Metodo que valida as credenciais do Gestor
     * @param email Email do Gestor
     * @param password Password do Gestor
     * @return true se as credenciais forem Validas
     * */
    boolean verificarLoginGestor(String email, String password);

    /**
     *  Metodo que permite adicionar um Evento ao sistema
     * @param nome Nome do evento
     * @param participantes lista de participantes
     * @param data data do evento
     * @param estado estado do evento
     * @param resultadosPossiveis resultados possiveis
     * */
    void adicionarEvento(String nome, List<String> participantes, LocalDateTime data, String desporto, String estado, List<String> resultadosPossiveis);

    /**
     *  Metodo que adiciona um Resultado Possivel ao sistema
     * @param descricao Descriçao do Resultado
     * @param odd odd do Resultado
     * @param tipoAposta tipo de Aposta
     * @param evento String formatada "id - Nome - estado"
     * @param participante o resultado implica este participante
     * @return return true se ...
     * */
    boolean adicionarResultadoPossivel(String descricao, float odd, String tipoAposta, String evento,String participante);

    /**
     *  Metodo que permite atualizar o estado do evento
     * @param idEvento id do evento
     * @param novo_estado novo estado
     * */
    void atualizarEvento(String idEvento, String novo_estado);

    /**
     *  Metodo que
     * */
    void atualizarResultadoEvento(String idEvento, String participante, int pontuacao);

    /**
     *  Metodo que
     * */
    void atualizarResultado(String selectedItems, String idResultado, boolean ganhou);

    /**
     *  Metodo que retorna o nome de um apostador atraves do email
     * @param email Email do Utilizador
     * @throws IsNullException se não existir nenhum Apostador com esse email
     * @return Nome
     * */
    String getNomeApostador(String email) throws IsNullException;

    /**
     *  Metodo que verfica se uma data tem mais de 18 anos
     * @param data data a validar
     * @return return true se sim
     * */
    boolean verificaData(String data);

    /**
     *  Metodo que verfica se uma data é postrior à atual
     * @param data data a validar
     * @return return true se for postrior
     * */
    boolean validaData(String data);

    /**
     *  Metodo que valida o input dado
     * @param data data a validar
     * @return return true se for valido
     * */
    boolean validaDateTime(String data);

    /**
     *  Metodo que verifica se uma Moeda já existe no sistema
     * @param nome Nome da moeda
     * @param token token representativo da Moeda
     * @param ratio ratio da moeda em relação ao euro
     * @param imposto imposto sobre o cambio
     * @return return true se a moeda nao existe
     * */
    boolean validarMoeda(String nome, String token, Float ratio, Float imposto);

    /**
     *  Metodo que da log out
     * @param codID codigo do utilizador
     * */
    void logoutUtilizador(String codID);

    /**
     *  Metodo que permite mudar o Username de um Utilizador
     * @param codID Email do Utilizador
     * @param user novo Username
     * @param pwd password para confirmar
     * @return return true se a password for valida
     * */
    boolean mudarUsername(String codID, String user, String pwd);

    /**
     *  Metodo que permite mudar o Email de um Utilizador
     * @param codID Email do Utilizador
     * @param email novo Email
     * @param pwd password para confirmar
     * @return return true se a password for valida
     * */
    boolean mudarEmail(String codID, String email, String pwd);

    /**
     *  Metodo que permite mudar a Password de um Utilizador
     * @param codID Email do Utilizador
     * @param pwd1 nova Password
     * @param pwd password para confirmar
     * @return return true se a password for valida
     * */
    boolean mudarPassword(String codID, String pwd1, String pwd);

    /**
     *  Metodo que retorna a lista de Moedas do sistema
     * @return
     * */
    List<String> getMoedasDisponiveis();

    /**
     *  Metodo que guarda a moeda atual
     * @param moeda moeda a guardar
     * @return true se o sistema contem a moeda
     * */
    boolean setMoeda(String moeda);

    /**
     *  Metodo que guarda a moeda a converter
     * @param moeda moeda a converter
     * @return true se o sistema contem a moeda
     * */
    boolean setMoeda_from(String moeda);

    /**
     *  Metodo que guarda a moeda para conversão
     * @param moeda moeda para conversão
     * @return true se o sistema contem a moeda
     * */
    boolean setMoeda_to(String moeda);

    /**
     *  Metodo que retorna a moeda atual
     * @return a moeda
     * */
    String getMoeda();


    /*
     *  Metodo que
     * @return Lista de Eventos formatada
     * */
    List<String> getListaEventos(String formatter);

    /*
     *  Metodo que
     * @return Lista de Eventos formatada
     * */
    List<String> getListaEventosAtivos(String formatter);

    /**
     *  Metodo que permite a criação de eventos
     * @param nome Nome do Evento
     * @param data Data do Evento
     * @param equipa1 Equipa participante 1
     * @param equipa2 Equipa participante 2
     * @param desporto   */
    void adicionarEvento(String nome, String data, String equipa1, String equipa2, String desporto);

    /**
     *  Metodo que permite adicionar Participantes
     * @param participante a adicionar
     * */
    void adicionarParticipante(String participante);

    /**
     *  Metodo que cancela um Evento
     * @param s String formatada relativa ao Evento "id - nome - estado"
     * */
    void cancelarEvento(String s);

    /**
     *  Metodo que suspende um Evento
     * @param s String formatada relativa ao Evento "id - nome - estado"
     * */
    void supenderEvento(String s);

    /**
     *  Metodo que ativa um Evento suspenso
     * @param s String formatada relativa ao Evento "id - nome - estado"
     * */
    void ativarEvento(String s);

    /**
     *  Metodo que retorna uma string formatada do saldo do Utilizador
     * @param email Email do Utilizador
     * @return lista formatada
     * */
    List<String> getSaldo(String email);

    /**
     *  Metodo que retorna um mapa com a carteira do Utilizador
     * @param email Email do Utilizador
     * @return Mapa<Nome da Moeda,Valor>
     * */
    Map<String, Float> getCarteira(String email);

    /**
     *  Metodo que retorna as Moedas de um utilizador
     * @param email Email do Utilizador
     * @return lista das moedas
     * */
    List<String> getMoedas(String email);

    /**
     *  Metodo que devolve o token de uma Moeda
     * @param moeda nome da moeda
     * @return token
     * */
    String getToken(String moeda);

    /**
     *  Metodo que permite a conversao de uma moeda
     * @param email Email do Utilizador
     * @param quantia quantia a converter
     * @return se a conversao pode ser efetuada
     * */
    boolean conversao(String email, Float quantia);

    /**
     *  Metodo que lista os Desportos existentes no sistema
     * @return Lista de Desportos
     * */
    List<String> getDesportos();

    /**
     *  Metodo que retorna uma lista de Eventos de um Desporto
     * @param desporto Desporto
     * @return Lista de Eventos
     * */
    List<String> getEventobyDesporto(String desporto);

    /**
     *  Metodo que permite mudar o nome de um Evento
     * @param s String formatada "id - Nome - Evento"
     * @param nome String com o novo Nome do Evento
     * */
    void mudarNomeEvento(String s, String nome);

    /**
     *  Metodo que permite mudar a data de um Evento
     * @param s String formatada "id - Nome - Estado"
     * @param data String data ainda por validar
     * @return true se a data for valida
     * */
    boolean mudarDataEvento(String s, String data);

    List<String> getMoedasSaldo(String email);
}
