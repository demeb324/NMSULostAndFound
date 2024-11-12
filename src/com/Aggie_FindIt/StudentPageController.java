package com.Aggie_FindIt;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.Aggie_FindIt.sql_link.itemSearch;

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
        // Get the item data from itemSearch
        String item = itemSearch("", "", "", "");
        
        // Split each line to get individual items
        String[] items = item.split("\n");
        
        // Prepare the ObservableList to store ItemLog objects
        ObservableList<ItemLog> itemLogs = FXCollections.observableArrayList();
        
        // Extract descriptions and create ItemLog objects
        for (String line : items) {
            // Split each line by commas to get individual details
            String[] desc = line.split(",");

            // Check if there is at least a description and a time
            if (desc.length > 2) {
                String description = desc[2].trim(); // Second element as the location
                String location = desc[4].trim();     // Third element as the time

                // Add a new ItemLog with location and time
                itemLogs.add(new ItemLog(description, location));
            }
        }
        
        return itemLogs;
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
        Aggie_FindIt.loadPage("RequestItem.fxml");
    }


    @FXML
    private void whereToGo() {
        System.out.println("whereToGo method called");
        Aggie_FindIt.loadPage("WhereToGo.fxml");
    }
}
