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
    private Label itemCategoryLabel, itemDateLabel;
    @FXML
    private DatePicker itemDate;
    @FXML
    private TextField itemDescription, itemColor; 
    @FXML 
    private Button completeReturn, cancel, addItem;
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
        addItem.setOpacity(0);
        itemDescription.setOpacity(0);
        itemColor.setOpacity(0);
        itemCategory.setOpacity(0);
        itemCategoryLabel.setOpacity(0);
        itemDate.setOpacity(0);
        itemDateLabel.setOpacity(0);
        completeReturn.setOpacity(0);
        cancel.setOpacity(0);
        returnText.setOpacity(0);
        itemCategory.getItems().addAll(categories);
    }
    @FXML
    private void addItem() {
        itemDescription.setDisable(false);
        itemDescription.setOpacity(100);
        itemColor.setDisable(false);
        itemColor.setOpacity(100);
        itemCategory.setDisable(false);
        itemCategory.setOpacity(100);
        itemCategoryLabel.setDisable(false);
        itemCategoryLabel.setOpacity(100);
        itemDate.setDisable(false);
        itemDate.setOpacity(100);
        itemDateLabel.setDisable(false);
        itemDateLabel.setOpacity(100);
        addItem.setDisable(false);
        addItem.setOpacity(100);
        completeReturn.setDisable(true);
        completeReturn.setOpacity(0);
        cancel.setDisable(false);
        cancel.setOpacity(100);
        returnText.setDisable(true);
        returnText.setOpacity(0);
    }
    @FXML
    private void itemReturn() {
        itemDescription.setDisable(true);
        itemDescription.setOpacity(0);
        itemColor.setDisable(true);
        itemColor.setOpacity(0);
        itemCategory.setDisable(true);
        itemCategory.setOpacity(0);
        itemCategoryLabel.setDisable(true);
        itemCategoryLabel.setOpacity(0);
        itemDate.setDisable(true);
        itemDate.setOpacity(0);
        itemDateLabel.setDisable(true);
        itemDateLabel.setOpacity(0);
        addItem.setDisable(true);
        addItem.setOpacity(0);
        completeReturn.setDisable(false);
        completeReturn.setOpacity(100);
        cancel.setDisable(false);
        cancel.setOpacity(100);
        returnText.setDisable(false);
        returnText.setOpacity(100);
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
        itemDate.setDisable(true);
        itemDate.setOpacity(0);
        itemDateLabel.setDisable(true);
        itemDateLabel.setOpacity(0);
        addItem.setDisable(true);
        addItem.setOpacity(0);
        completeReturn.setDisable(true);
        completeReturn.setOpacity(0);
        cancel.setDisable(true);
        cancel.setOpacity(0);
        returnText.setDisable(true);
        returnText.setOpacity(0);
    }

    @FXML
    private void submitItem(){

    }

}