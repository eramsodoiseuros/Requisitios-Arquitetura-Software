package BL;

import Entidades.Evento;

import java.time.LocalDateTime;
import java.util.List;


public interface IGestorFacade {
    /**
     * Atualiza a odd de um resultado
     * @param idResultado id do resultado possivel
     * @param odd odd nova
     * */
    void atualizarOdds(String idResultado, Float odd);

    /**
     * Atualiza todas as apostas de um evento de um certo tipo
     * @param idEvento id do resultado possivel
     * @param tipoAposta tipo da aposta a ser alterada
     * @param estado Novo estado das apostas.
     * */
    void atualizarEstadoApostas(String idEvento,String tipoAposta, String estado);

    /**
     *  Metodo que
     * @param
     * @param
     * */
    void atualizarResultadoEvento(String idEvento, String resultado);

    /**
     * Indica se um resultado possivel se cumpriu ou nao
     * @param idResultado id do resultado possivel
     * @param ganhou valor booleano que indica se o resultado possivel se cumpriu ou nao
     * */
    void atualizarResultadoPossivel(String idResultado, boolean ganhou);

    /**
     * Finaliza o evento, pondo todos os resultados possiveis com o estado "fechado"
     * @param idEvento id do evento a fechar
     * @param estado estado final.
     * */
    void finalizarEvento(String idEvento, String estado);

    void abrirEvento(String idEvento, String estado);

    /**
     *  Metodo que
     * @return
     * */
    List<Evento> listarEventos();

    /**
     *  Metodo que
     * @param
     * @return
     * */
    Evento getEvento(String idEvento);

    /**
     *  Metodo que
     * @param
     * */
    void adicionarEvento(Evento e,List<String> participantes);
}
