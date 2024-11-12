package com.Aggie_FindIt;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
  
public class AdminPageController implements Initializable{
    @FXML
    private ChoiceBox<String> itemCategory;
    @FXML
    private Label itemCategoryLabel, itemDateLabel;
    @FXML
    private DatePicker itemDate;
    @FXML
    private TextField itemDescription, itemColor, itemSearchName; 
    @FXML 
    private Button completeReturn, cancel, addItem,searchButton;
    @FXML
    private AnchorPane middlePane;
    @FXML
    private TextArea returnText, procedure;
    private String[] categories = {"Phone", "Tablet", "Computer", "School supply", "Personal Item"};


    @FXML
    private void logout() {
        System.out.println("Returning to home page from Admin page");
        Aggie_FindIt.loadMainPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addItem.setOpacity(0);
        itemDescription.setOpacity(0);
        itemColor.setOpacity(0);
        itemCategory.setOpacity(0);
        itemCategoryLabel.setOpacity(0);
        itemDate.setOpacity(0);
        itemDateLabel.setOpacity(0);
        completeReturn.setOpacity(0);
        cancel.setOpacity(0);
        returnText.setOpacity(0);
        itemCategory.getItems().addAll(categories);

        itemSearchName.setOpacity(0);
        searchButton.setOpacity(0);
        procedure.setOpacity(0);
  
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
    @FXML
    private void addItem() {
        cancel();
        itemDescription.setDisable(false);
        itemDescription.setOpacity(100);
        itemColor.setDisable(false);
        itemColor.setOpacity(100);
        itemCategory.setDisable(false);
        itemCategory.setOpacity(100);
        itemCategoryLabel.setDisable(false);
        itemCategoryLabel.setOpacity(100);
        itemDate.setDisable(false);
        itemDate.setOpacity(100);
        itemDateLabel.setDisable(false);
        itemDateLabel.setOpacity(100);
        addItem.setDisable(false);
        addItem.setOpacity(100);
        cancel.setDisable(false);
        cancel.setOpacity(100);
    }
    @FXML
    private void itemReturn() {
        cancel();
        completeReturn.setDisable(false);
        completeReturn.setOpacity(100);
        cancel.setDisable(false);
        cancel.setOpacity(100);
        returnText.setDisable(true);
        returnText.setOpacity(100);
        procedure.setOpacity(100);
    }

    @FXML
    private void cancel() {
        itemDescription.setDisable(true);
        itemDescription.setOpacity(0);
        itemColor.setDisable(true);
        itemColor.setOpacity(0);
        itemCategory.setDisable(true);
        itemCategory.setOpacity(0);
        itemCategoryLabel.setDisable(true);
        itemCategoryLabel.setOpacity(0);
        itemDate.setDisable(true);
        itemDate.setOpacity(0);
        itemDateLabel.setDisable(true);
        itemDateLabel.setOpacity(0);
        addItem.setDisable(true);
        addItem.setOpacity(0);
        completeReturn.setDisable(true);
        completeReturn.setOpacity(0);
        cancel.setDisable(true);
        cancel.setOpacity(0);
        returnText.setDisable(true);
        returnText.setOpacity(0);
        itemSearchName.setDisable(true);
        itemSearchName.setOpacity(0);
        searchButton.setDisable(true);
        searchButton.setOpacity(0);
        procedure.setOpacity(0);
    }

    @FXML
    private void itemSearch() {
        cancel();
        itemDescription.setDisable(false);
        itemDescription.setOpacity(100);
        itemCategory.setDisable(false);
        itemCategory.setOpacity(100);
        itemCategoryLabel.setDisable(false);
        itemCategoryLabel.setOpacity(100);
        cancel.setDisable(false);
        cancel.setOpacity(100);
        itemSearchName.setDisable(false);
        itemSearchName.setOpacity(100);
        searchButton.setDisable(false);
        searchButton.setOpacity(100);
    }

    @FXML
    private void submitItem(){
    }


}
