package com.kinalas.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("kinalas-login.fxml"));
        stage.setResizable(false);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("KinalasGUI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}