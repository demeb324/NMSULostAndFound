package com.Aggie_FindIt;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.Aggie_FindIt.sql_link.*;

public class AdminPageController implements Initializable{
    @FXML
    private TableView<ObservableList<String>> logTableView;
    @FXML
    private TableColumn<ObservableList<String>, String> itemNameColumn, descriptionColumn, buildingColumn, categoryColumn, timeColumn;
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
    private Button markFoundButton;
    @FXML
    private Button markNotFoundButton;
    @FXML
    private VBox requestInfoContainer;
    @FXML
    private TextArea requestDisplayArea; 
    @FXML
    private Label requestLabel;
    @FXML
    private ListView<String> requestListView;
    @FXML
    private TextArea requestDetailsArea;

    private String selectedRequestId;


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

        requestListView.setVisible(false);
        requestDetailsArea.setVisible(false);
        markFoundButton.setVisible(false);
        markNotFoundButton.setVisible(false);
    }

    @FXML
    private void updateLog() {
        String logContent = getRecentItems();

        String[] items = logContent.split("\n");
        logEntries.clear();

        for (String item : items) {
            String[] fields = item.split(", ");
            if (fields.length == 5) {
                String itemName = fields[0].split(": ")[1];
                String description = fields[1].split(": ")[1];
                String building = fields[2].split(": ")[1];
                String category = fields[3].split(": ")[1];
                String time = fields[4].split(": ")[1]; 

                ObservableList<String> row = FXCollections.observableArrayList(itemName, description, building, category, time);
                logEntries.add(row);
            }
        }
    }

    @FXML
    private void showItemInputForm() {
        cancel();
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
        itemInputForm.setVisible(false);
        hideRequestInfo();
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
    private void submitItem() {}


    @FXML
    private void showRequestInfo() {
        cancel();
        requestListView.setVisible(true);
        requestDetailsArea.setVisible(true);
        markFoundButton.setVisible(true);
        markNotFoundButton.setVisible(true);

        String allRequests = sql_link.getRequests();
        displayRequests(allRequests);
    }
    
    @FXML
    private void hideRequestInfo(){
        requestListView.setVisible(false);
        requestDetailsArea.setVisible(false);
        markFoundButton.setVisible(false);
        markNotFoundButton.setVisible(false);
    }


    @FXML
    private void markAsFound() {
        if (selectedRequestId != null) {
            sql_link.markRequest(selectedRequestId, 1);
            clearSelection();
        }
    }

    @FXML
    private void markAsNotFound() {
        if (selectedRequestId != null) {
            sql_link.markRequest(selectedRequestId, 2);
            clearSelection();
        }
    }

    private void clearSelection() {
        requestListView.getSelectionModel().clearSelection();
        requestDetailsArea.clear();
        selectedRequestId = null;
    }


    private void displayRequests(String requestData) {
        // Split requests by two new lines
        String[] requests = requestData.split("\\n\\n");
        ObservableList<String> requestList = FXCollections.observableArrayList();
    
        for (String request : requests) {
            // Extract fields (assuming each field is separated by a single new line)
            String[] fields = request.split("\\n");
    
            // Skip requests that don't have enough fields (adjust if necessary)
            if (fields.length > 1) {
                // Exclude the object ID (assume it's the first field)
                StringBuilder formattedRequest = new StringBuilder();
                for (int i = 1; i < fields.length; i++) {
                    formattedRequest.append(fields[i]).append("\n");
                }
    
                // Add formatted request (excluding object ID) to the list
                requestList.add(formattedRequest.toString().trim());
            }
        }
    
        requestListView.setItems(requestList);
    }
    

    @FXML
    private void handleRequestSelection() {
        int selectedIndex = requestListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedRequest = requestListView.getSelectionModel().getSelectedItem();
            requestDetailsArea.setText(selectedRequest);

            String allRequests = sql_link.getRequests();
            String[] requests = allRequests.split("\\n\\n");

            if (selectedIndex < requests.length) {
                String originalRequest = requests[selectedIndex];
                selectedRequestId = extractRequestId(originalRequest);
            }
        }
    }
    
    private String extractRequestId(String request) {
        String[] fields = request.split("\\n");
        if (fields.length > 0) {
            return fields[0]; // Assume the object ID is the first line
        }
        return null;
    }

}