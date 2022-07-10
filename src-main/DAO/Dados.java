package DAO;

import java.util.List;

public interface Dados<K> {

    /**
     *  Metodo que cria instancias de classes apartir de uma tabela
     * @return classe criada para inserir no programa
     * */
    Dados<K> fromRow(List<String> row);

    /**
     *  Metodo que cria uma tabela apartir de uma classe
     * @return tabela para inserir na base de dados
     * */
    List<String> toRow();
}