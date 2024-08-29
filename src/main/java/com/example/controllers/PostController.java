package com.example.controllers;

import com.example.Main;
import com.example.dao.MediaDAO;
import com.example.dao.PostDAO;
import com.example.models.Media;
import com.example.models.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
public class PostController {
    private Main mainApp;
    private String mediaPath;
    private int mediaId;
    private homeController homeController;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setHomeController(homeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private TextArea postContent;

    private PostDAO postDao = new PostDAO();
    private MediaDAO mediaDao = new MediaDAO();

    @FXML
    private void handlePost(ActionEvent event) {
        String content = postContent.getText();
        String author = mainApp.getLoggedInUser().getFullName();
        if (content.length() > 3000) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Post content exceeds 3000 characters!");
            alert.showAndWait();
        } else {
            Post post = new Post(author, content, mediaId);
            postDao.addPost(post);

            postContent.clear();
            mediaId = 0;

            if (homeController != null) {
                homeController.updateFeed();
                mainApp.showHomePage();
            } else {
                System.err.println("HomeController is null!");
            }
        }
    }

    @FXML
    private void handleAddMedia(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov")
        );
        File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (selectedFile != null) {
            mediaPath = selectedFile.getAbsolutePath();
            Media media = new Media(mediaPath);
            mediaId = mediaDao.addMedia(media);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Media Added");
            alert.setHeaderText(null);
            alert.setContentText("Media file added: " + selectedFile.getName());
            alert.showAndWait();
        }
    }
}
