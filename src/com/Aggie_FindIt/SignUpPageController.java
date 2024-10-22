package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static com.Aggie_FindIt.sql_link.*;

public class SignUpPageController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private ComboBox<String> buildingComboBox;

    @FXML
    private void initialize() {
        adminCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            buildingComboBox.setDisable(!newValue);
        });
    }

    @FXML
    private void signUp() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Incomplete Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields before signing up.");
            alert.showAndWait();
            return;
//            messageLabel.setText("Please fill in all fields.");
//            return;
        }

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match. Please try again.");
            alert.showAndWait();
            return;
//            messageLabel.setText("Passwords do not match.");
//            return;
        }

        if (adminCheckBox.isSelected()) {
            String selectedBuilding = buildingComboBox.getValue();
            if (selectedBuilding == null || selectedBuilding.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Building Not Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a building if signing up as an admin.");
                alert.showAndWait();
                return;
            }
            if(addAdminUser(fullName,password,selectedBuilding,email)){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Sign up successful for admin: " + fullName);
            alert.show();
            }
            else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("User already exists");
                alert.setHeaderText(null);
                alert.setContentText("I'm sorry, the username: " + fullName + " is taken, please use another.");
                alert.showAndWait();
            }
        }
        else {
           if(addStudentUser(fullName, password, email)){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Sign up successful for Student: " + fullName);
            alert.show();
           }
           else {
               Alert alert = new Alert(AlertType.WARNING);
               alert.setTitle("User already exists");
               alert.setHeaderText(null);
               alert.setContentText("I'm sorry, the username: " + fullName + " is taken, please use another.");
               alert.showAndWait();
           }
        }

        // Here you would typically add code to create a new user account
        // For now, we'll just print a success message
//        System.out.println("Sign up successful for: " + fullName);
    }

    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from SignUp page");
        Aggie_FindIt.loadMainPage();
    }
}