package com.kinalas.core.model.employee;

import com.kinalas.core.model.BaseModel;

import java.util.ArrayList;
import java.util.Random;

public class Employee extends BaseModel {

    private String firstName;
    private String lastName;
    private int accessLevel;

    protected Employee(String id, String firstName, String lastName, int accessLevel) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessLevel = accessLevel;
    }

    public Employee(String firstName, String lastName, int accessLevel) {
        this(String.format("%06d", new Random().nextInt(999999)), firstName, lastName, accessLevel);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    // region controller

    public boolean create() {
        return EmployeeController.create(this) > 0;
    }

    public boolean update() {
        return EmployeeController.update(this) > 0;
    }

    public boolean delete() {
        return EmployeeController.delete(this) > 0;
    }

    // region statics

    public static Employee get(String id) {
        return EmployeeController.get(id);
    }

    public static ArrayList<Employee> getAll() {
        return EmployeeController.getAll();
    }

    // endregion

    // endregion


    @Override
    public String toString() {
        return "(" + this.id + ", " + this.accessLevel + ") " + this.firstName + " " + this.lastName;
    }
}
