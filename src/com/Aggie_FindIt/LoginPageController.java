package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void backToHome() {
        System.out.println("backToHome method called");
        Aggie_FindIt.loadMainPage();
    }

    @FXML
    private void login() {
        // Implement login logic here
        System.out.println("Login attempted with username: " + usernameField.getText());
        Aggie_FindIt.loadPage("StudentPage.fxml");
    }
}