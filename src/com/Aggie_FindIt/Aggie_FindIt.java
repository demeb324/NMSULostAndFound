package com.Aggie_FindIt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.io.IOException;

public class Aggie_FindIt extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        loadMainPage();
        primaryStage.show();
    }

    public static void loadMainPage() {
        loadPage("Main.fxml");
    }

    public static void loadPage(String fxmlFile) {
        System.out.println("Attempting to load: " + fxmlFile);
        try {
            URL resource = Aggie_FindIt.class.getResource(fxmlFile);
            if (resource == null) {
                System.err.println("FXML file not found: " + fxmlFile);
                return;
            }
            System.out.println("Resource found: " + resource);
            
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            System.out.println("FXML loaded successfully");
            
            Scene scene = primaryStage.getScene();
            if (scene == null) {
                scene = new Scene(root, 450, 650);
                primaryStage.setScene(scene);
                System.out.println("New scene created and set");
            } else {
                scene.setRoot(root);
                System.out.println("Existing scene updated with new root");
            }
            
            // Force a layout pass
            primaryStage.sizeToScene();
            
            System.out.println("Page load complete");
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



//commands to run the program; change the path to the javafx-sdk-23 folder on your computer properly
//need to write a makefile later on

//javac --module-path C:/Users/deyru/javafx-sdk-23/lib --add-modules=javafx.controls,javafx.fxml com/Aggie_FindIt/*.java
//java --module-path C:/Users/deyru/javafx-sdk-23/lib --add-modules=javafx.base,javafx.graphics,javafx.controls,javafx.fxml -cp . com.Aggie_FindIt.Aggie_FindIt