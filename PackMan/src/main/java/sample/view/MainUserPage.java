package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.UserController;

import java.util.Optional;

public class MainUserPage extends Application {
    private static final UserController CONTROLLER = UserController.getInstance();
    private static Stage stage;
    private static AnchorPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/MainUserPage.fxml"));
        stage.setTitle("MainUserPage");
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    public void newGame(MouseEvent mouseEvent) throws Exception {
        new GameSettingPage().start(stage);
    }

    public void loadGame(MouseEvent mouseEvent) {
    }

    public void changePassword(MouseEvent mouseEvent) {
        TextInputDialog dialog = new TextInputDialog("New Password");
        dialog.setTitle("Change Password");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your new password:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                CONTROLLER.changePassword(result.get());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Change Password");
                alert.setHeaderText(null);
                alert.setContentText("Your password has been successfully changed");
                alert.showAndWait();
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.setTitle("Change Password Error.");
                alert.show();
            }
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account Confirmation");
        alert.setHeaderText("Delete Account Confirmation");
        alert.setContentText("Are you sure?" +
                "Do you want to delete your account?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CONTROLLER.deleteAccount();
            logout(null);
        }
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        CONTROLLER.logout();
        new StartPage().start(stage);
    }
}
