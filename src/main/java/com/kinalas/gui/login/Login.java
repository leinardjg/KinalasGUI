package com.kinalas.gui.login;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.KinalasGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    public Employee start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("kinalas-login.fxml"));
        stage.setResizable(false);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Kinalas Login");
        stage.setScene(scene);
        stage.showAndWait();
        return ((LoginController) fxmlLoader.getController()).getEmployee();
    }

}