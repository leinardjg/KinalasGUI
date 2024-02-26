package com.kinalas.gui.kinalas;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.orderable.item.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;

public class KinalasViewController {

    private Kinalas kinalas;
    private int currentIndex = 1;

    @FXML private TabPane itemsTabPane;
    @FXML private TabPane ordersTabPane;
    @FXML private Label employeeInfoLabel;
    @FXML private Label timeLabel;

    @FXML
    private void onStart() {

    }

    @FXML
    private void onAddOrder() {
        ordersTabPane.getTabs().add(new Tab(String.format("Order %s", currentIndex++)));
    }

    protected void initialize(Kinalas kinalas) {

        this.kinalas = kinalas;
        this.employeeInfoLabel.setText(kinalas.getEmployee().getFirstName() + " " + kinalas.getEmployee().getLastName() + " " + kinalas.getEmployee().getId());
        this.timeLabel.setText("Live time WIP"); // todo

        itemsTabPane.getTabs().clear();
        ArrayList<String> itemTypes = Item.getTypes();

        for (String itemType : itemTypes) {
            itemsTabPane.getTabs().add(new Tab(itemType + "s"));
        }

    }

}