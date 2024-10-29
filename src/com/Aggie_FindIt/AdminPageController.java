package com.Aggie_FindIt;

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
    private Label itemCategoryLabel, itemDateLabel;

    @FXML
    private DatePicker itemDate;

    @FXML
    private TextField itemDescription, itemColor; 

    @FXML 
    private Button completeReturn, cancelReturn;

    @FXML
    private TextArea returnText;

    private String[] categories = {"Phone", "Tablet", "Computer", "School supply", "Personal Item"};

    @FXML
    private void logout() {
        System.out.println("Returning to home page from Admin page");
        Aggie_FindIt.loadMainPage();
    }
    
}
