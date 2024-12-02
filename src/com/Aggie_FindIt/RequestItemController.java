package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static com.Aggie_FindIt.sql_link.addRequest;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

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
    private DatePicker timeField;
    @FXML
    private Label statusLabel;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private ComboBox<String> amPmField;
    @FXML
    private Button markFoundButton;
    @FXML
    private Button markNotFoundButton;
    @FXML
    private VBox requestInfoContainer; 
    @FXML
    private TextArea requestDisplayArea; 
    @FXML
    private Label requestLabel;


    // Initialize dropdown options
    @FXML
    public void initialize() {
        categoryField.getItems().addAll("Electronics", "Clothing", "Books", "Keys", "Accessories", "Others");
        locationField.getItems().addAll("Library", "Cafeteria", "Gym", "Classroom", "Dormitory", "Other");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 6);
        hourSpinner.setValueFactory(valueFactory);
        amPmField.getItems().addAll("AM","PM");
        amPmField.setValue("AM");
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
        LocalDate date = timeField.getValue();
        Integer hour = hourSpinner.getValue(); 
        String amPm = amPmField.getValue();

        // Validate inputs
        if (name.isEmpty() || studentId.isEmpty() || email.isEmpty() || itemName.isEmpty() || 
            itemDescription.isEmpty() || category == null || location == null || date == null || hour == null || amPm == null) {
            statusLabel.setText("Please fill out all fields.");
        } else if (!studentId.matches("\\d{9}")) {
            statusLabel.setText("Invalid ID");
        } else {
            if (amPm.equals("PM") && hour != 12) {
                hour += 12; 
            } else if (amPm.equals("AM") && hour == 12) {
                hour = 0; 
            }
            LocalDateTime dateTime = date.atTime(hour, 0);
            Date requestTime = java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            boolean success = addRequest(name, studentId, email, itemName, itemDescription, category, location, requestTime);

            if (success) {
                statusLabel.setText("Request submitted successfully!");
                clearFields(); 
            } else {
                statusLabel.setText("Failed to submit the request. Please try again.");
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        studentIdField.clear();
        emailField.clear();
        itemNameField.clear();
        itemDescriptionArea.clear();
        categoryField.setValue(null);
        locationField.setValue(null);
        timeField.setValue(null);hourSpinner.getValueFactory().setValue(6); 
        amPmField.setValue("AM");
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
