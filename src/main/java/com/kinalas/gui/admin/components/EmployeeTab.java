package com.kinalas.gui.admin.components;

import com.kinalas.core.model.employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EmployeeTab extends Tab {

    private final ObservableList<Employee> employees = FXCollections.observableList(Employee.getAll());

    public EmployeeTab() {

        this.setText("Employees");

        TableView<Employee> tableView = new TableView<>();
        VBox.setVgrow(tableView, Priority.ALWAYS);

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

        Label label = new Label("Employees");
        label.setStyle("-fx-font-size: 20");

        Hyperlink link = new Hyperlink("Create new");
        link.setPadding(new Insets(0, 8, 0, 8));

        HBox hBox = new HBox(label, link);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.setPadding(new Insets(16, 4, 16, 4));

        this.setContent(new VBox(hBox, tableView));

    }

}
