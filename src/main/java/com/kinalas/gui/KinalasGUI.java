package com.kinalas.gui;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.kinalas.KinalasView;
import com.kinalas.gui.login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {

    private final double taxMultiplier = 0.05d;

    @Override
    public void start(Stage stage) throws IOException {

        Stage loginStage = new Stage();
        LoginView login = new LoginView();
        Employee employee = login.start(loginStage);

        Stage kinalasStage = new Stage();
        Kinalas.initialize(employee, taxMultiplier);
        KinalasView kinalasView = new KinalasView(kinalasStage, Kinalas.getInstance());
        kinalasView.start();

    }

    public static void main(String[] args) {
        launch();
    }
}