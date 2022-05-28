package sample.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.controller.GameController;
import sample.controller.animation.GhostAnimation;
import sample.model.*;
import sample.model.button.and.image.GameOverImage;
import sample.model.button.and.image.MuteButton;
import sample.model.button.and.image.StopButton;
import sample.model.ghost.Ghost;
import sample.model.ghost.GhostColor;
import sample.model.map.CellType;
import sample.model.map.Map;

import java.util.ArrayList;

public class GamePage extends Application {
    private GameController controller;
    private static Stage stage;
    private static AnchorPane pane;
    @FXML
    private Map map;
    private Label scoreLabel;
    private Label healthLabel;
    private MuteButton muteButton;
    private StopButton stopButton;

    private ImageView[][] mapImages;
    private Ghost redGhost; //xy : 10
    private Ghost blueGhost; //xy : 01
    private Ghost yellowGhost;//xy : 00
    private Ghost pinkGhost; //xy : 11
    private ArrayList<Ghost> allGhosts;
    private ArrayList<GhostAnimation> allGhostsAnimations;
    private PacMan pacMan;
    private MusicPack musicPack;
    private boolean isGameOver = false;
    private boolean isMapReset = false;

    public GamePage() {
        this.controller = GameController.getInstance(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = FXMLLoader.load(getClass().getResource("/sample/fxml/GamePage.fxml"));
        stage.setTitle("GamePage");
        musicPack = new MusicPack();
        loadAllParameters();
        stage.setScene(new Scene(pane, 630, 630));
        pane.getChildren().get(pane.getChildren().size() - 1).requestFocus();
        stage.show();
    }

    private void loadAllParameters() {
        playBackGroundMusic();
        loadStartingMap();
        loadPackMan();
        loadGhosts();
        loadGhostsAnimations();
        loadLabel();
        loadMuteButton();
        loadStopButton();
        setResumeEventHandler();
        pane.getChildren().add(pacMan);
    }

    private void loadMuteButton() {
        muteButton = new MuteButton(5, 530);
        muteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isGameOver) {
                    if (musicPack.isMute()) {
                        playBackGroundMusic();
                        muteButton.unMute();
                    } else {
                        pauseBackGroundMusic();
                        muteButton.mute();
                    }
                }
            }
        });
        pane.getChildren().add(muteButton);
    }

    private void loadStopButton() {
        stopButton = new StopButton(5, 570);
        stopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isGameOver) {
                    if (!stopButton.isGameStop()) {
                        stopTheGame();
                        stopButton.pause();
                    } else {
                        resumeTheGame(0);
                        stopButton.play();
                    }
                }
            }
        });
        pane.getChildren().add(stopButton);
    }

    private void loadLabel() {
        scoreLabel = new Label();
        scoreLabel.setLayoutY(5);
        scoreLabel.setLayoutX(10);
        scoreLabel.setText("Score: 0");
        scoreLabel.prefHeight(20);
        scoreLabel.prefWidth(40);
        scoreLabel.setFont(new Font("Baskerville Old Face", 18));
        scoreLabel.setTextFill(Color.WHITE);
        pane.getChildren().add(scoreLabel);
        healthLabel = new Label();
        healthLabel.setLayoutY(5);
        healthLabel.setLayoutX(120);
        healthLabel.setText("Health: " + controller.getHealth());
        healthLabel.prefHeight(20);
        healthLabel.prefWidth(40);
        healthLabel.setFont(new Font("Baskerville Old Face", 18));
        healthLabel.setTextFill(Color.WHITE);
        pane.getChildren().add(healthLabel);
    }

    private void loadPackMan() {
        int[] location = controller.getRandomLocationForPacMan();
        pacMan = new PacMan(location[1], location[0]);
        map.setCellPicture(location[0] / 30, location[1] / 30, CellType.FIELD);
        pacMan.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                switch (keyName) {
                    case "Left":
                        controller.movePacmanLeft(pacMan);
                        break;
                    case "Right":
                        controller.movePacmanRight(pacMan);
                        break;
                    case "Up":
                        controller.movePacmanUp(pacMan);
                        break;
                    case "Down":
                        controller.movePacmanDown(pacMan);
                        break;
                }
            }
        });
    }

    private void loadStartingMap() {
        map = controller.getActiveMap();
        mapImages = map.getMapPictures();
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                mapImages[i][j].setLayoutX(j * 30);
                mapImages[i][j].setLayoutY(i * 30);
                pane.getChildren().add(mapImages[i][j]);
            }
        }
    }

    private void loadGhosts() {
        allGhosts = new ArrayList<>();
        redGhost = new Ghost(GhostColor.RED);
        redGhost.setYPosition(30);
        redGhost.setXPosition(570);
        allGhosts.add(redGhost);
        blueGhost = new Ghost(GhostColor.BLUE);
        blueGhost.setYPosition(570);
        blueGhost.setXPosition(30);
        allGhosts.add(blueGhost);
        yellowGhost = new Ghost(GhostColor.YELLOW);
        yellowGhost.setYPosition(30);
        yellowGhost.setXPosition(30);
        allGhosts.add(yellowGhost);
        pinkGhost = new Ghost(GhostColor.PINK);
        pinkGhost.setYPosition(570);
        pinkGhost.setXPosition(570);
        allGhosts.add(pinkGhost);
        allGhostsAnimations = new ArrayList<>();
    }

    private void loadGhostsAnimations() {
        for (Ghost ghost : allGhosts) {
            pane.getChildren().add(ghost);
            allGhostsAnimations.add(new GhostAnimation(ghost, map, pacMan, controller));
        }
        Timeline playtime = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    allGhostsAnimations.get(0).play();
                    allGhostsAnimations.get(1).play();
                    allGhostsAnimations.get(2).play();
                    allGhostsAnimations.get(3).play();
                })
        );
        playtime.play();
    }

    private void setResumeEventHandler() {
        pane.getChildren().get(0).setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                if (!stopButton.isGameStop()) {
                    if (keyName.equals("Right") ||
                            keyName.equals("Left") ||
                            keyName.equals("Up") ||
                            keyName.equals("Down")) {
                        if (!isMapReset) {
                            resumeTheGame(2);
                        } else {
                            setMapReset(false);
                            resumeTheGame(0);
                        }
                    }
                }
            }
        });
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setHealth(int health) {
        healthLabel.setText("Health: " + health);
    }

    public void changeGhostsImagesToScapeGhost() {
        for (Ghost ghost : allGhosts)
            ghost.changePictureToScapeGhost();
        Timeline playtime = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    controller.decreaseNumberOfActiveEnergyBombs();
                    if (controller.getNumberOfActiveEnergyBombs() == 0) {
                        for (Ghost ghost : allGhosts)
                            ghost.changePictureToDefaultPicture();
                        controller.setNumberOfEatenGhosts(0);
                    }
                })
        );
        playtime.play();
    }

    public void gameOver() {
        stopTheGame();
        pane.getChildren().get(1).requestFocus();
        musicPack.getBackGround().stop();
        isGameOver = true;
        if (!musicPack.isMute()) {
            musicPack.getGameOver().play();
        }
        pane.getChildren().add(new GameOverImage(30, -5));
        Timeline playtime = new Timeline(
                new KeyFrame(Duration.seconds(7), event -> {
                    try {
                        new GameSettingPage().start(stage);
                    } catch (Exception ignored) {
                    }
                })
        );
        playtime.play();
    }

    public void stopTheGame() {
        for (GhostAnimation ghostAnimation : allGhostsAnimations) {
            ghostAnimation.pause();
        }
        pane.getChildren().get(0).requestFocus();
    }

    public void resumeTheGame(int ghostStopTime) {
        pane.getChildren().get(pane.getChildren().size() - 1).requestFocus();
        if (ghostStopTime == 0) {
            for (GhostAnimation ghostAnimation : allGhostsAnimations) {
                ghostAnimation.play();
            }
        } else {
            Timeline playtime = new Timeline(
                    new KeyFrame(Duration.seconds(ghostStopTime), event -> {
                        for (GhostAnimation ghostAnimation : allGhostsAnimations) {
                            ghostAnimation.play();
                        }
                    })
            );
            playtime.play();
        }
    }

    public void playBackGroundMusic() {
        musicPack.getBackGround().play();
        musicPack.setMute(false);
    }

    public void pauseBackGroundMusic() {
        musicPack.getBackGround().pause();
        musicPack.setMute(true);
    }

    public void playEatCoinMusic() {
        if (!musicPack.isMute()) {
            musicPack.getEatCoin().play();
        }
    }

    public void playEatEnergyBombMusic() {
        if (!musicPack.isMute()) {
            musicPack.getEatEnergyBomb().play();
        }
    }

    public void playPacManDeathMusic() {
        if (!musicPack.isMute()) {
            musicPack.getPacManDeath().play();
        }
    }

    public void playEatGhostMusic() {
        if (!musicPack.isMute()) {
            musicPack.getEatGhost().play();
        }
    }

    public void setMapReset(boolean mapReset) {
        isMapReset = mapReset;
    }
}
