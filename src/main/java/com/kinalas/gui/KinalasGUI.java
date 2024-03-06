package com.kinalas.gui;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.kinalas.KinalasView;
import com.kinalas.gui.login.LoginController;
import com.kinalas.gui.login.LoginView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {

    private final double taxMultiplier = 0.05d;

    @Override
    public void start(Stage stage) throws IOException {

        if (Employee.getAll().size() < 1) {
            Stage initializeStage = new Stage();
            FXMLLoader setupFXML = new FXMLLoader(KinalasGUI.class.getResource("kinalas-setup.fxml"));
            initializeStage.setResizable(false);
            Scene scene = new Scene(setupFXML.load(), 640, 480);
            initializeStage.setTitle("Kinalas Setup");
            initializeStage.setScene(scene);
            initializeStage.showAndWait();
        }

        FXMLLoader startFXML = new FXMLLoader(KinalasGUI.class.getResource("start/kinalas-start.fxml"));
        stage.setResizable(false);
        Scene scene = new Scene(startFXML.load(), 640, 480);
        stage.setTitle("Kinalas");
        stage.setScene(scene);
        stage.show();

//        Stage loginStage = new Stage();
//        LoginView login = new LoginView();
//        Employee employee = login.start(loginStage);
//
//        Stage kinalasStage = new Stage();
//        Kinalas.initialize(employee, taxMultiplier);
//        KinalasView kinalasView = new KinalasView(kinalasStage, Kinalas.getInstance());
//        kinalasView.start();

    }

    public static void main(String[] args) {
        launch();
    }
}