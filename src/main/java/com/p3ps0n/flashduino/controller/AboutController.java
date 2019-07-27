package com.p3ps0n.flashduino.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Properties;

public class AboutController {
    @FXML Label labelVersion;
    @FXML Hyperlink developerEmail;

    @FXML
    private void initialize() throws IOException {
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/project.properties"));
        labelVersion.setText("Flashduino " + prop.getProperty("version"));
        developerEmail.setText(prop.getProperty("developer.email"));


    }
}
