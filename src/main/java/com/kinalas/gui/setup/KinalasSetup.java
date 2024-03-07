package com.kinalas.gui.setup;

import com.kinalas.core.model.employee.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KinalasSetup {

    @FXML private TextField firstNameField, lastNameField;
    @FXML private Button submitButton;

    @FXML
    private void initialize() {
        submitButton.setOnAction(actionEvent -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            if (firstName.length() < 1) {
                new Alert(Alert.AlertType.ERROR, "First name cannot be empty.").showAndWait();
                return;
            }

            if (lastName.length() < 1) {
                new Alert(Alert.AlertType.ERROR, "Last name cannot be empty.").showAndWait();
                return;
            }

            Employee employee = new Employee(firstName, lastName, 0);

            if (employee.create()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format("Employee %s %s %s created.", firstName, lastName, employee.getId()));
                alert.getDialogPane().setHeaderText("Employee created");
                alert.showAndWait();
                ((Stage) submitButton.getScene().getWindow()).close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong.").showAndWait();
            }

        });
    }

}
