package com.Aggie_FindIt;

import javafx.fxml.FXML;

public class AdminPageController {

    @FXML
    private void backToHome() {
        System.out.println("Returning to home page from About page");
        Aggie_FindIt.loadMainPage();
    }
    
}
