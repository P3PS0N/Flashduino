package com.p3ps0n.flashduino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    static  public Image getIcon() {
        return new Image("/images/icon.png");
    }

    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")));
        stage.setScene(scene);
        stage.getIcons().add(getIcon());
        stage.setTitle("Flashduino");
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
