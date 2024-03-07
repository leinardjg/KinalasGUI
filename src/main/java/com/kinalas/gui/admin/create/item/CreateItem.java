package com.kinalas.gui.admin.create.item;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.core.model.orderable.item.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateItem {

    @FXML private TextField nameField, descriptionField, priceField, typeField;
    @FXML private Button submitButton, cancelButton;

    @FXML
    private void initialize() {
        cancelButton.setOnAction(actionEvent -> {
            ((Stage) cancelButton.getScene().getWindow()).close();
        });
        submitButton.setOnAction(actionEvent -> {
            if (nameField.getText().length() < 1) {
                new Alert(Alert.AlertType.ERROR, "Name cannot be empty").showAndWait();
                return;
            }
            if (priceField.getText().length() < 1) {
                new Alert(Alert.AlertType.ERROR, "Price cannot be empty").showAndWait();
                return;
            }
            if (typeField.getText().length() < 1) {
                new Alert(Alert.AlertType.ERROR, "Type cannot be empty").showAndWait();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR, "Invalid price.").showAndWait();
                return;
            }

            Item item = new Item(
                    nameField.getText(),
                    descriptionField.getText(),
                    price,
                    typeField.getText()
            );


            if (item.create()) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item successfully created.").showAndWait();
                ((Stage) submitButton.getScene().getWindow()).close();
            }
        });
    }

}
