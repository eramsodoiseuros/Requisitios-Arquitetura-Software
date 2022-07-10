package GUI;

import PL.Controller;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class Scenes {
    private Controller controller;
    private ListView<String> listView;
    private String idEvento;
    private ApostadorObserver ao;
    private Gestor gestor;
    private Utilizador utilizador;

    public Scenes(){
        // this.ao = new ApostadorObserver();
        this.controller = new Controller();
        this.gestor = new Gestor(controller);
        this.utilizador = new Utilizador(controller);
    }

    public Scene menu() {
        VBox layout = javafx.makebox();

        listView = new ListView<>();
        listView.getItems().addAll(
                "Registar Utilizador", "Login de Utilizador", "Visualizar Apostas", "Login de Gestor"
        );

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button b1 = new Button("Escolher.");
        b1.setOnAction(e -> escolherMenu());

        Button b2 = javafx.shutdown();

        Button b3 = new Button("Atualizar.");
        b3.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Menu", menu());
        });

        layout.getChildren().addAll(listView,b1,b2,b3);
        return new Scene(layout, 400, 400);
    }

    private void escolherMenu(){
        String s = String.valueOf(listView.getSelectionModel().getSelectedItems());

        if(s.equals("[Registar Utilizador]")){
            javafx.makeWindow("Registo - RASBET", utilizador.registarUtilizador());
        }

        if(s.equals("[Login de Gestor]")){
            javafx.makeWindow("Log In - Gestor", login(true));
        }

        if(s.equals("[Login de Utilizador]")){
            javafx.makeWindow("Log In - Utilizador", login(false));
        }

        if(s.equals("[Visualizar Apostas]")){
            javafx.makeWindow("Apostas - RASBET", vizualizarApostas());
        }
    }

    public Scene login(boolean isGestor){
        VBox layout = javafx.makebox();

        TextField usertxt = new TextField();
        Label lblUser = new Label("Username");

        PasswordField passwordtxt = new PasswordField();
        Label lblPassword = new Label("Password");

        Button b = new Button("LogIn.");
        b.setOnAction(e -> {
            String user = usertxt.getText();
            String pwd = passwordtxt.getText();

            if(user.equals("")) javafx.alert("ERRO", "Precisa de inserir um Código ID para se conectar.");
            if(pwd.equals("")) javafx.alert("ERRO", "Precisa de inserir uma palavra-passe para se conectar.");
            else {
                login(user, pwd, isGestor);
                javafx.endScene(e);
            }
        });

        layout.getChildren().addAll(lblUser, usertxt, lblPassword, passwordtxt, b);
        return new Scene(layout, 500, 400);
    }

    private Scene vizualizarApostas(){
        VBox layout = javafx.makebox();

        listView = new ListView<>();
        listView.getItems().addAll(controller.getListaEventos("id_nome_estado"));

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button b1 = new Button("Escolher Evento");
        b1.setOnAction(e -> {
            String s = String.valueOf(listView.getSelectionModel().getSelectedItems());
            javafx.makeWindow("Evento - " + s, painelApostasbyEvento(s));
        });

        Button b3 = new Button("Atualizar.");
        b3.setOnAction(e -> {
            javafx.endScene(e);
            javafx.makeWindow("Apostas - RASBET", vizualizarApostas());
        });

        Button b = javafx.sair();

        layout.getChildren().addAll(listView,b1,b3,b);
        return new Scene(layout, 400, 400);
    }

    private Scene painelApostasbyEvento(String evento) {
        VBox layout = javafx.makebox();

        listView = new ListView<>();
        System.out.println("a " + evento);
        List<String> l = controller.getResultadosPossiveis(evento);
        if(l.isEmpty()) {
            javafx.alert("Evento sem Apostas abertas", "O evento selecionado provavelmente acabou.");
            return vizualizarApostas();
        }
        listView.getItems().addAll(l);

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button b3 = new Button("Atualizar.");
        b3.setOnAction(e -> {
            javafx.endScene(e);
            System.out.println(evento);
            javafx.makeWindow("Desportos - RASBET -" + evento, painelApostasbyEvento(evento));
        });

        layout.getChildren().addAll(listView,b3);
        return new Scene(layout, 500, 400);
    }

    private void login(String email, String pwd, boolean isGestor){
        if(isGestor){
            if(controller.verificarLoginGestor(email, pwd)){
                javafx.makeWindow("Menu do Gestor : "+ email, gestor.painelGestor());
            } else javafx.alert("ERRO", "Falha ao iniciar a sessão, verifique os seus dados.");
        } else {
            if(controller.verificarLoginApostador(email, pwd)){
                try {
                    String nome = controller.getNomeApostador(email);
                    javafx.makeWindow("Menu do Utilizador : " + nome, utilizador.painelUtilizador(nome, email));
                } catch (Exception e){
                    javafx.alert("ERRO", "Não há nenhum apostador com esse email.");
                }
            } else javafx.alert("ERRO", "Falha ao iniciar a sessão, verifique os seus dados.");
        }
    }
}
