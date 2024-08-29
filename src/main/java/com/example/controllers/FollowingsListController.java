
package com.example.controllers;

import com.example.dao.UserDAO;
import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FollowingsListController {

    @FXML
    private TableView<User> followingsTable;
    @FXML
    private TableColumn<User, String> colFullName;
    @FXML
    private TableColumn<User, String> colEmail;

    private UserDAO userDAO;

    public FollowingsListController() {
        userDAO = new UserDAO();
    }

    public void setUser(User user) {
        followingsTable.getItems().clear();
        followingsTable.getItems().addAll(userDAO.getFollowings(user.getId()));
        userDAO.getFollowers(user.getId()).add(user);
        colFullName.getColumns().add(new TableColumn<>(user.getFullName()));
        colEmail.getColumns().add(new TableColumn<>(user.getEmail()));
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) followingsTable.getScene().getWindow();
        stage.close();
    }
}