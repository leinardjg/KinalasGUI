package com.kinalas.gui;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.login.LoginView;
import com.kinalas.gui.start.KinalasStart;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Stage secondaryStage = new Stage();
        secondaryStage.setResizable(false);

        // if first time, setup new employee
        if (Employee.getAll().size() < 1) {

            FXMLLoader setupFXML = new FXMLLoader(KinalasGUI.class.getResource("setup/kinalas-setup.fxml"));
            secondaryStage.setResizable(false);
            Scene scene = new Scene(setupFXML.load(), 640, 480);
            secondaryStage.setTitle("Kinalas Setup");
            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();

        }

        if (Employee.getAll().size() > 0) {

            // prompt login
            Employee employee = new LoginView().start();

            if (employee == null) return;

            // main menu
            FXMLLoader startFXML = new FXMLLoader(KinalasGUI.class.getResource("start/kinalas-start.fxml"));
            stage.setResizable(false);
            Scene scene = new Scene(startFXML.load(), 640, 480);
            stage.setTitle("Kinalas");
            stage.setScene(scene);
            stage.show();

            // set employee
            ((KinalasStart) startFXML.getController()).setEmployee(employee);

        }

    }

    public static void main(String[] args) {
        launch();
    }
}