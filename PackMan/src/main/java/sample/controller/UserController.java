package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.DataBase;
import sample.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class sortUsersForScoreBoard implements Comparator<User> {
    public int compare(User user1, User user2) {
        if (user1.getMaxScore() == user2.getMaxScore()) {
            if (user1.getMaxScoreAchievedTime().isBefore(user2.getMaxScoreAchievedTime())) return -1;
            return 1;
        }
        return -(user1.getMaxScore() - user2.getMaxScore());
    }
}

public class UserController {
    private static UserController instance;
    private static User workingUser;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null) instance = new UserController();
        return instance;
    }

    public User getWorkingUser() {
        return workingUser;
    }

    public void login(String username, String password) throws Exception {
        User user = User.getUserByUsername(username);
        if (user == null) throw new Exception("There is no user with this username.");
        if (!user.getPassword().equals(password))
            throw new Exception("The password entered is incorrect.");
        workingUser = User.getUserByUsername(username);
    }

    public void register(String username, String password, String confirmedPassword) throws Exception {
        User user = User.getUserByUsername(username);
        if (username.equals("") || password.equals("") || confirmedPassword.equals(""))
            throw new Exception("You must fill in all the fields.");
        if (user != null) throw new Exception("There is another user with this username!\nTry something else.");
        if (!password.equals(confirmedPassword))
            throw new Exception("The password entered and the confirmed password do not match");
        User newUser = new User(username, password);
        DataBase.getInstance().addNewUser(newUser);
        DataBase.getInstance().updateUserInformation(newUser);
    }

    public void deleteAccount() {
        DataBase.getInstance().removeUser(workingUser);
        workingUser.remove();
    }

    public void logout() {
        workingUser = null;
    }

    public void changePassword(String password) throws Exception {
        if (password.equals("")) throw new Exception("New password can not be empty!");
        workingUser.setPassword(password);
        DataBase.getInstance().updateUserInformation(workingUser);
    }

    public ObservableList<User> getScoreBoard() {
        ArrayList<User> allUsers = User.getAllUsers();
        Collections.sort(allUsers, new sortUsersForScoreBoard());
        ObservableList<User> scoreBoard = FXCollections.observableArrayList();
        int lastRank = 0;
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (i == 0) {
                user.setRank(i + 1);
                lastRank = i + 1;
            } else {
                if (user.getMaxScore() == allUsers.get(i - 1).getMaxScore()) {
                    user.setRank(lastRank);
                } else {
                    user.setRank(i + 1);
                    lastRank = i + 1;
                }
            }
            scoreBoard.add(user);
            if (i == 9) break;
        }
        return scoreBoard;
    }

    public void setUserScore(int score) {
        if (workingUser.getMaxScore() <= score) {
            workingUser.setMaxScore(score);
        }
        DataBase.getInstance().updateUserInformation(workingUser);
    }
}
