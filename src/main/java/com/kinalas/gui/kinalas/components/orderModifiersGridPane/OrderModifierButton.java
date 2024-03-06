package com.kinalas.gui.kinalas.components.orderModifiersGridPane;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OrderModifierButton extends StackPane {

    private ToggleButton toggleButton;

    public OrderModifierButton(String text) {
        this.setPadding(new Insets(1));

        this.toggleButton = new ToggleButton();
        this.toggleButton.setText(text);
        this.toggleButton.setMaxWidth(Double.MAX_VALUE);
        this.toggleButton.setMaxHeight(Double.MAX_VALUE);

        this.getChildren().add(this.toggleButton);
    }

    public void setSelected(boolean value) {
        this.toggleButton.setSelected(value);
    }

    public ToggleButton getToggleButton() {
        return this.toggleButton;
    }
}
