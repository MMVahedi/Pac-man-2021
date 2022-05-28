package sample.controller.animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import sample.model.PacMan;

public class PacManAnimation extends Transition {
    private DirectionOfMovement direction;
    private PacMan pacMan;
    private double yPositionBeforeMove;
    private double xPositionBeforeMove;

    public PacManAnimation(PacMan pacMan, DirectionOfMovement direction) {
        this.pacMan = pacMan;
        this.direction = direction;
        this.yPositionBeforeMove = pacMan.getYPosition();
        this.xPositionBeforeMove = pacMan.getXPosition();
        this.setCycleDuration(Duration.millis(50));
        this.setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        switch (direction) {
            case UP: {
                pacMan.setYPosition(yPositionBeforeMove - v * 30);
                break;
            }
            case RIGHT: {
                pacMan.setXPosition(xPositionBeforeMove + v * 30);
                break;
            }
            case DOWN: {
                pacMan.setYPosition(yPositionBeforeMove + v * 30);
                break;
            }
            case LEFT: {
                pacMan.setXPosition(xPositionBeforeMove - v * 30);
                break;
            }
        }
    }
}
