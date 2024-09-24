package com.Aggie_FindIt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Aggie_FindIt extends Application {
    @Override
    public void start(Stage stage) {

        Label header1 = new Label("Welcome to Aggie Find-it!" + "\n");
        header1.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label header2 = new Label("Your One-Stop Hub for Lost & Found Made Simple!");
        header2.setStyle("-fx-font-size: 20px;");

        VBox vbox = new VBox(10, header1, header2);
        Scene scene = new Scene(vbox, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Aggie Find-it");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
// gitbash commands:
// Compile: javac --module-path ~/documents/java/jfx/javafx-sdk-23/lib --add-modules javafx.controls,javafx.fxml -d out src/com/Aggie_FindIt/Aggie_FindIt.java
// Run: java --module-path ~/Documents/java/jfx/javafx-sdk-23/lib --add-modules javafx.controls,javafx.fxml -cp out com.Aggie_FindIt.Aggie_FindIt
// Note: Make Ant for ease
