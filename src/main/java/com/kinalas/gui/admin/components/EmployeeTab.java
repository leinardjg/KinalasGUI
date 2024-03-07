package com.kinalas.gui.admin.components;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.gui.KinalasGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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

        // link on click
        link.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("admin/create/employee/create-employee.fxml"));
            stage.setResizable(false);
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Create new employee");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.showAndWait();

            employees.clear();
            employees.addAll(Employee.getAll());

        });

    }

}
