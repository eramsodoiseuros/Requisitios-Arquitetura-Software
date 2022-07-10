package GUI;

import PL.Controller;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class Gestor implements IGestor{
    private ListView<String> listView;
    private Controller controller;

    public Gestor(Controller c){
        this.controller = c;
    }

    public Scene painelGestor() {
        VBox layout = javafx.makebox();

        listView = new ListView<>();
        listView.getItems().addAll(
                "Adicionar Evento", "Lista de Eventos Ativos", "Lista de Todos os Eventos", "Adicionar uma nova Moeda"
        );

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button b1 = new Button("Escolher.");
        b1.setOnAction(e -> escolherMenuGestor());

        Button b2 = new Button("Terminar Sessão.");
        b2.setOnAction(javafx::endScene);

        Button b3 = new Button("Atualizar.");
        b3.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Menu do Gestor", painelGestor());
        });

        layout.getChildren().addAll(listView,b1,b2,b3);
        return new Scene(layout, 400, 400);
    }

    private void escolherMenuGestor(){
        String s = String.valueOf(listView.getSelectionModel().getSelectedItems());

        if(s.equals("[Adicionar Evento]")){
            javafx.makeWindow("Adicionar Evento - Gestor", painelAdicionarEvento());
        }

        if(s.equals("[Lista de Eventos Ativos]")){
            javafx.makeWindow("Lista de Eventos Ativos", painelEventosAtivos());
        }

        if(s.equals("[Lista de Todos os Eventos]")){
            javafx.makeWindow("Lista de Todos os Eventos", painelEventos());
        }

        if(s.equals("[Adicionar uma nova Moeda]")){
            javafx.makeWindow("Painel da Moeda", painelMoeda());
        }
    }

    public Scene painelMoeda() {
        VBox layout = javafx.makebox();

        TextField nometxt = new TextField();
        Label lblnome = new Label("Nome da Moeda");

        TextField tokentxt = new TextField();
        Label lbltoken = new Label("Token da Moeda");

        TextField ratiotxt = new TextField();
        Label lblratio = new Label("Ratio da Moeda em relação ao Euro");

        TextField taxtxt = new TextField();
        Label lbltax = new Label("Imposto sobre o cambio da Moeda");

        Button b = new Button("Adicionar esta moeda ao cambio.");
        b.setOnAction(e -> {
            String nome = nometxt.getText();
            String token = tokentxt.getText();
            float ratio = (float) 0.0;
            float imposto = (float) 0.0;
            try {
                ratio = Float.parseFloat(ratiotxt.getText());
            } catch (Exception ex){
                javafx.alert("ERRO", "Precisa de inserir um valor para o ratio.");
            }
            try {
                imposto = Float.parseFloat(taxtxt.getText());
            } catch (Exception ex){
                javafx.alert("ERRO", "Precisa de inserir um valor para o imposto.");
            }

            if(nome.equals("")) javafx.alert("ERRO", "Precisa de inserir um nome para registar uma moeda.");
            if(token.equals("")) javafx.alert("ERRO", "Precisa de inserir um token para registar uma moeda.");
            else {
                if(!controller.validarMoeda(nome, token, ratio, imposto)) javafx.alert("ERRO", "Esse token já está registado.");
                javafx.endScene(e);
            }
        });

        layout.getChildren().addAll(lblnome, nometxt, lbltoken, tokentxt, lblratio, ratiotxt, lbltax, taxtxt, b);
        return new Scene(layout, 500, 400);
    }

    @Override
    public Scene painelAdicionarEvento() {
        VBox layout = javafx.makebox();

        TextField txtnome = new TextField();
        Label lblnome = new Label("Nome do Evento");

        TextField txtd = new TextField();
        Label lbld = new Label("Desporto");

        TextField txtdata = new TextField();
        Label lbldata = new Label("Data do Evento");

        TextField txtequipa1 = new TextField();
        Label lblequipa1 = new Label("Equipa 1");

        TextField txtequipa2 = new TextField();
        Label lblequipa2 = new Label("Equipa 2");

        Button b = new Button("Adicionar Evento.");
        b.setOnAction(e -> {

            String nome = txtnome.getText();
            String data = txtdata.getText();
            String equipa1 = txtequipa1.getText();
            String equipa2 = txtequipa2.getText();
            String desporto = txtd.getText();

            if(nome.equals("")) javafx.alert("ERRO", "Precisa de introduzir um nome para criar um Evento.");
            else if(data.equals("")) javafx.alert("ERRO", "Precisa de introduzir uma data para criar um Evento.");
            else if(equipa1.equals("")) javafx.alert("ERRO", "Precisa de introduzir o nome da equipa 1 para criar um Evento.");
            else if(equipa2.equals("")) javafx.alert("ERRO", "Precisa de introduzir o nome da equipa 2 para criar um Evento.");
            else if(desporto.equals("")) javafx.alert("ERRO", "Precisa de introduzir um desporto para criar um Evento.");
            else if(!controller.validaData(data)) javafx.alert("ERRO", "A data inserida é inválida.");
            else {
                controller.adicionarEvento(nome,data,equipa1,equipa2, desporto);
                javafx.endScene(e);
            }
        });

        layout.getChildren().addAll(lblnome, txtnome, lbld, txtd, lbldata, txtdata, lblequipa1, txtequipa1, lblequipa2, txtequipa2, b);
        return new Scene(layout, 500, 400);
    }

    @Override
    public Scene painelEventosAtivos() {
        VBox layout = javafx.makebox();

        listView = new ListView<>();
        listView.getItems().addAll(controller.getListaEventosAtivos("id_nome_estado"));

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button b = new Button("Cancelar Evento.");
        b.setOnAction(e -> {
            controller.cancelarEvento(String.valueOf(listView.getSelectionModel().getSelectedItems()));
        });

        Button b1 = new Button("Suspender Evento.");
        b1.setOnAction(e -> {
            controller.supenderEvento(String.valueOf(listView.getSelectionModel().getSelectedItems()));
        });

        Button b5 = new Button("Atualizar.");
        b5.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Eventos Ativos", painelEventosAtivos());
        });

        Button b2 = new Button("Adicionar uma aposta ao Evento.");
        b2.setOnAction(e -> {
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelAdicionarAposta());
        });

        Button b3 = new Button("Finalizar Evento.");
        b3.setOnAction(e -> {
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelFinalizarEvento());
        });

        Button b4 = new Button("Editar Evento.");
        b4.setOnAction(e -> {
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelEditarEvento());
        });

        layout.getChildren().addAll(listView, b, b1, b2, b3, b4, b5);
        return new Scene(layout, 500, 400);
    }

    @Override
    public Scene painelAdicionarAposta() {
        VBox layout = javafx.makebox();
        String s = String.valueOf(listView.getSelectionModel().getSelectedItems());

        TextField txtaposta = new TextField();
        Label lblaposta = new Label("Descrição da Aposta");

        TextField txttipo = new TextField();
        Label lbltipo = new Label("Tipo da Aposta");

        TextField txtodd = new TextField();
        Label lblodd = new Label("Odd da Aposta");

        TextField txtp = new TextField();
        Label lblp = new Label("Participante da Aposta");

        Button b = new Button("Adicionar aposta.");
        b.setOnAction(e -> {

            String aposta = txtaposta.getText();
            String sodd = txtodd.getText();
            String tipo = txttipo.getText();
            String participante = txtp.getText();

            try{
                if(aposta.equals("")) javafx.alert("ERRO", "Precisa de inserir uma Descrição na aposta");
                else if(sodd.equals("")) javafx.alert("ERRO", "Precisa de inserir uma Odd na aposta");
                else if(tipo.equals("")) javafx.alert("ERRO", "Precisa de inserir um tipo de aposta");
                else if(participante.equals("")) javafx.alert("ERRO", "Precisa de inserir um participante");
                else {
                    float odd = Float.parseFloat(sodd);
                    controller.adicionarResultadoPossivel(aposta, odd, tipo, s, participante);
                    javafx.endScene(e);
                }
            } catch (Exception ex){
                javafx.alert("Erro", "A odd precisa de ser um valor decimal.");
            }
        });

        layout.getChildren().addAll(lblaposta, txtaposta, lbltipo, txttipo, lblp, txtp, lblodd, txtodd, b);
        return new Scene(layout, 500, 400);
    }

    @Override
    public Scene painelEventos() {
        VBox layout = javafx.makebox();

        listView = new ListView<>();
        listView.getItems().addAll(controller.getListaEventos("id_nome_estado"));

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button b = new Button("Cancelar Evento.");
        b.setOnAction(e -> {
            controller.cancelarEvento(String.valueOf(listView.getSelectionModel().getSelectedItems()));
        });

        Button b1 = new Button("Ativar Evento.");
        b1.setOnAction(e -> {
            controller.ativarEvento(String.valueOf(listView.getSelectionModel().getSelectedItems()));
        });

        Button b5 = new Button("Atualizar.");
        b5.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Todos os Eventos", painelEventos());
        });

        Button b2 = new Button("Adicionar uma aposta ao Evento.");
        b2.setOnAction(e -> {
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelAdicionarAposta());
        });

        Button b3 = new Button("Finalizar Evento.");
        b3.setOnAction(e -> {
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelFinalizarEvento());
        });

        Button b4 = new Button("Editar Evento.");
        b4.setOnAction(e -> {
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelEditarEvento());
        });

        layout.getChildren().addAll(listView, b, b1, b2, b3, b4, b5);
        return new Scene(layout, 500, 400);
    }

    private Scene painelFinalizarEvento() {
        VBox layout = javafx.makebox();
        String evento = String.valueOf(listView.getSelectionModel().getSelectedItems());
        List<String> resultados = controller.getResultadosPossiveis(evento);

        Label lblresultado = new Label("Resultado");
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll(resultados);
        cb.setPromptText("Escolha o resultado final : ");
        cb.setOnAction(e -> {
            controller.atualizarResultado(String.valueOf(listView.getSelectionModel().getSelectedItems()), cb.getValue(), true);
        });

        Button b3 = new Button("Atualizar.");
        b3.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelFinalizarEvento());
        });

        layout.getChildren().addAll(lblresultado, cb, b3);
        return new Scene(layout, 500, 400);
    }

    @Override
    public Scene painelEditarEvento() {
        VBox layout = javafx.makebox();

        TextField valor = new TextField();
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll("Mudar nome", "Mudar data", "Alterar odd de uma Aposta");
        cb.setPromptText("Escolha a configuração : ");

        Button b = new Button("Selecionar");
        b.setOnAction(e -> {
            String s = String.valueOf(listView.getSelectionModel().getSelectedItems());
            System.out.println(s);
            System.out.println(cb.getValue());
            if(cb.getValue().equals("Mudar nome")){
                String val = valor.getText();
                if(val.equals("")) javafx.alert("Erro", "Não inseriu dados para realizar uma configuração.");
                else {
                    controller.mudarNomeEvento(s,val);
                }
            }

            if(cb.getValue().equals("Mudar data")){
                String val = valor.getText();
                if(val.equals("")) javafx.alert("Erro", "Não inseriu dados para realizar uma configuração.");
                else {
                    if(!controller.mudarDataEvento(s,val)) javafx.alert("Erro", "A data inserida é invalida.");
                }
            }

            if(cb.getValue().equals("Alterar odd de uma Aposta")){
                String val = valor.getText();
                if(val.equals("")) javafx.alert("Erro", "Não inseriu dados para realizar uma configuração.");
                else {
                    javafx.makeWindow("Alterar odd", painelAlterarResultado(val, s));
                }
            }
        });


        Button b3 = new Button("Atualizar.");
        b3.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Evento - " + listView.getSelectionModel().getSelectedItems(), painelEditarEvento());
        });

        layout.getChildren().addAll(cb, valor, b, b3);
        return new Scene(layout, 500, 400);
    }

    private Scene painelAlterarResultado(String val, String evento) {
        VBox layout = javafx.makebox();

        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll(controller.getResultadosPossiveis(evento));
        cb.setPromptText("Escolha o resultado a alterar : ");

        Button b = new Button("Alterar.");
        b.setOnAction(e -> {
            javafx.endScene(e);
            System.out.println(val);
            if(!controller.atualizarOdd(cb.getValue(), val)) javafx.alert("Erro", "A odd inserida precisa de ser um valor decimal.");
        });

        layout.getChildren().addAll(cb, b);
        return new Scene(layout, 500, 400);
    }
}
