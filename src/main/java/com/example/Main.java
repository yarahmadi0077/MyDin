package com.example;

import com.example.controllers.*;
import com.example.database.DatabaseConnection;
import com.example.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    private Stage primaryStage;
    private homeController homeController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showWelcomePage();
//        showHomePage();
    }

    public void showWelcomePage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/fxml/Welcome.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome Page");
            primaryStage.show();

            WelcomeController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSignUpPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/fxml/SignUp.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            Stage signUpStage = new Stage();
            signUpStage.setScene(scene);
            signUpStage.setTitle("Sign Up");
            signUpStage.initModality(Modality.APPLICATION_MODAL);

            SignUpController controller = loader.getController();
            controller.setSignUpStage(signUpStage);
            controller.setMainApp(this);

            signUpStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/fxml/profile.fxml"));
            ScrollPane root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Profile Page");

            ProfileController controller = loader.getController();
            controller.setMainApp(this);
            System.out.println("ProfileController: setMainApp called");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/fxml/home.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root, 350, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Home Page");
            primaryStage.show();

            homeController = loader.getController();
            homeController.setMainApp(this);
            System.out.println("homeController: setMainApp called");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSearchPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/fxml/Search.fxml"));
            VBox root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Search Page");

            searchController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showUserProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fxml/UserProfile.fxml"));
            Parent root = loader.load();

            UserProfileController controller = loader.getController();
            controller.setMainApp(this);
            System.out.println(user.getEmail());
            user.setId(getUserId(user.getEmail()));
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("User Profile");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getUserId(String email) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM users WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }

    public void showPostPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/fxml/Post.fxml"));
            VBox postPage = (VBox) loader.load();

            Scene scene = new Scene(postPage);
            primaryStage.setScene(scene);
            primaryStage.show();

            PostController controller = loader.getController();
            controller.setMainApp(this);
            controller.setHomeController(homeController);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static User loggedInUser;

    static public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) throws SQLException {
        loggedInUser.setId(getUserId(loggedInUser.getEmail()));
        System.out.println(loggedInUser.getEmail());
        this.loggedInUser = loggedInUser;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
