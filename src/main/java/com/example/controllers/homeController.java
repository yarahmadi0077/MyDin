package com.example.controllers;

import com.example.Main;
import com.example.dao.PostDAO;
import com.example.models.Post;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class homeController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private VBox feedBox;

    private PostDAO postDao = new PostDAO();

    @FXML
    private void initialize() {
        updateFeed();
    }

    public void updateFeed() {
        feedBox.getChildren().clear();
        List<Post> posts = postDao.getAllPosts();
        for (Post post : posts) {
            VBox postBox = new VBox();
            postBox.setSpacing(10);

            Text authorText = new Text("Author: " + post.getAuthor());
            Text contentText = new Text(post.getContent());
            Text timestampText = new Text("Posted on: " + post.getTimestamp().toString());

            postBox.getChildren().addAll(authorText, contentText, timestampText);

            if (post.getMediaPath() != null && !post.getMediaPath().isEmpty()) {
                File mediaFile = new File(post.getMediaPath());
                if (mediaFile.exists()) {
                    ImageView imageView = new ImageView();
                    Image mediaImage = new Image(mediaFile.toURI().toString());
                    imageView.setImage(mediaImage);
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);

                    postBox.getChildren().add(imageView);
                }
            }

            feedBox.getChildren().add(postBox);
        }
    }

    @FXML
    private void navigateToSearch() throws IOException {
        mainApp.showSearchPage();
    }

    @FXML
    private void navigateToProfile() throws IOException {
        mainApp.showProfilePage();
    }

    @FXML
    private void navigateToPost() throws IOException {
        mainApp.showPostPage();
    }
}
