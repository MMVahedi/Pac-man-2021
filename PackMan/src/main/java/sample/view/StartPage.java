package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.UserController;

public class StartPage extends Application {
    private static final UserController CONTROLLER = UserController.getInstance();
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/sample/fxml/StartPage.fxml"));
        stage.setTitle("PacMan");
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        new LoginPage().start(stage);
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        new RegisterPage().start(stage);
    }

    public void loginWithGuest(MouseEvent mouseEvent) throws Exception {
        new MainGuestPage().start(stage);
    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        new ScoreBoardPage().start(stage);
    }

    public void exit(MouseEvent mouseEvent) {
        stage.close();
    }
}
