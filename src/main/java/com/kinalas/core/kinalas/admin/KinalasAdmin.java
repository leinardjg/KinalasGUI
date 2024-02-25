package com.kinalas.core.kinalas.admin;

import com.kinalas.core.model.employee.Employee;

public abstract class KinalasAdmin {

    private final Employee employee;

    public KinalasAdmin(String employeeID) {
        this.employee = Employee.get(employeeID);
        if (employee.getAccessLevel() > 0) {
            System.out.println("No access");
            return;
        }
    }

    public Employee getEmployee() {
        return employee;
    }

}
