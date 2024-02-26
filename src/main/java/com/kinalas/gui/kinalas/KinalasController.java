package com.kinalas.gui.kinalas;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class KinalasController {

    private int currentIndex = 1;

    @FXML
    private TabPane ordersTabPane;

    @FXML
    private void onStart() {

    }

    @FXML
    private void onAddOrder() {
        ordersTabPane.getTabs().add(new Tab(String.format("Order %s", currentIndex++)));
    }

}