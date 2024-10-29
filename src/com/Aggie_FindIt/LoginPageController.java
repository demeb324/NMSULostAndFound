package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import static com.Aggie_FindIt.sql_link.login;

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
    private void Login() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        int attempt = login(usernameField.getText(), passwordField.getText());
        switch(attempt) {
            case 3:
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Login successful for Student: " + usernameField.getText());
                alert.show();
                break;
            case 2:
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Login successful for Admin: " + usernameField.getText());
                alert.show();
                Aggie_FindIt.loadPage("Admin.fxml");
                break;
            case 1:
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Login not successful for: " + usernameField.getText());
                alert.show();
                break;
            case 0:
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("User Not Found");
                alert.show();
            default:
                break;
        }
    }
}