package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class ContactUsPageController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea messageArea;

    @FXML
    private Label statusLabel;

    @FXML
    private void submitForm() {
        String name = nameField.getText();
        String email = emailField.getText();
        String message = messageArea.getText();

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        System.out.println("Contact form submitted by: " + name);
        statusLabel.setText("Message sent successfully!");
    }

    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from Contact Us page");
        Aggie_FindIt.loadMainPage();
    }
}