package com.example.controllers;

import com.example.Main;
import com.example.dao.UserDAO;
import com.example.models.User;
import com.example.util.PasswordUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class WelcomeController {

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        signInButton.setOnAction(event -> {
            try {
                handleLoginConfirm();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        signUpButton.setOnAction(event -> handleSignUp());
    }

    @FXML
    private void handleSignIn() throws SQLException {
        handleLoginConfirm();
    }

    @FXML
    private void handleSignUp() {
        mainApp.showSignUpPage();
    }

    @FXML
    private void handleLoginConfirm() throws SQLException {
        String email = loginEmail.getText();
        String password = loginPassword.getText();

        if (email == null || email.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter an email address.");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Password", "Please enter a password.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, PasswordUtils.hashPassword(password));

        if (user != null) {
            showAlert(Alert.AlertType.INFORMATION, "Login successful", "Welcome " + user.getFirstName() + "!");
            mainApp.setLoggedInUser(user);
            mainApp.showProfilePage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid email or password", "The email or password you entered is incorrect.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
