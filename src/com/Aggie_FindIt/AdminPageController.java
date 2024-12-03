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
    private Button reloadButton, removeItem, editItem;

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
    private VBox lookupForm;
    @FXML
    private ChoiceBox<String> itemCategory;
    @FXML
    private Label itemCategoryLabel, itemDateLabel;
    @FXML
    private DatePicker itemDate;
    @FXML
    private TextField itemDescription;
    @FXML
    private TextField itemColor;
    @FXML
    private TextField itemSearchName; 
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
        editItem.setDisable(true);
        editItem.setVisible(false);

        addItem.setOpacity(0);
        itemDescription.setOpacity(0);
        itemColor.setOpacity(0);
        itemCategory.setOpacity(0);
        itemCategoryLabel.setOpacity(0);
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
        clearItemInputForm();
        itemInputForm.setVisible(false);
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

        boolean success = sql_link.addItem(itemName, description, building, category);

        if (success) {
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Item added successfully!");
            alert.show();
            clearItemInputForm();
            itemInputForm.setVisible(false);
            updateLog();
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

    private void clearSearchForm() {
        SearchitemNameField.clear();
        SearchdescriptionField.clear();
        SearchbuildingField.getSelectionModel().clearSelection(); 
        SearchcategoryField.getSelectionModel().clearSelection();
    }

    @FXML
    private void addItem() {
        cancel();
        itemDescription.setVisible(true);
        itemDescription.setOpacity(100);
        itemColor.setVisible(true);
        itemColor.setOpacity(100);
        itemCategory.setVisible(true);
        itemCategory.setOpacity(100);
        itemCategoryLabel.setVisible(true);
        itemCategoryLabel.setOpacity(100);
        addItem.setVisible(true);
        addItem.setOpacity(100);
        cancel.setVisible(true);
        cancel.setOpacity(100);
    }
    @FXML
    private void itemReturn() {
        cancel();
        completeReturn.setVisible(true);
        completeReturn.setOpacity(100);
        cancel.setVisible(true);
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
        lookupForm.setVisible(false);
        hideRequestInfo();
        itemSearchForm.setVisible(false);
    }

    @FXML
    private void itemSearch() {
        cancel();
        lookupForm.setVisible(true);
    }

    public void submitItem(){}


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
            return fields[0]; 
        }
        return null;
    }

    @FXML
    private void populateLogButtons() {
        removeItem.setVisible(true);
        removeItem.setDisable(false);
        editItem.setVisible(true);
        editItem.setDisable(false);
    }

    @FXML
    private void removeItemButton() {
        String item = sql_link.itemSearch(currentRowSelected.get(0), currentRowSelected.get(1), currentRowSelected.get(2), currentRowSelected.get(3));
        System.out.println(sql_link.removeItem(item.strip().split(", ")[0].strip()));
        updateLog();
    }

}