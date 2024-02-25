package com.kinalas.core.kinalas.admin.employee;

import com.kinalas.core.kinalas.admin.KinalasAdmin;
import com.kinalas.core.model.employee.Employee;

public class EmployeeManager extends KinalasAdmin {

    public EmployeeManager(String employeeID) {
        super(employeeID);
    }

    public Employee newEmployee(String firstName, String lastName, int accessLevel) {
        Employee employee = new Employee(firstName, lastName, accessLevel);
        if (employee.create()) return employee;
        return null;
    }

}
