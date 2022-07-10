package BL;

import Entidades.Evento;

import java.util.List;

public interface IEventoFacade {

    /**
     *  Metodo que
     * @return
     * */
    List<Evento> listarEventos();

    /**
     *  Metodo que
     * @param
     * */
    void putEvento(Evento e, List<String> participantes,List<String> resultadosPossiveis);
}
