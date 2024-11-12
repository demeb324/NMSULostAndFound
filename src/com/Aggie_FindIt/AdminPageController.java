package com.Aggie_FindIt;

import javafx.fxml.FXML; 
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.Aggie_FindIt.sql_link.*;

public class AdminPageController {
    @FXML
    private TableView<ObservableList<String>> logTableView;
    @FXML
    private TableColumn<ObservableList<String>, String> itemNameColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> descriptionColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> buildingColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> categoryColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> timeColumn; // New column for time
    @FXML
    private Button reloadButton; 

    private ObservableList<ObservableList<String>> logEntries = FXCollections.observableArrayList();



    @FXML
    private VBox itemInputForm;
    @FXML
    private TextField itemNameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<String> buildingField;
    @FXML
    private ComboBox<String> categoryField;

    @FXML
    private void logout() {
        System.out.println("Returning to home page from Admin page");
        Aggie_FindIt.loadMainPage();
    }


    @FXML
    public void initialize() {
        logTableView.setItems(logEntries);

        // Set up column value factories to display data in each column
        itemNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        descriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        buildingColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
        categoryColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
        timeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4))); // For time
        
        updateLog();
        reloadButton.setOnAction(event -> updateLog());
    }

    @FXML
    private void updateLog() {
        String logContent = getRecentItems();
        System.out.println(logContent);

        // Split the string into individual items
        String[] items = logContent.split("\n");
        logEntries.clear();

        // Parse each item and split it into columns
        for (String item : items) {
            // Extract the fields for Item Name, Description, Building, and Category from each item
            String[] fields = item.split(", ");
            if (fields.length == 5) {
                // Get the individual field values from the split string
                String itemName = fields[0].split(": ")[1];
                String description = fields[1].split(": ")[1];
                String building = fields[2].split(": ")[1];
                String category = fields[3].split(": ")[1];
                String time = fields[4].split(": ")[1]; // Extract the time

                // Create an observable list of fields and add it to the table
                ObservableList<String> row = FXCollections.observableArrayList(itemName, description, building, category, time);
                logEntries.add(row);
            }
        }
    }

    @FXML
    private void showItemInputForm() {
        // Show the item input form in the main content area
        itemInputForm.setVisible(true);
    }

    @FXML
    private void handleCancelItemInput() {
        // Hide the form and clear the fields
        clearItemInputForm();
        itemInputForm.setVisible(false);
    }

    @FXML
    private void handleSubmitItem() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String itemName = itemNameField.getText();
        String description = descriptionField.getText();
        String building = buildingField.getValue();
        String category = categoryField.getValue();

        if (itemName.isEmpty() || description.isEmpty() || category == null || building == null) {
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.show();
            return;
        }

        // Attempt to add the item to the database
        boolean success = sql_link.addItem(itemName, description, building, category);

        if (success) {
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Item added successfully!");
            alert.show();
            clearItemInputForm();
            itemInputForm.setVisible(false);
        } else {
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add item. Please try again.");
            alert.show();
        }
    }

    private void clearItemInputForm() {
        itemNameField.clear();
        descriptionField.clear();
    }
}
