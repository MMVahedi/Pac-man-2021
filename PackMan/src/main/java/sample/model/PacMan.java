package sample.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.controller.animation.DirectionOfMovement;
import sample.model.map.Map;

public class PacMan extends ImageView {
    private final Image PACMAN_RIGHT = new Image(String.valueOf(Map.class.getResource("/sample/pic/pacmanRight.gif")));
    private final Image PACMAN_LEFT = new Image(String.valueOf(Map.class.getResource("/sample/pic/pacmanLeft.gif")));
    private final Image PACMAN_UP = new Image(String.valueOf(Map.class.getResource("/sample/pic/pacmanUp.gif")));
    private final Image PACMAN_DOWN = new Image(String.valueOf(Map.class.getResource("/sample/pic/pacmanDown.gif")));
    private double xPosition;
    private double yPosition;

    public PacMan(int x, int y) {
        this.setXPosition(x);
        this.setYPosition(y);
        this.setImage(PACMAN_RIGHT);
        this.setFitWidth(30);
        this.setFitHeight(30);
    }

    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
        this.setLayoutX(xPosition);
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
        this.setLayoutY(yPosition);
    }

    public void setDirection(DirectionOfMovement direction) {
        switch (direction) {
            case UP: {
                this.setImage(PACMAN_UP);
                break;
            }
            case RIGHT: {
                this.setImage(PACMAN_RIGHT);
                break;
            }
            case DOWN: {
                this.setImage(PACMAN_DOWN);
                break;
            }
            case LEFT: {
                this.setImage(PACMAN_LEFT);
                break;
            }
        }
    }
}
