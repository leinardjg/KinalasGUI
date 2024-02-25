package com.kinalas.gui.login;

import com.kinalas.core.model.employee.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField employeeCodeField;

    @FXML
    private void onLogin() {
        Employee employee = Employee.get(employeeCodeField.getText().strip());
        System.out.println(employee);
    }

}
