package com.Aggie_FindIt;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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


//javac --module-path C:/Users/deyru/javafx-sdk-23/lib --add-modules=javafx.controls,javafx.fxml com/Aggie_FindIt/*.java

//this seems to work for me to run the program from the terminal, lmk if it works:


//javac --module-path C:/Users/deyru/javafx-sdk-23/lib --add-modules javafx.controls,javafx.graphics,javafx.media,javafx.fxml -cp lib/mongodb-driver-sync-5.2.0.jar:lib/mongodb-driver-core-5.2.0.jar:lib/bson-5.2.0.jar:lib/bson-record-codec-5.2.0.jar src/com/Aggie_FindIt/*.java


//cd src


//java --module-path <sdkpath>/lib --add-modules javafx.base,javafx.graphics,javafx.controls,javafx.fxml -cp .:<absolute_paths to bson-5.2.0.jar, bson-record-codec-5.2.0.jar, mongodb-driver-core-5.2.0.jar, mongodb-driver-sync-5.2.0.jar()seperated by ":"> com.Aggie_FindIt.Aggie_FindIt


//javac --module-path C:/Users/deyru/javafx-sdk-23/lib --add-modules javafx.controls,javafx.graphics,javafx.media,javafx.fxml  -cp .;lib/mongodb-driver-sync-5.2.0.jar;lib/mongodb-driver-core-5.2.0.jar;lib/bson-5.2.0.jar;lib/bson-record-codec-5.2.0.jar src/com/Aggie_FindIt/*.java
//javac --module-path C:/Users/deyru/javafx-sdk-23/lib --add-modules javafx.controls,javafx.graphics,javafx.media,javafx.fxml  -cp .;lib/mongodb-driver-sync-5.2.0.jar;lib/mongodb-driver-core-5.2.0.jar;lib/bson-5.2.0.jar;lib/bson-record-codec-5.2.0.jar C:/Users/deyru/javafx-sdk-23/src/com/Aggie_FindIt/*.java