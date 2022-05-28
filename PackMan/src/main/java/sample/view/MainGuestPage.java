package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainGuestPage extends Application {
    private static Stage stage;
    private static AnchorPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/MainGuestPage.fxml"));
        stage.setTitle("MainGuestPage");
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    public void newGame(MouseEvent mouseEvent) throws Exception {
        new GameSettingPage().start(stage);
    }

    public void loadGame(MouseEvent mouseEvent) {
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        new StartPage().start(stage);
    }
}
