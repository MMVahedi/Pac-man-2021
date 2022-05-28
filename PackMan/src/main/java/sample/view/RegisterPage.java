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

public class RegisterPage extends Application {
    private static final UserController CONTROLLER = UserController.getInstance();
    private static Stage stage;
    private static AnchorPane pane;
    private static TextField usernameField;
    private static PasswordField passwordField;
    private static PasswordField confirmedPasswordField;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/RegisterPage.fxml"));
        addFieldsToPane(pane);
        stage.setTitle("RegisterPage");
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    private void addFieldsToPane(AnchorPane pane) {
        usernameField = new TextField();
        usernameField.setLayoutX(150.0);
        usernameField.setLayoutY(108.0);
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setLayoutX(150.0);
        passwordField.setLayoutY(156.0);
        passwordField.setPromptText("Password");
        confirmedPasswordField = new PasswordField();
        confirmedPasswordField.setLayoutX(150.0);
        confirmedPasswordField.setLayoutY(205.0);
        confirmedPasswordField.setPromptText("Confirm Password");
        pane.getChildren().add(usernameField);
        pane.getChildren().add(passwordField);
        pane.getChildren().add(confirmedPasswordField);
    }

    public void register(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmedPassword = confirmedPasswordField.getText();
        clear(null);
        try {
            CONTROLLER.register(username, password, confirmedPassword);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register successful");
            alert.setHeaderText("You have successfully registered.");
            alert.setContentText("Now you can back to main menu and login with your account.");
            alert.showAndWait();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Register Error.");
            alert.setContentText(exception.getMessage());
            alert.setTitle("Register Error");
            alert.show();
        }
    }

    public void backToStartPage(MouseEvent mouseEvent) throws Exception {
        new StartPage().start(stage);
    }

    public void clear(MouseEvent mouseEvent) {
        usernameField.clear();
        passwordField.clear();
        confirmedPasswordField.clear();
    }
}
