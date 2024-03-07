package com.kinalas.gui.admin.create.employee;

import com.kinalas.core.model.employee.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateEmployee {

    @FXML
    private TextField firstNameField, lastNameField;
    @FXML private ComboBox<Integer> accessLevelBox;
    @FXML private Button submitButton, cancelButton;

    @FXML
    private void initialize() {
        accessLevelBox.getItems().addAll(0, 1, 2);
        cancelButton.setOnAction(actionEvent -> {
            ((Stage) cancelButton.getScene().getWindow()).close();
        });
        submitButton.setOnAction(actionEvent -> {
            if (firstNameField.getText().length() < 1) {
                new Alert(Alert.AlertType.ERROR, "First Name cannot be empty").showAndWait();
                return;
            }
            if (lastNameField.getText().length() < 1) {
                new Alert(Alert.AlertType.ERROR, "Last Name cannot be empty").showAndWait();
                return;
            }
            if (accessLevelBox.getSelectionModel().getSelectedItem() == null) {
                new Alert(Alert.AlertType.ERROR, "Please set access level").showAndWait();
                return;
            }

            Employee employee = new Employee(firstNameField.getText().strip(), lastNameField.getText().strip(), accessLevelBox.getSelectionModel().getSelectedItem());
            if (employee.create()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee successfully created");
                alert.getDialogPane().setHeaderText("Employee created");
                alert.showAndWait();
                ((Stage) submitButton.getScene().getWindow()).close();
            }
        });
    }

}
