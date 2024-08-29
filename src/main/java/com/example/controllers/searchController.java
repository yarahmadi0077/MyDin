package com.example.controllers;

import com.example.Main;
import com.example.dao.UserDAO;
import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class searchController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> resultListView;

    private UserDAO userDAO;
    private List<User> searchResults;

    public searchController() {
        userDAO = new UserDAO();
    }

    @FXML
    private void search() {
        String query = searchField.getText();
        searchResults = userDAO.searchUsers(query);
        resultListView.getItems().clear();
        for (User user : searchResults) {
            if (user.getCountry() == null)
                user.setCountry("Iran");
            if (user.getCity() == null)
                user.setCity("Tehran");
            if (user.getProfession() == null)
                user.setProfession("Programmer");
            resultListView.getItems().add(user.getFullName() + "     " + user.getEmail() + "    " + user.getCountry() + "   " + user.getCity() + "      " + user.getProfession());
        }
    }

    @FXML
    private void viewProfile(MouseEvent event) {
        String selectedUserInfo = resultListView.getSelectionModel().getSelectedItem();
        if (selectedUserInfo != null) {
            for (User user : searchResults) {
                String userInfo = user.getFullName() + "     " + user.getEmail() + "    " + user.getCountry() + "   " + user.getCity() + "      " + user.getProfession();
                if (userInfo.equals(selectedUserInfo)) {
                    mainApp.showUserProfile(user);
                    break;
                }
            }
        }
    }
}
