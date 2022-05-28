package sample.controller.animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import sample.controller.GameController;
import sample.model.ghost.Ghost;
import sample.model.map.Map;
import sample.model.PacMan;

import java.util.ArrayList;
import java.util.Random;

public class GhostAnimation extends Transition {
    private Ghost ghost;
    private char[][] mapMaze;
    private PacMan pacMan;
    private int movePosition;
    private double xPositionBeforeMove;
    private double yPositionBeforeMove;
    private boolean haveBeenFinished = false;
    private GameController controller;

    public GhostAnimation(Ghost ghost, Map map, PacMan pacMan, GameController controller) {
        this.ghost = ghost;
        this.mapMaze = map.getMaze();
        this.pacMan = pacMan;
        this.controller = controller;
        this.setCycleDuration(Duration.millis(500));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        if (ghost.hasCollision(pacMan)) {
            controller.pacManHasDealtWithGhost(this);
        }
        if (!haveBeenFinished) {
            ArrayList<Integer> numbers = new ArrayList<>();
            Random randomGenerator = new Random();
            while (numbers.size() < 4) {
                int random = randomGenerator.nextInt(4);
                if (!numbers.contains(random)) {
                    numbers.add(random);
                }
            }
            xPositionBeforeMove = ghost.getXPosition();
            yPositionBeforeMove = ghost.getYPosition();
            int ghostXIndex = (int) (xPositionBeforeMove / 30);
            int ghostYIndex = (int) (yPositionBeforeMove / 30);
            for (int i = 0; i < 4; i++) {
                int randomMove = numbers.get(i);
                // 1-up 2-right 3-down 4-left
                if (randomMove == 0 && mapMaze[ghostYIndex - 1][ghostXIndex] != '1') {
                    movePosition = randomMove;
                    break;
                } else if (randomMove == 1 && mapMaze[ghostYIndex][ghostXIndex + 1] != '1') {
                    movePosition = randomMove;
                    break;
                } else if (randomMove == 2 && mapMaze[ghostYIndex + 1][ghostXIndex] != '1') {
                    movePosition = randomMove;
                    break;
                } else if (randomMove == 3 && mapMaze[ghostYIndex][ghostXIndex - 1] != '1') {
                    movePosition = randomMove;
                    break;
                }
            }
            haveBeenFinished = true;
        }
        switch (movePosition) {
            case 0: {
                ghost.setYPosition(yPositionBeforeMove - v * 30);
                break;
            }
            case 1: {
                ghost.setXPosition(xPositionBeforeMove + v * 30);
                break;
            }
            case 2: {
                ghost.setYPosition(yPositionBeforeMove + v * 30);
                break;
            }
            case 3: {
                ghost.setXPosition(xPositionBeforeMove - v * 30);
                break;
            }
        }
        if ((int) v == 1) {
            haveBeenFinished = false;
        }
    }

    public static void main(String[] args) {
        int size = 20;

        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }

        Random rand = new Random();
        while (list.size() > 0) {
            int index = rand.nextInt(list.size());
            System.out.println("Selected: " + list.remove(index));
        }
    }
}
