package com.Aggie_FindIt;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WhereToGoPageController {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ImageView imageView;

    public void initialize() {
        // Set a placeholder image
        Image placeholderImage = new Image("file:../resources/images/aflogo.png");
        imageView.setImage(placeholderImage);

        // Populate the ComboBox with locations
        comboBox.getItems().addAll(
            "Branson", "Computer Center", "Corbett Center Student Union",
            "Hardman and Jacobs Undergraduate Learning Center", "Campus Police",
            "Science Hall", "Zuhl Library"
        );

        // Add a listener to update the ImageView when the selection changes
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Map the selected item to an image path
                String imagePath = switch (newValue) {
                    case "Branson" -> "file:../resources/images/branson.png";
                    case "Computer Center" -> "file:../resources/images/cc.png";
                    case "Corbett Center Student Union" -> "file:../resources/images/corbett.png";
                    case "Hardman and Jacobs Undergraduate Learning Center" -> "file:../resources/images/hjuc.png";
                    case "Campus Police" -> "file:../resources/images/police.png";
                    case "Science Hall" -> "file:../resources/images/sh.png";
                    case "Zuhl Library" -> "file:../resources/images/zhul.png";
                    default -> null;
                };

                if (imagePath != null) {
                    // Load and display the selected image
                    Image image = new Image(imagePath);
                    imageView.setImage(image);
                }
            }
        });

        // Add zoom functionality to the ImageView
        imageView.setOnScroll(event -> {
            double zoomFactor = 1.1; // Zoom increment
            double minScale = 0.5; // Minimum zoom level
            double maxScale = 3.0; // Maximum zoom level

            if (event.getDeltaY() > 0 && imageView.getScaleX() < maxScale) {
                // Zoom in
                imageView.setScaleX(imageView.getScaleX() * zoomFactor);
                imageView.setScaleY(imageView.getScaleY() * zoomFactor);
            } else if (event.getDeltaY() < 0 && imageView.getScaleX() > minScale) {
                // Zoom out
                imageView.setScaleX(imageView.getScaleX() / zoomFactor);
                imageView.setScaleY(imageView.getScaleY() / zoomFactor);
            }
        });
    }

    @FXML
    private void goBack() {
        System.out.println("goBack method called");
        Aggie_FindIt.loadPage("StudentPage.fxml");
    }
}
