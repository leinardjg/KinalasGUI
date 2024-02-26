package com.kinalas.gui.login;

import com.kinalas.core.model.employee.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private Employee employee;

    @FXML
    private TextField employeeCodeField;
    @FXML
    private Label statusText;

    @FXML
    private void onEmployeeCodeFieldChange() {
        statusText.setVisible(false);
    }

    @FXML
    private void onLogin() {

        String code = employeeCodeField.getText().strip();

        if (code.strip().equals("")) {
            statusText.setVisible(true);
            statusText.setText("Please enter employee code");
            return;
        }

        this.employee = Employee.get(code);

        if (this.employee != null) {
            Stage stage = (Stage) employeeCodeField.getScene().getWindow();
            stage.close();
        } else {
            statusText.setVisible(true);
            statusText.setText("Employee " + code + " not found.");
        }
    }

    public Employee getEmployee() {
        return this.employee;
    }

}
