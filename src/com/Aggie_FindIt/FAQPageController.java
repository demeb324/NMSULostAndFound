package com.Aggie_FindIt;

import javafx.fxml.FXML;

public class FAQPageController {

    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from FAQ page");
        Aggie_FindIt.loadMainPage();
    }
}