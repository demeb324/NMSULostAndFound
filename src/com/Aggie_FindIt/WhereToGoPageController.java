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
    private TableView<ItemLog> itemTable;

    @FXML
    private TableColumn<ItemLog, String> buildingColumn;

    @FXML
    private TableColumn<ItemLog, String> roomColumn;

    public void initialize() {
        // Initialize table columns
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("building"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));

        // Populate the table with sample data
        itemTable.setItems(getSampleData());
    }

    private ObservableList<ItemLog> getSampleData() {
        return FXCollections.observableArrayList(
            new ItemLog("Hardman and Jacobs", "123"),
            new ItemLog("Science Hall", "456"),
            new ItemLog("Corbett Center", "789"),
            new ItemLog("Corbett Center", "789")
        );
    }

    public static class ItemLog {
        private final String building;
        private final String room;

        public ItemLog(String building, String room) {
            this.building = building;
            this.room = room;
        }

        public String getBuilding() {
            return building;
        }

        public String getRoom() {
            return room;
        }
    }


    @FXML
    private void goBack() {
        System.out.println("goBack method called");
        Aggie_FindIt.loadPage("StudentPage.fxml");
    }
}