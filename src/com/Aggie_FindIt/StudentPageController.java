package com.Aggie_FindIt;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentPageController {
    
    //item log chart goes here, has building and time on y axis and item number on x axis
    @FXML
    private TableView<ItemLog> itemTable;

    @FXML
    private TableColumn<ItemLog, String> buildingColumn;

    @FXML
    private TableColumn<ItemLog, String> timeColumn;

    public void initialize() {
        // Initialize table columns
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("building"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        // Populate the table with sample data
        itemTable.setItems(getSampleData());
    }

    private ObservableList<ItemLog> getSampleData() {
        return FXCollections.observableArrayList(
            new ItemLog("Library", "10:00 AM"),
            new ItemLog("Gym", "2:30 PM"),
            new ItemLog("Cafeteria", "12:15 PM")
        );
    }

    public static class ItemLog {
        private final String building;
        private final String time;

        public ItemLog(String building, String time) {
            this.building = building;
            this.time = time;
        }

        public String getBuilding() {
            return building;
        }

        public String getTime() {
            return time;
        }
    }

    @FXML
    private void logout() {
        System.out.println("logout method called");
        Aggie_FindIt.loadMainPage();
    }

    @FXML
    private void requestItem() {
        System.out.println("requestItem method called");
        Aggie_FindIt.loadPage("requestItemPage.xml");
    }


    @FXML
    private void whereToGo() {
        System.out.println("whereToGo method called");
        Aggie_FindIt.loadPage("whereToGoPage.xml");
    }
}