package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class RequestItemController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField itemNameField;
    @FXML
    private TextArea itemDescriptionArea;
    @FXML
    private ComboBox<String> categoryField;
    @FXML
    private ComboBox<String> locationField;
    @FXML
    private ComboBox<String> timeField;
    @FXML
    private Label statusLabel;

    // Initialize dropdown options
    @FXML
    public void initialize() {
        categoryField.getItems().addAll("Electronics", "Clothing", "Books", "Keys", "Accessories", "Others");
        locationField.getItems().addAll("Library", "Cafeteria", "Gym", "Classroom", "Dormitory", "Other");
        timeField.getItems().addAll("Morning", "Afternoon", "Evening", "Night");
    }

    @FXML
    private void submitRequest(ActionEvent event) {
        String name = nameField.getText();
        String studentId = studentIdField.getText();
        String email = emailField.getText();
        String itemName = itemNameField.getText();
        String itemDescription = itemDescriptionArea.getText();
        String category = categoryField.getValue();
        String location = locationField.getValue();
        String time = timeField.getValue();

        // Validate inputs
        if (name.isEmpty() || studentId.isEmpty() || email.isEmpty() || itemName.isEmpty() || 
            itemDescription.isEmpty() || category == null || location == null || time == null) {
            statusLabel.setText("Please fill out all fields.");
        } else {
            statusLabel.setText("Request submitted successfully!");
        }
    }

    //go back to previous page
    @FXML
    private void goBack(){
        System.out.println("Returning to previous from RequestItem page");
        Aggie_FindIt.loadStudentPage();
    }


    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from SignUp page");
        Aggie_FindIt.loadMainPage();
    }
}
