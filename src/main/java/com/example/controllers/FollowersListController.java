package com.example.controllers;

import com.example.Main;
import com.example.dao.UserDAO;
import com.example.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FollowersListController {

    @FXML
    private TableView<User> followersTable;
    @FXML
    private TableColumn<User, String> colFullName;
    @FXML
    private TableColumn<User, String> colEmail;

    private UserDAO userDAO;

    public FollowersListController() {
        userDAO = new UserDAO();
    }

    public void setUser(User user) {
        followersTable.getItems().clear();
        followersTable.getItems().addAll(userDAO.getFollowers(user.getId()));
        userDAO.getFollowers(user.getId()).add(user);
        colFullName.getColumns().add(new TableColumn<>(user.getFullName()));
        colEmail.getColumns().add(new TableColumn<>(user.getEmail()));
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) followersTable.getScene().getWindow();
        stage.close();
    }
}