package com.example.controllers;

import com.example.Main;
import com.example.dao.UserDAO;
import com.example.models.User;
import com.example.util.GlobalUserSession;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class ProfileController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField additionalNameField;
    @FXML private TextField titleField;
    @FXML private TextField countryField;
    @FXML private TextField cityField;
    @FXML private TextField professionField;
    @FXML private TextField emailField;

    @FXML private TextField phoneNumberField;
    @FXML private ComboBox<String> phoneTypeComboBox;
    @FXML private TextField addressField;
    @FXML private DatePicker birthdayPicker;
    @FXML private ComboBox<String> birthdayAccessComboBox;
    @FXML private TextField otherWayField;

    @FXML private TextField jobTitleField;
    @FXML private ComboBox<String> employmentTypeComboBox;
    @FXML private TextField companyNameField;
    @FXML private TextField locationField;
    @FXML private ComboBox<String> locationTypeComboBox;
    @FXML private CheckBox activityCheckBox;
    @FXML private DatePicker startToWorkPicker;
    @FXML private DatePicker endToWorkPicker;
    @FXML private TextArea jobDescriptionArea;

    @FXML private TextField schoolNameField;
    @FXML private TextField fieldOfStudyField;
    @FXML private DatePicker educationStartPicker;
    @FXML private DatePicker educationEndPicker;
    @FXML private TextField gradeField;
    @FXML private TextArea activitiesField;
    @FXML private TextArea educationDescriptionArea;

    @FXML private TextField certificateNameField;
    @FXML private TextField issuingOrgField;
    @FXML private DatePicker issueDatePicker;
    @FXML private DatePicker expiryDatePicker;
    @FXML private TextField credentialIdField;
    @FXML private TextField credentialUrlField;

    @FXML private Button profileImageButton;
    @FXML private Button backgroundImageButton;

    private UserDAO userDAO = new UserDAO();

    private File profileImageFile;
    private File backgroundImageFile;

    @FXML
    private void initialize() {
        phoneTypeComboBox.getItems().addAll("Mobile", "Home", "Work");
        birthdayAccessComboBox.getItems().addAll("Only Me", "My Connections", "My Network", "Everyone");
        employmentTypeComboBox.getItems().addAll("Full-Time", "Part-Time", "Self-Employed", "Freelance", "Contract", "Internship", "Apprenticeship", "Seasonal");
        locationTypeComboBox.getItems().addAll("On-Site", "Hybrid", "Remote");
    }

    @FXML
    private void chooseProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        profileImageFile = fileChooser.showOpenDialog(null);
        if (profileImageFile != null) {
        }
    }

    @FXML
    private void chooseBackgroundImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        backgroundImageFile = fileChooser.showOpenDialog(null);
        if (backgroundImageFile != null) {
        }
    }

    @FXML
    private void saveProfile() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String additionalName = additionalNameField.getText();
        String title = titleField.getText();
        String country = countryField.getText();
        String city = cityField.getText();
        String profession = professionField.getText();
        String email = emailField.getText();

        String imagePathProfile = saveImage(profileImageFile, "profileImages");
        String imagePathBackground = saveImage(backgroundImageFile, "backgroundImages");

        String phoneNumber = phoneNumberField.getText();
        String phoneType = phoneTypeComboBox.getValue();
        String address = addressField.getText();
        LocalDate birthday = birthdayPicker.getValue();
        String birthdayAccess = birthdayAccessComboBox.getValue();
        String otherWay = otherWayField.getText();

        String jobTitle = jobTitleField.getText();
        String employmentType = employmentTypeComboBox.getValue();
        String companyName = companyNameField.getText();
        String location = locationField.getText();
        String locationType = locationTypeComboBox.getValue();
        boolean activity = activityCheckBox.isSelected();
        LocalDate startToWork = startToWorkPicker.getValue();
        LocalDate endToWork = endToWorkPicker.getValue();
        String jobDescription = jobDescriptionArea.getText();

        String schoolName = schoolNameField.getText();
        String fieldOfStudy = fieldOfStudyField.getText();
        LocalDate educationStart = educationStartPicker.getValue();
        LocalDate educationEnd = educationEndPicker.getValue();

        Double grade = null;
        if (!gradeField.getText().isEmpty()) {
            try {
                grade = Double.parseDouble(gradeField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Grade must be a valid number.");
                return;
            }
        }

        String activities = activitiesField.getText();
        String educationDescription = educationDescriptionArea.getText();

        String originalEmail = GlobalUserSession.getEmail();

        User user = Main.getLoggedInUser();


        int connectionInfoId = userDAO.saveConnectionInfo(email, phoneNumber, phoneType, address, birthday, birthdayAccess, otherWay);
        if (connectionInfoId == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save connection information.");
            return;
        }

        int jobId = userDAO.saveJob(jobTitle, employmentType, companyName, location, locationType, activity, startToWork, endToWork, jobDescription);
        if (jobId == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save job information.");
            return;
        }

        int educationId = userDAO.saveEducation(schoolName, fieldOfStudy, educationStart, educationEnd, grade, activities, educationDescription);
        if (educationId == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save education information.");
            return;
        }

        user.setJobId(jobId);
        user.setEducationId(educationId);
        user.setConnectionInfoId(connectionInfoId);

//        showAlert(Alert.AlertType.INFORMATION, "Success", "Profile saved successfully.");
        System.out.println("Profile data saved. Navigating to home page...");
        mainApp.showHomePage();
    }

    private String saveImage(File imageFile, String directory) {
        if (imageFile != null) {
            File destDir = new File(System.getProperty("user.dir"), directory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File destFile = new File(destDir, imageFile.getName());
            try {
                Files.copy(imageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return destFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
