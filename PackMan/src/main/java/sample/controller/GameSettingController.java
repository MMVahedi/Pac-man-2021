package sample.controller;

import sample.model.map.Map;

public class GameSettingController {
    private static GameSettingController instance;

    private Map defaultMap;
    private Map activeMap;
    private int health;

    private GameSettingController() {
        health = 3;
        defaultMap = new Map(getRandomMaze(10, 10), 15);
        activeMap = defaultMap;
    }

    public static GameSettingController getInstance() {
        if (instance == null) instance = new GameSettingController();
        return instance;
    }

    public int getHealth() {
        return health;
    }

    public int decreaseHealth() {
        if (health > 2) health--;
        return health;
    }

    public int increaseHealth() {
        if (health < 5) health++;
        return health;
    }

    public Map getDefaultMap() {
        return defaultMap;
    }

    public Map getActiveMap() {
        return activeMap;
    }

    public void setActiveMap(Map activeMap) {
        this.activeMap = activeMap;
    }

    public Map getRandomMap() {
        Map map = new Map(getRandomMaze(10, 10), 15);
        return map;
    }

    private char[][] getRandomMaze(int height, int weight) {
        char[][] map = new char[2 * height + 1][2 * weight + 1];
        makeStartingMaze(height, weight, map);
        int randomXLocation = (int) (Math.random() * (weight - 1));
        int randomYLocation = (int) (Math.random() * (height - 1));
        makeFinalMaze(height, weight, map, randomXLocation, randomYLocation);
        removeSomeRandomWalls(20, map, height, weight);
        addSomeEnergyBomb(5, map, height, weight);
        return map;
    }

    private void makeStartingMaze(int height, int weight, char[][] maze) {
        for (int i = 0; i < height * 2 + 1; i++) {
            for (int j = 0; j < 2 * weight + 1; j++) {
                if (j % 2 == 1 && i % 2 == 1) maze[i][j] = '-';
                else maze[i][j] = '1';
            }
        }
    }

    private void makeFinalMaze(int height, int weight, char[][] maze, int XLocation, int YLocation) {
        maze[2 * YLocation + 1][2 * XLocation + 1] = '*'; //* means coin
        int[] remainedNeighbors = {1, 2, 3, 4};
        int numberOfRemainedNeighbors = makeStartingRemainedNeighbors(remainedNeighbors, height, weight, XLocation, YLocation);
        int numberOfRemainedNeighborsHolder = numberOfRemainedNeighbors;
        for (int i = 0; i < numberOfRemainedNeighborsHolder; i++) {
            int randomIndex = (int) (Math.random() * numberOfRemainedNeighbors);
            if (remainedNeighbors[randomIndex] == 1 && maze[2 * (YLocation - 1) + 1][2 * XLocation + 1] != '*') {
                makeFinalMaze(height, weight, maze, XLocation, YLocation - 1);
                maze[2 * YLocation - 1 + 1][2 * XLocation + 1] = '*';
            } else if (remainedNeighbors[randomIndex] == 3 && maze[2 * (YLocation + 1) + 1][2 * XLocation + 1] != '*') {
                makeFinalMaze(height, weight, maze, XLocation, YLocation + 1);
                maze[2 * YLocation + 1 + 1][2 * XLocation + 1] = '*';
            } else if (remainedNeighbors[randomIndex] == 4 && maze[2 * YLocation + 1][2 * (XLocation - 1) + 1] != '*') {
                makeFinalMaze(height, weight, maze, XLocation - 1, YLocation);
                maze[2 * YLocation + 1][2 * XLocation - 1 + 1] = '*';
            } else if (remainedNeighbors[randomIndex] == 2 && maze[2 * YLocation + 1][2 * (XLocation + 1) + 1] != '*') {
                makeFinalMaze(height, weight, maze, XLocation + 1, YLocation);
                maze[2 * YLocation + 1][2 * XLocation + 1 + 1] = '*';
            }
            removeNeighbor(remainedNeighbors, remainedNeighbors[randomIndex]);
            numberOfRemainedNeighbors--;
        }
    }

    private int makeStartingRemainedNeighbors(int[] remainedNeighbors, int height, int weight, int XLocation, int YLocation) {
        int numberOfRemainedNeighbors = 4;
        if (XLocation == 0) {
            removeNeighbor(remainedNeighbors, 4);
            numberOfRemainedNeighbors--;
        }
        if (XLocation == weight - 1) {
            removeNeighbor(remainedNeighbors, 2);
            numberOfRemainedNeighbors--;
        }
        if (YLocation == 0) {
            removeNeighbor(remainedNeighbors, 1);
            numberOfRemainedNeighbors--;
        }
        if (YLocation == height - 1) {
            removeNeighbor(remainedNeighbors, 3);
            numberOfRemainedNeighbors--;
        }
        return numberOfRemainedNeighbors;
    }

    private void removeNeighbor(int[] remainedNeighbors, int valueToDelete) {
        for (int i = 0; i < 4; i++) {
            if (remainedNeighbors[i] == valueToDelete) {
                for (int j = i; j < 3; j++) remainedNeighbors[j] = remainedNeighbors[j + 1];
                remainedNeighbors[3] = 0;
                return;
            }
        }
    }

    private void removeSomeRandomWalls(int count, char[][] map, int height, int weight) {
        int numberOfRemovedWalls = 0;
        while (numberOfRemovedWalls <= count) {
            int randomXLocation = (int) (Math.random() * (weight - 2)) + 1;
            int randomYLocation = (int) (Math.random() * (height - 2)) + 1;
            if (map[randomYLocation * 2][randomXLocation * 2] == '1' &&
                    (map[randomYLocation * 2 + 1][randomXLocation * 2] == '*' ||
                            map[randomYLocation * 2][randomXLocation * 2 + 1] == '*' ||
                            map[randomYLocation * 2 - 1][randomXLocation * 2] == '*' ||
                            map[randomYLocation * 2][randomXLocation * 2 - 1] == '*')) {
                map[randomYLocation * 2][randomXLocation * 2] = '*';
                numberOfRemovedWalls++;
            }
        }
    }

    private void addSomeEnergyBomb(int count, char[][] map, int height, int weight) {
        int numberOfRemovedWalls = 0;
        while (numberOfRemovedWalls < count) {
            int randomXLocation = (int) (Math.random() * (weight - 1));
            int randomYLocation = (int) (Math.random() * (height - 1));
            if (map[randomYLocation * 2 + 1][randomXLocation * 2 + 1] == '*') {
                map[randomYLocation * 2 + 1][randomXLocation * 2 + 1] = '#'; //# means energy bomb.
                numberOfRemovedWalls++;
            }
        }
    }
}
