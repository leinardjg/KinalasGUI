package com.kinalas.gui;

import com.kinalas.gui.login.Login;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Login login = new Login();
        login.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}