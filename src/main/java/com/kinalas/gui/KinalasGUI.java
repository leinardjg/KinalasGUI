package com.kinalas.gui;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.kinalas.KinalasView;
import com.kinalas.gui.login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Stage loginStage = new Stage();
        LoginView login = new LoginView();
        Employee employee = login.start(loginStage);

        Stage kinalasStage = new Stage();
        Kinalas kinalas = new Kinalas(employee.getId());
        KinalasView kinalasView = new KinalasView(kinalasStage, kinalas);
        kinalasView.start();

    }

    public static void main(String[] args) {
        launch();
    }
}