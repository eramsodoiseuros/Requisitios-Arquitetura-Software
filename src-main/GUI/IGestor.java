package GUI;

import javafx.scene.Scene;

public interface IGestor {
    /**
     * Cria o Menu principal do Gestor
     * @return  Scene representante do Menu
     * */
    Scene painelGestor();

    /**
     * Cria uma Janela onde se adicionam Moedas novas ao sistema
     * @return  Scene representante da Janela
     * */
    Scene painelMoeda();

    /**
     * Cria uma Janela onde se adicionam Eventos novos ao sistema
     * @return  Scene representante da Janela
     * */
    Scene painelAdicionarEvento();

    /**
     * Cria uma Janela onde se visualizam os Eventos ativos
     * @return  Scene representante da Janela
     * */
    Scene painelEventosAtivos();

    /**
     * Cria uma Janela onde se visualizam os Eventos presentes no sistema
     * @return  Scene representante da Janela
     * */
    Scene painelEventos();

    /**
     * Cria uma Janela onde se permite alterar as configurações de um Evento
     * @return  Scene representante da Janela
     * */
    Scene painelEditarEvento();

    /**
     * Cria uma Janela onde se pode adicionar uma Aposta ao Evento
     * @return  Scene representante da Janela
     * */
    Scene painelAdicionarAposta();
}
