package com.kinalas.gui.start;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.KinalasGUI;
import com.kinalas.gui.kinalas.KinalasViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasStart {

    private Employee employee;

    @FXML private Button adminButton;
    @FXML private Button kinalasButton;

    @FXML private void initialize() {
        adminButton.setOnAction(actionEvent -> {
            try {
                startAdmin();
                ((Stage) adminButton.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        kinalasButton.setOnAction(actionEvent -> {
            try {
                startKinalas();
                ((Stage) kinalasButton.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setEmployee(Employee employee) throws IOException {
        Kinalas.initialize(employee, 0.05d); // todo no hardcode
        if (employee.getAccessLevel() > 0) {
            startKinalas();
            ((Stage) kinalasButton.getScene().getWindow()).close();
        }
    }

    private void startAdmin() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("admin/kinalas-admin.fxml"));

        stage.setResizable(true);
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Kinalas Admin");
        stage.setScene(scene);
        stage.show();
    }

    private void startKinalas() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("kinalas-view.fxml"));

        stage.setResizable(true);
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Kinalas");
        stage.setScene(scene);
        stage.show();
    }

}
