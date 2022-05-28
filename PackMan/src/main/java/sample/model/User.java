package sample.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private static ArrayList<User> allUsers;

    private String username;
    private String password;
    private int maxScore;
    private LocalDateTime maxScoreAchievedTime;
    private int rank;

    static {
        allUsers = new ArrayList<>();
        DataBase.getInstance().updateAllUsersArrayList();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.maxScore = 0;
        maxScoreAchievedTime = LocalDateTime.now();
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public String getUsername() {
        return username;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        maxScoreAchievedTime = LocalDateTime.now();
        this.maxScore = maxScore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getMaxScoreAchievedTime() {
        return maxScoreAchievedTime;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void remove() {
        allUsers.remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
