package com.kinalas.gui.admin.components;

import com.kinalas.core.model.employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeTab extends Tab {

    private final ObservableList<Employee> employees = FXCollections.observableList(Employee.getAll());

    public EmployeeTab() {

        this.setText("Employees");

        TableView<Employee> tableView = new TableView<>();

        // columns
        TableColumn<Employee, String> employeeIDColumn = new TableColumn<>("ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, Integer> accessLevelColumn = new TableColumn<>("Access Level");
        accessLevelColumn.setCellValueFactory(new PropertyValueFactory<>("accessLevel"));

        tableView.getColumns().add(employeeIDColumn);
        tableView.getColumns().add(firstNameColumn);
        tableView.getColumns().add(lastNameColumn);
        tableView.getColumns().add(accessLevelColumn);

        tableView.setItems(employees);
        tableView.getColumns().forEach(column -> column.setReorderable(false));

        this.setContent(tableView);
    }

}
