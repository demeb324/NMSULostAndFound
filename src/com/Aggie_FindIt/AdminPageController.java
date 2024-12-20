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
import javafx.beans.value.ObservableValue;

import static com.Aggie_FindIt.sql_link.*;

public class AdminPageController implements Initializable{
    @FXML
    private TableView<ObservableList<String>> logTableView;
    @FXML
    private TableColumn<ObservableList<String>, String> itemNameColumn, descriptionColumn, buildingColumn, categoryColumn, timeColumn;
    @FXML
    private Button reloadButton, removeItem;

    private ObservableList<ObservableList<String>> logEntries = FXCollections.observableArrayList();


    @FXML
    private VBox itemInputForm, itemSearchForm;
    @FXML
    private TextField itemNameField, SearchitemNameField;
    @FXML
    private TextField descriptionField, SearchdescriptionField;
    @FXML
    private ComboBox<String> buildingField, SearchbuildingField;
    @FXML
    private ComboBox<String> categoryField, SearchcategoryField;
  
 
    @FXML
    private AnchorPane middlePane;
    @FXML
    private TextArea returnText, procedure;


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

    private ObservableList<String> currentRowSelected;


    @FXML
    private void logout() {
        System.out.println("Returning to home page from Admin page");
        Aggie_FindIt.loadMainPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logTableView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends ObservableList<String>> observable, ObservableList<String> oldValue, ObservableList<String> newValue) -> {
                if (newValue != null) {
                    currentRowSelected = newValue;
                    populateLogButtons();

                    System.out.println("Selected row: " + newValue);
                }
            }
        );


        removeItem.setDisable(true);
        removeItem.setVisible(false);

        
        returnText.setOpacity(0);

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
        itemInputForm.setDisable(false);
    }

    @FXML
    private void handleCancelItemInput() {
        // Hide the form and clear the fields
        clearItemInputForm();
        itemInputForm.setVisible(false);
        itemInputForm.setDisable(true);
    }

    @FXML
    private void showItemSearchForm() {
        cancel();
        itemSearchForm.setVisible(true);
    }

    @FXML
    private void handleCancelSearch() {
        clearSearchForm();
        itemSearchForm.setVisible(false);
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
            updateLog();
        } else {
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add item. Please try again.");
            alert.show();
        }

        handleCancelItemInput();
        
    }

    private void clearItemInputForm() {
        itemNameField.clear();
        descriptionField.clear();
    }

    private void clearSearchForm() {
        SearchitemNameField.clear();
        SearchdescriptionField.clear();
        SearchbuildingField.getSelectionModel().clearSelection(); 
        SearchcategoryField.getSelectionModel().clearSelection();
    }

    
    @FXML
    private void itemReturn() {
        cancel();
        returnText.setDisable(true);
        returnText.setOpacity(100);
        procedure.setOpacity(100);
    }

    @FXML
    private void cancel() {
        returnText.setDisable(true);
        returnText.setOpacity(0);
        procedure.setOpacity(0);
        itemInputForm.setVisible(false);
        hideRequestInfo();
        itemSearchForm.setVisible(false);
    }


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
        String[] requests = requestData.split("\\n\\n");
        ObservableList<String> requestList = FXCollections.observableArrayList();
    
        for (String request : requests) {
            String[] fields = request.split("\\n");
    
            if (fields.length > 1) {
                StringBuilder formattedRequest = new StringBuilder();
                for (int i = 1; i < 3; i++) {
                    formattedRequest.append(fields[i]).append("\n");
                }
    
                requestList.add(formattedRequest.toString().trim());
            }
        }
    
        requestListView.setItems(requestList);
    }
    

    @FXML
    private void handleRequestSelection() {
        int selectedIndex = requestListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String allRequests = sql_link.getRequests();
            String[] requests = allRequests.split("\\n\\n");
    
            if (selectedIndex < requests.length) {
                String originalRequest = requests[selectedIndex];
                String[] fields = originalRequest.split("\\n");
                StringBuilder filteredDetails = new StringBuilder();
    
                for (int i = 0; i < fields.length; i++) {
                    if (i != 0) { 
                        filteredDetails.append(fields[i]).append("\n");
                    }
                }
    
                requestDetailsArea.setText(filteredDetails.toString().trim());
                selectedRequestId = extractRequestId(originalRequest);
            }
        }
    }

    @FXML
    private void performItemSearch() {
        String itemName = SearchitemNameField.getText().trim();
        String description = SearchdescriptionField.getText().trim();
        String building = SearchbuildingField.getValue() != null ? SearchbuildingField.getValue() : "";
        String category = SearchcategoryField.getValue() != null ? SearchcategoryField.getValue() : "";

        String searchResults = sql_link.itemSearch(itemName, description, building, category);

        logEntries.clear();

        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Results");
            alert.setHeaderText(null);
            alert.setContentText("No items found matching the search criteria.");
            alert.show();
            return;
        }

        String[] items = searchResults.split("\n");
        for (String item : items) {
            String[] fields = item.split(", ");
            if (fields.length >= 5) {
                String id = fields[0]; 
                String itemNameResult = fields[1];
                String descriptionResult = fields[2];
                String buildingResult = fields[3];
                String categoryResult = fields[4];
                String time = fields.length > 5 ? fields[5] : "N/A";

                ObservableList<String> row = FXCollections.observableArrayList(
                    itemNameResult, descriptionResult, buildingResult, categoryResult, time
                );
                logEntries.add(row);
            }
        }
    }

    
    @FXML
    private String extractRequestId(String request) {
        String[] fields = request.split("\\n");
        if (fields.length > 0) {
            return fields[0]; // Assume the object ID is the first line
        }
        return null;
    }

    @FXML
    private void populateLogButtons() {
        removeItem.setDisable(false);
        removeItem.setVisible(true);
    }

    @FXML
    private void removeItemButton() {
        String item = sql_link.itemSearch(currentRowSelected.get(0), currentRowSelected.get(1), currentRowSelected.get(2), currentRowSelected.get(3));
        System.out.println(sql_link.removeItem(item.strip().split(", ")[0].strip()));
        updateLog();
    }

}