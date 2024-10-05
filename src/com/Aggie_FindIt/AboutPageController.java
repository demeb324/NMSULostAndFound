package com.Aggie_FindIt;

import javafx.fxml.FXML;

public class AboutPageController {

    public AboutPageController() {
        System.out.println("AboutPageController constructor called");
    }

    @FXML
    public void initialize() {
        System.out.println("AboutPageController initialized");
    }

    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from About page");
        Aggie_FindIt.loadMainPage();
    }
}