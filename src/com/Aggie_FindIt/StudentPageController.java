package com.Aggie_FindIt;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.Aggie_FindIt.sql_link.getRecentItemsStudent;

public class StudentPageController {
    
    //item log chart goes here, has building and time on y axis and item number on x axis
    @FXML
    private TableView<ItemLog> itemTable;

    @FXML
    private TableColumn<ItemLog, String> buildingColumn;

    @FXML
    private TableColumn<ItemLog, String> timeColumn;

    @FXML
    private TableColumn<ItemLog, String> catagoryColumn;

    public void initialize() {
        // Initialize table columns
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("building"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        catagoryColumn.setCellValueFactory(new PropertyValueFactory<>("catagory"));

        // Populate the table with sample data
        itemTable.setItems(getSampleData());
    }

    private ObservableList<ItemLog> getSampleData() {
        // Get the item data from itemSearch
        String item = getRecentItemsStudent();
        System.out.println(item);

        // Split each line to get individual items
        String[] items = item.split("\n");
        
        // Prepare the ObservableList to store ItemLog objects
        ObservableList<ItemLog> itemLogs = FXCollections.observableArrayList();

        // Extract descriptions and create ItemLog objects
        for (String line : items) {
            // Split each line by commas to get individual details
            String[] desc = line.split(", ");

            // Check if there is at least a description and a time
            if (desc.length > 2) {
                String building = desc[0].trim(); // Second element as the location
                String[] splitTime = desc[1].trim().split(" ");     // Third element as the time
                String time = splitTime[0] + "-" + splitTime[1] + "-" + splitTime[2] + "-" + findHour(splitTime[3]);
                String catagory = desc[2].trim();     // Third element as the time
                System.out.println(catagory);
                // Add a new ItemLog with location and time
                itemLogs.add(new ItemLog(building, time, catagory));
            }
        }
        
        return itemLogs;
    }

    public static String findHour(String time) {
        String hourString = time.split(":")[0];
        int hour = Integer.parseInt(hourString);
        if (hour == 0) {
            return "12" + "AM";
        } else if (hour < 12) {
            return hour + "AM";
        } else if (hour == 12) {
            return hour + "PM";
        } else {
            return (hour - 12) + "PM";
        }
    }

    public static class ItemLog {
        private final String building;
        private final String time;
        private final String catagory;


        public ItemLog(String building, String time, String catagory) {
            this.building = building;
            this.time = time;
            this.catagory = catagory;
        }

        public String getBuilding() {
            return building;
        }

        public String getTime() {
            return time;
        }

        public String getCatagory() {
            return catagory;
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
