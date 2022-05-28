package sample.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.controller.GameSettingController;
import sample.controller.UserController;
import sample.model.map.Map;

public class GameSettingPage extends Application {
    private static final GameSettingController CONTROLLER = GameSettingController.getInstance();
    private static Stage stage;
    private static AnchorPane pane;

    @FXML
    private static Label healthLabel;
    private static Map map;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/GameSettingPage.fxml"));
        stage.setTitle("GameSettingPage");
        map = CONTROLLER.getDefaultMap();
        loadMap();
        loadHealthLabel();
        pane.getChildren().add(healthLabel);
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    private void loadMap() {
        map.setLayoutX(40);
        map.setLayoutY(40);
        pane.getChildren().add(map);
    }

    public void loadHealthLabel() {
        healthLabel = new Label();
        healthLabel.setLayoutY(52);
        healthLabel.setLayoutX(479);
        healthLabel.setText(String.valueOf(CONTROLLER.getHealth()));
        healthLabel.prefHeight(31);
        healthLabel.prefWidth(17);
        healthLabel.setFont(new Font("Baskerville Old Face", 24));
    }

    public void decreaseHealth(MouseEvent mouseEvent) {
        int health = CONTROLLER.decreaseHealth();
        healthLabel.setText(String.valueOf(health));
    }

    public void increaseHealth(MouseEvent mouseEvent) {
        int health = CONTROLLER.increaseHealth();
        healthLabel.setText(String.valueOf(health));
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        if (UserController.getInstance().getWorkingUser() == null) {
            new MainGuestPage().start(stage);
        } else {
            new MainUserPage().start(stage);
        }
    }

    public void newMap(MouseEvent mouseEvent) {
        pane.getChildren().remove(map);
        map = CONTROLLER.getRandomMap();
        loadMap();
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        CONTROLLER.setActiveMap(map);
        new GamePage().start(stage);
    }
}
