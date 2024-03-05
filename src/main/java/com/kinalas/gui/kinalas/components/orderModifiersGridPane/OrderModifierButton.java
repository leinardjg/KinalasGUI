package com.kinalas.gui.kinalas.components.orderModifiersGridPane;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

public class OrderModifierButton extends StackPane {

    private ToggleButton toggleButton;

    public OrderModifierButton(String text) {
        this.setPadding(new Insets(1));
        this.setMinHeight(32);

        this.toggleButton = new ToggleButton();
        this.toggleButton.setText(text);
        this.toggleButton.setMaxWidth(Double.MAX_VALUE);
        this.toggleButton.setPrefHeight(32);

        this.getChildren().add(this.toggleButton);
    }

    public void setSelected(boolean value) {
        this.toggleButton.setSelected(value);
    }

    public ToggleButton getToggleButton() {
        return this.toggleButton;
    }
}
