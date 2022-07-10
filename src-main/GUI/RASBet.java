package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

/*
VM OPTIONS:

--module-path
C:\javafx-sdk-17.0.1\lib
--add-modules
javafx.fxml,javafx.controls,javafx.graphics

*/

public class RASBet extends Application {
    Stage window;

    @Override
    public void start(Stage primaryStage){
        Scenes s = new Scenes();

        window = primaryStage;
        window.setScene(s.menu());
        window.setTitle("Paginal Inicial - RASBET");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
