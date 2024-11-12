package com.Aggie_FindIt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
public class AdminIntermediatePageController implements Initializable{
    @FXML
    private ChoiceBox<String> buildingsDropdown;
    private String[] buildings = {"Hardman Jacobs Learning Center", "Zuhl Library", "Corbett center", "Science Hall"};
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        buildingsDropdown.getItems().addAll(buildings);
    }
    @FXML
    private void submit() {
        Aggie_FindIt.loadPage("Admin.fxml");
    }
    
    
    
}