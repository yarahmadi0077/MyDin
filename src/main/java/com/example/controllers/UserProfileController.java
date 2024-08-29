package com.example.controllers;

import com.example.Main;
import com.example.dao.UserDAO;
import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class UserProfileController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Label lblFullName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblProfession;
    @FXML
    private TextArea txtNotes;

    private UserDAO userDAO;
    private User currentUser;

    public UserProfileController() {
        userDAO = new UserDAO();
    }

    public void setUser(User user) {
        this.currentUser = user;
        displayUserInfo();
    }

    private void displayUserInfo() {
        if (currentUser != null) {
            lblFullName.setText(currentUser.getFullName());
            lblEmail.setText(currentUser.getEmail());
            lblCity.setText(currentUser.getCity());
            lblCountry.setText(currentUser.getCountry());
            lblProfession.setText(currentUser.getProfession());
        }
    }

    @FXML
    private void followUser(ActionEvent event) {
        if (currentUser != null) {
            userDAO.followUser(currentUser.getId(), Main.getLoggedInUser().getId());
            System.out.println(Main.getLoggedInUser().getId());
            showAlert("Follow", "Followed user successfully!");
        }
    }

    @FXML
    private void connectUser(ActionEvent event) {
        String notes = txtNotes.getText();
        if (notes.length() > 500) {
            showAlert("Error", "Notes length exceeds 500 characters.");
        } else {
            if (currentUser != null) {
                userDAO.sendConnectionRequest(mainApp.getLoggedInUser().getId(), currentUser.getId(), notes);
                showAlert("Connect", "Connection request sent successfully!");
            }
        }
    }


    @FXML
    private void viewFollowers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fxml/FollowersList.fxml"));
            Parent root = loader.load();

            FollowersListController controller = loader.getController();
            controller.setUser(Main.getLoggedInUser());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewFollowings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fxml/FollowingsList.fxml"));
            Parent root = loader.load();

            FollowingsListController controller = loader.getController();
            controller.setUser(Main.getLoggedInUser());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
