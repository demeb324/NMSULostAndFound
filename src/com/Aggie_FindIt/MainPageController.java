package com.Aggie_FindIt;

import javafx.fxml.FXML;
import static com.Aggie_FindIt.sql_link.deleteOldItems;

public class MainPageController {

    @FXML
    private void openLoginPage() {
        System.out.println("openLoginPage method called");
        Aggie_FindIt.loadPage("Login.fxml");
        sql_link.deleteOldItems();
    }

    @FXML
    private void openSignUpPage() {
        System.out.println("openSignUpPage method called");
        Aggie_FindIt.loadPage("Signup.fxml");
    }

    @FXML
    private void openAboutPage() {
        System.out.println("openAboutPage method called");
        Aggie_FindIt.loadPage("About.fxml");
    }

    @FXML
    private void openContactPage() {
        System.out.println("openContactPage method called");
        Aggie_FindIt.loadPage("ContactUs.fxml");
    }

    @FXML
    private void openFAQPage() {
        System.out.println("openFAQPage method called");
        Aggie_FindIt.loadPage("FAQ.fxml");
    }
}