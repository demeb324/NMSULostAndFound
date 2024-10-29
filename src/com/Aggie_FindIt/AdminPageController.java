package com.Aggie_FindIt;

import javafx.fxml.FXML;

public class AdminPageController {

    @FXML
    private void logout() {
        System.out.println("Returning to home page from Admin page");
        Aggie_FindIt.loadMainPage();
    }
    
}
