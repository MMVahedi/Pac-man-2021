package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.UserController;

public class LoginPage extends Application {
    private static final UserController CONTROLLER = UserController.getInstance();
    private static Stage stage;
    private static AnchorPane pane;
    private static TextField usernameField;
    private static PasswordField passwordField;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/LoginPage.fxml"));
        addFieldsToPane(pane);
        stage.setTitle("LoginPage");
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    private void addFieldsToPane(AnchorPane pane) {
        usernameField = new TextField();
        usernameField.setLayoutX(150.0);
        usernameField.setLayoutY(126.0);
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setLayoutX(150.0);
        passwordField.setLayoutY(186.0);
        passwordField.setPromptText("Password");
        pane.getChildren().add(usernameField);
        pane.getChildren().add(passwordField);
    }

    public void backToStartPage(MouseEvent mouseEvent) throws Exception {
        new StartPage().start(stage);
    }

    public void login(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        clear(mouseEvent);
        try {
            CONTROLLER.login(username, password);
            new MainUserPage().start(stage);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login Error.");
            alert.setContentText(exception.getMessage());
            alert.setTitle("Login Error.");
            alert.show();
        }
    }

    public void clear(MouseEvent mouseEvent) {
        usernameField.clear();
        passwordField.clear();
    }
}
