package com.Aggie_FindIt;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WhereToGoPageController {
    @FXML
    private TableView<String[]> tableView; // Using String[] for each row
    @FXML
    private TableColumn<String[], String> buildingColumn;
    @FXML
    private TableColumn<String[], String> roomNumberColumn;

    @FXML
    public void initialize() {
        // Initialize the columns
        buildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        roomNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));

        // Sample data as arrays
        ObservableList<String[]> rooms = FXCollections.observableArrayList(
            new String[]{"Science Hall", "101"},
            new String[]{"Hardman and Jacobs", "202"},
            new String[]{"Corbett Center Student Union", "303"}
        );

        // Populate the table
        tableView.setItems(rooms);
    }

    @FXML
    private void goBack() {
        System.out.println("goBack method called");
        Aggie_FindIt.loadPage("StudentPage.fxml");
    }
}