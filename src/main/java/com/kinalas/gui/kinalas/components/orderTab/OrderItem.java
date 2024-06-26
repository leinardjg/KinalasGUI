package com.kinalas.gui.kinalas.components.orderTab;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.orderModifier.OrderModifier;
import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OrderItem extends VBox {

    private final String highlightedStyle = "-fx-background-color: #00CC9937; -fx-background-radius: 8px";

    private final Item item;

    public OrderItem(Item item) {

        this.item = item;

        this.setPadding(new Insets(5));

        Text itemName = new Text(item.getName());
        Text itemPrice = new Text(String.format("%.2f", item.getPrice()));

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        this.getChildren().add(new HBox(itemName, space, itemPrice));

        VBox vBox = new VBox();

        Kinalas.getInstance().getSelectedItems().addListener((ListChangeListener<Item>) change -> {
            if (change.getList().contains(item)) {
                highlight();
            } else {
                unHighlight();
            }
        });

        item.getModifiers().addListener((ListChangeListener<OrderModifier>) change -> {
            vBox.getChildren().clear();
            for (OrderModifier modifier : item.getModifiers()) {
                Text modifierName = new Text(modifier.getName() + " " + modifier.getTarget().getName());
                Text modifierPrice = new Text(String.format("%.2f", modifier.getPrice()));

                if (modifier.getPrice() == 0.00) {
                    modifierPrice.setText("");
                }

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                HBox hBox = new HBox(modifierName, spacer, modifierPrice);
                hBox.setPadding(new Insets(5, 0, 0, 10));

                vBox.getChildren().add(hBox);
            }
        });

        for (OrderModifier modifier : item.getModifiers()) {
            Text modifierName = new Text(modifier.getName() + " " + modifier.getTarget().getName());
            Text modifierPrice = new Text(String.format("%.2f", modifier.getPrice()));

            if (modifier.getPrice() == 0.00) {
                modifierPrice.setText("");
            }

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            HBox hBox = new HBox(modifierName, spacer, modifierPrice);
            hBox.setPadding(new Insets(5, 0, 0, 10));

            vBox.getChildren().add(hBox);
        }

        this.getChildren().add(vBox);

        this.setOnMouseClicked(mouseEvent -> {
            if (!Kinalas.getInstance().getSelectedItems().contains(item)) {
                Kinalas.getInstance().getSelectedItems().add(this.item);
            } else {
                Kinalas.getInstance().getSelectedItems().remove(this.item);
            }
            mouseEvent.consume();
        });

        Kinalas.getInstance().getSelectedItems().clear();
        Kinalas.getInstance().getSelectedItems().add(item);
    }

    public void highlight() {
        this.setStyle(highlightedStyle);
    }

    public void unHighlight() {
        this.setStyle("");
    }

    public Item getItem() {
        return item;
    }
}
