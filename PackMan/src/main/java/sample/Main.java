package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.view.StartPage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new StartPage().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
