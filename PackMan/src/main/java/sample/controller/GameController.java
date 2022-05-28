package sample.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import sample.controller.animation.GhostAnimation;
import sample.controller.animation.PacManAnimation;
import sample.model.*;
import sample.model.map.Map;
import sample.view.GamePage;

import static sample.controller.animation.DirectionOfMovement.*;
import static sample.model.map.CellType.*;

public class GameController {
    private static GamePage VIEW;
    private static GameController instance;

    private Map map;
    private char[][] maze;
    private int pacmanXIndex;
    private int pacmanYIndex;
    private int score;
    private int health;
    private int numberOfEatenItems;
    private int numberOfActiveEnergyBombs;
    private int numberOfEatenGhosts = 0;

    private GameController() {
    }

    public static GameController getInstance(GamePage gamePage) {
        if (instance == null) instance = new GameController();
        VIEW = gamePage;
        instance.score = 0;
        instance.numberOfEatenItems = 0;
        instance.numberOfActiveEnergyBombs = 0;
        return instance;
    }

    public Map getActiveMap() {
        map = GameSettingController.getInstance().getActiveMap().clone(30);
        return map;
    }

    public int getHealth() {
        health = GameSettingController.getInstance().getHealth();
        return health;
    }

    public int[] getRandomLocationForPacMan() {
        maze = map.getMaze();
        int[] location = new int[2];
        while (true) {
            int randomXLocation = (int) (Math.random() * 8) + 6;
            int randomYLocation = (int) (Math.random() * 8) + 6;
            if (maze[randomYLocation][randomXLocation] == '*') {
                location[0] = randomYLocation * 30;
                location[1] = randomXLocation * 30;
                break;
            }
        }
        return location;
    }

    public void movePacmanRight(PacMan pacMan) {
        setPacmanIndexes(pacMan);
        if (maze[pacmanYIndex][pacmanXIndex + 1] != '1') {
            pacMan.setDirection(RIGHT);
            new PacManAnimation(pacMan, RIGHT).play();
            setField(pacmanYIndex, pacmanXIndex + 1, pacMan);
        }
    }

    public void movePacmanLeft(PacMan pacMan) {
        setPacmanIndexes(pacMan);
        if (maze[pacmanYIndex][pacmanXIndex - 1] != '1') {
            pacMan.setDirection(LEFT);
            new PacManAnimation(pacMan, LEFT).play();
            setField(pacmanYIndex, pacmanXIndex - 1, pacMan);
        }
    }

    public void movePacmanUp(PacMan pacMan) {
        setPacmanIndexes(pacMan);
        if (maze[pacmanYIndex - 1][pacmanXIndex] != '1') {
            pacMan.setDirection(UP);
            new PacManAnimation(pacMan, UP).play();
            setField(pacmanYIndex - 1, pacmanXIndex, pacMan);
        }
    }

    public void movePacmanDown(PacMan pacMan) {
        setPacmanIndexes(pacMan);
        if (maze[pacmanYIndex + 1][pacmanXIndex] != '1') {
            pacMan.setDirection(DOWN);
            new PacManAnimation(pacMan, DOWN).play();
            setField(pacmanYIndex + 1, pacmanXIndex, pacMan);
        }
    }

    private void setField(int yIndex, int xIndex, PacMan pacMan) {
        char cell = maze[yIndex][xIndex];
        if (cell != '0') {
            if (cell == '*') {
                score += 5;
                VIEW.setScore(score);
                VIEW.playEatCoinMusic();
            } else if (cell == '#') {
                numberOfActiveEnergyBombs++;
                VIEW.changeGhostsImagesToScapeGhost();
                VIEW.playEatEnergyBombMusic();
            }
            numberOfEatenItems++;
            map.setCellPicture(yIndex, xIndex, FIELD);
        }
        if (numberOfEatenItems == 219) {
            numberOfEatenItems = 0;
            health++;
            VIEW.setHealth(health);
            resetMap(yIndex, xIndex, pacMan);
        }
    }

    private void resetMap(int yIndex, int xIndex, PacMan pacMan) {
        char[][] startingMaze = GameSettingController.getInstance().getActiveMap().getMaze();
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                char value = startingMaze[i][j];
                switch (value) {
                    case '1': {
                        map.setCellPicture(i, j, WALL);
                        break;
                    }
                    case '0': {
                        map.setCellPicture(i, j, FIELD);
                        break;
                    }
                    case '*': {
                        map.setCellPicture(i, j, COIN);
                        break;
                    }
                    case '#': {
                        map.setCellPicture(i, j, ENERGY_BOMB);
                        break;
                    }
                }
            }
        }
        map.setCellPicture(yIndex, xIndex, FIELD);
        VIEW.setMapReset(true);
        VIEW.stopTheGame();
    }

    public void decreaseNumberOfActiveEnergyBombs() {
        numberOfActiveEnergyBombs--;
    }

    public int getNumberOfActiveEnergyBombs() {
        return numberOfActiveEnergyBombs;
    }

    private void setPacmanIndexes(PacMan pacMan) {
        pacmanXIndex = (int) pacMan.getXPosition() / 30;
        pacmanYIndex = (int) pacMan.getYPosition() / 30;
    }

    public void pacManHasDealtWithGhost(GhostAnimation ghostAnimation) {
        if (numberOfActiveEnergyBombs == 0) {
            killPacMan();
        } else {
            eatGhost(ghostAnimation);
        }
    }

    private void eatGhost(GhostAnimation ghostAnimation) {
        numberOfEatenGhosts++;
        score += numberOfEatenGhosts * 200;
        ghostAnimation.pause();
        Timeline playtime = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    ghostAnimation.play();
                })
        );
        playtime.play();
        VIEW.setScore(score);
        VIEW.playEatGhostMusic();
    }

    private void killPacMan() {
        health--;
        VIEW.setHealth(health);
        if (health == 0) {
            if (UserController.getInstance().getWorkingUser() != null) {
                UserController.getInstance().setUserScore(score);
            }
            VIEW.gameOver();
        } else {
            VIEW.playPacManDeathMusic();
            VIEW.stopTheGame();
        }
    }

    public void setNumberOfEatenGhosts(int numberOfEatenGhosts) {
        this.numberOfEatenGhosts = numberOfEatenGhosts;
    }
}
