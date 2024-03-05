package com.kinalas.gui.kinalas.components.orderModifiersGridPane;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;

public class OrderModifierButton extends ToggleButton {
    public OrderModifierButton(String text) {
        this.setPadding(new Insets(2));
        this.textProperty().set(text);
    }
}
