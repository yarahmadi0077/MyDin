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

public class SignUpController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField signUpEmail;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private PasswordField signUpPassword1;

    @FXML
    private Button signUpConfirm;

    @FXML
    private Button cancelSignUp;

    private Stage signUpStage;
    private Main mainApp;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        signUpConfirm.setOnAction(event -> handleSignUpConfirm());
        cancelSignUp.setOnAction(event -> handleCancelSignUp());
    }

    public void setSignUpStage(Stage signUpStage) {
        this.signUpStage = signUpStage;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private void handleSignUpConfirm() {
        String first = firstName.getText();
        String last = lastName.getText();
        String email = signUpEmail.getText();
        String pwd = signUpPassword.getText();
        String pwdConfirm = signUpPassword1.getText();

        if (first == null || first.isEmpty() ||
                last == null || last.isEmpty() ||
                email == null || email.isEmpty() ||
                pwd == null || pwd.isEmpty() ||
                pwdConfirm == null || pwdConfirm.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required.");
            return;
        }

        if (!validateName(first) || !validateName(last)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Name", "Names must contain only letters.");
            return;
        }

        if (!validateEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (email.length() > 254) {
            showAlert(Alert.AlertType.ERROR, "Email too long", "Email length should not exceed 254 characters.");
            return;
        }

        if (!validatePassword(pwd)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Password", "Password must be at least 8 characters long and contain both letters and numbers.");
            return;
        }

        if (!pwd.equals(pwdConfirm)) {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match", "The passwords you entered do not match.");
            return;
        }

        User user = new User(first, last, email, PasswordUtils.hashPassword(pwd));
        if (userDAO.isEmailTaken(email)) {
            showAlert(Alert.AlertType.ERROR, "Email is already taken", "The email you entered is already registered.");
            return;
        }

        if (userDAO.registerUser(user)) {
            showAlert(Alert.AlertType.INFORMATION, "User registered successfully", "You have successfully registered.");
            signUpStage.close();
            mainApp.showWelcomePage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to register user", "There was an error registering your account. Please try again.");
        }
    }

    private void handleCancelSignUp() {
        signUpStage.close();
    }

    private boolean validateName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean validateEmail(String email) {
        return email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean validatePassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
