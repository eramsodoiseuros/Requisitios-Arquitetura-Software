package GUI;

import javafx.scene.Scene;

public interface IUtilizador {

    /**
     * Cria o Menu principal do Utilizador
     * @param nome Nome do Utilizador
     * @param email Email do Utilizador
     * @return  Scene representante do Menu
     * */
    Scene painelUtilizador(String nome, String email);

    /**
     * Cria uma Janela onde se inserem os dados para registar um Utilizador no sistema
     * @return  Scene representante da Janela
     * */
    Scene registarUtilizador();

    /**
     * Cria uma Janela onde se podem consultar as Estatisticas das Apostas do Utilizador
     * @return  Scene representante do Janela
     * */
    Scene painelEstatisticasUtilizador();

    /**
     * Cria uma Janela onde o Utilizador tem acesso à configuração dos seus dados
     * @return  Scene representante do Janela
     * */
    Scene painelConfigUtilizador();

    /**
     * Cria uma Janela onde o Utilizador pode alterar o seu username
     * @return  Scene representante do Janela
     * */
    Scene mudarUsername();

    /**
     * Cria uma Janela onde o Utilizador pode alterar o seu email
     * @return  Scene representante do Janela
     * */
    Scene mudarEmail();

    /**
     * Cria uma Janela onde o Utilizador pode alterar a sua password
     * @return  Scene representante do Janela
     * */
    Scene mudarPassword();

    /**
     * Cria uma Janela onde o Utilizador pode adicionar ou levantar saldo
     * @return  Scene representante do Janela
     * */
    Scene painelSaldoUtilizador();

    /**
     * Cria uma Janela onde o Utilizador pode consultar a Carteira
     * @return  Scene representante do Janela
     * */
    Scene painelCarteiraUtilizador();

    /**
     * Cria uma Janela onde o Utilizador pode consultar os Desportos
     * @return  Scene representante do Janela
     * */
    Scene painelDesportoUtilizador();

    /**
     * Cria uma Janela onde o Utilizador pode consultar os Eventos Ativos
     * @return  Scene representante do Janela
     * */
    Scene painelEventosUtilizador();

    /**
     * Cria uma Janela onde o Utilizador pode consultar o seu Historico
     * @return  Scene representante do Janela
     * */
    Scene painelHistoricoUtilizador();
}
