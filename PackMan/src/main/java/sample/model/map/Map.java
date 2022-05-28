package sample.model.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Map extends GridPane {
    private static final Image WALL_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/wall.png")));
    private static final Image FIELD_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/field.png")));
    private static final Image COIN_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/coin.png")));
    private static final Image ENERGY_BOMB_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/energyBomb.png")));

    private ImageView[][] mapPictures = new ImageView[10 * 2 + 1][10 * 2 + 1];
    private char[][] maze;

    public Map(char[][] maze, int cellSize) {
        this.maze = maze;
        fillPictures(maze, cellSize);
        makeGridPane();
    }

    private void fillPictures(char[][] maze, int cellSize) {
        for (int i = 0; i < 10 * 2 + 1; i++) {
            for (int j = 0; j < 10 * 2 + 1; j++) {
                if (maze[i][j] == '1') {
                    this.mapPictures[i][j] = new ImageView(WALL_IMAGE);
                } else if (maze[i][j] == '*') this.mapPictures[i][j] = new ImageView(COIN_IMAGE);
                else {
                    this.mapPictures[i][j] = new ImageView(ENERGY_BOMB_IMAGE);
                }
                this.mapPictures[i][j].setFitHeight(cellSize);
                this.mapPictures[i][j].setFitWidth(cellSize);
            }
        }
    }

    private void makeGridPane() {
        for (int i = 0; i < 10 * 2 + 1; i++) {
            for (int j = 0; j < 10 * 2 + 1; j++) {
                this.add(this.mapPictures[i][j], j, i);
            }
        }
    }

    public char[][] getMaze() {
        return this.maze;
    }

    public ImageView[][] getMapPictures() {
        return mapPictures;
    }

    public void setCellPicture(int yIndex, int xIndex, CellType cellType) {
        switch (cellType) {
            case WALL: {
                this.mapPictures[yIndex][xIndex].setImage(WALL_IMAGE);
                this.maze[yIndex][xIndex] = '1';
                break;
            }
            case FIELD: {
                this.mapPictures[yIndex][xIndex].setImage(FIELD_IMAGE);
                this.maze[yIndex][xIndex] = '0';
                break;
            }
            case COIN: {
                this.mapPictures[yIndex][xIndex].setImage(COIN_IMAGE);
                this.maze[yIndex][xIndex] = '*';
                break;
            }
            case ENERGY_BOMB: {
                this.mapPictures[yIndex][xIndex].setImage(ENERGY_BOMB_IMAGE);
                this.maze[yIndex][xIndex] = '#';
                break;
            }
        }
    }

    public Map clone(int cellSize) {
        char[][] cloneMaze = new char[21][21];
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                cloneMaze[i][j] = this.maze[i][j];
            }
        }
        Map map = new Map(cloneMaze, cellSize);
        return map;
    }
}
