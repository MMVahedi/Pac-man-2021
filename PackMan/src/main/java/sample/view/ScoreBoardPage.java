package sample.view;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.controller.UserController;
import sample.model.User;

public class ScoreBoardPage extends Application {
    private static final UserController CONTROLLER = UserController.getInstance();
    private static Stage stage;
    private static AnchorPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/ScoreBoardPage.fxml"));
        stage.setTitle("ScoreBoardPage");
        addScoreBoard();
        stage.setScene(new Scene(pane, 600, 400));
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new StartPage().start(stage);
    }

    private void addScoreBoard() {
        TableView<User> tableView = new TableView<>();
        TableColumn<User, Integer> rankColumn = new TableColumn<>("Rank");
        rankColumn.setMinWidth(100);
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("maxScore"));
        tableView.setItems(CONTROLLER.getScoreBoard());
        tableView.getColumns().addAll(rankColumn, nameColumn, scoreColumn);
        tableView.setFixedCellSize(25);
        tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(30));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView);
        vbox.setLayoutY(50);
        vbox.setLayoutX(100);
        pane.getChildren().add(vbox);
    }
}
