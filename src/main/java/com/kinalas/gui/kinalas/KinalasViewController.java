package com.kinalas.gui.kinalas;

import com.kinalas.core.kinalas.Kinalas;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class KinalasViewController {

    private Kinalas kinalas;

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

    protected void setKinalas(Kinalas kinalas) {
        this.kinalas = kinalas;
    }

}