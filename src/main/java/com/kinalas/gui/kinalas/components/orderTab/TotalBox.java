package com.kinalas.gui.kinalas.components.orderTab;

import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderModifier.OrderModifier;
import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class TotalBox extends HBox {

    private final Order order;

    public TotalBox(Order order) {
        this.order = order;

        Text label = new Text("Total");
        Text total = new Text(String.format("%.2f", calculateTotal()));
        Region space = new Region();

        HBox.setHgrow(space, Priority.ALWAYS);

        this.setPadding(new Insets(10));
        this.getChildren().addAll(label, space, total);

        for (Item item : order.getItems()) {
            item.getModifiers().addListener((ListChangeListener<OrderModifier>) change -> calculateTotal());
        }

        order.getItems().addListener((ListChangeListener<Item>) change -> {
            while (change.next()) {
                for (Item item : change.getAddedSubList()) {
                    item.getModifiers().addListener((ListChangeListener<OrderModifier>) modifierChange -> total.setText(
                            String.format("%.2f", calculateTotal())
                    ));
                }
                total.setText(String.format("%.2f", calculateTotal()));
            }
        });

    }

    private double calculateTotal() {
        double total = 0d;
        for (Item item : order.getItems()) {
            total += item.getPrice();
            for (OrderModifier modifier : item.getModifiers()) {
                total += modifier.getPrice();
            }
        }
        return total;
    }
}
