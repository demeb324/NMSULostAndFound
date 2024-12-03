package com.Aggie_FindIt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
public class AdminIntermediatePageController{
    @FXML
    private void submit() {
        Aggie_FindIt.loadPage("Admin.fxml");
    }

    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from About page");
        Aggie_FindIt.loadMainPage();
    } 
}