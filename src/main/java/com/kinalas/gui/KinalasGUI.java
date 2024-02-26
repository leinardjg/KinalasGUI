package com.kinalas.gui;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.login.Login;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        Login login = new Login();
        Employee employee = login.start(loginStage);

        System.out.println(employee);
    }

    public static void main(String[] args) {
        launch();
    }
}