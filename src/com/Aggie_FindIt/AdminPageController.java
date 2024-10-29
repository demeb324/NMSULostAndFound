package com.Aggie_FindIt;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminPageController implements Initializable{

    @FXML
    private ChoiceBox<String> itemCategory;

    @FXML
    private Label itemCategoryLabel; 
    
    @FXML
    private Label itemDateLabel;

    @FXML
    private DatePicker itemDate;

    @FXML
    private TextField itemDescription;

    @FXML
    private TextField itemColor; 

    @FXML 
    private Button completeReturn;
    
    @FXML
    private Button cancelReturn;

    @FXML
    private TextArea returnText;

    private String[] categories = {"Phone", "Tablet", "Computer", "School supply", "Personal Item"};

    @FXML
    private void logout() {
        System.out.println("Returning to home page from Admin page");
        Aggie_FindIt.loadMainPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemCategory.getItems().addAll(categories);
    }

    @FXML
    private void addItem() {
        itemDescription.setDisable(false);
        itemColor.setDisable(false);
        itemCategory.setDisable(false);
        itemCategoryLabel.setDisable(false);
        itemDate.setDisable(false);
        itemDateLabel.setDisable(false);
        completeReturn.setDisable(true);
        cancelReturn.setDisable(true);
        returnText.setDisable(true);
    }

    @FXML
    private void itemReturn() {
        itemDescription.setDisable(true);
        itemColor.setDisable(true);
        itemCategory.setDisable(true);
        itemCategoryLabel.setDisable(true);
        itemDate.setDisable(true);
        itemDateLabel.setDisable(true);
        completeReturn.setDisable(false);
        cancelReturn.setDisable(false);
        returnText.setDisable(false);
    }
    
}
