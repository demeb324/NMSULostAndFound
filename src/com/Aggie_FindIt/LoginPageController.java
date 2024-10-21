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
        String username = usernameField.getText();
        // Implement login logic here
        System.out.println("Login attempted with username: " + username);

        //PRELIMIARY LOAD OF ADMIN PAGE TO LOAD 
        Aggie_FindIt.loadPage("Admin.fxml");
    }
}