package com.kinalas.gui.kinalas.components.orderTab;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import java.util.List;

public class OrderTab extends Tab {

    private final Order order;

    public OrderTab(Order order) {
        this.order = order;
        this.setText("Order " + order.getOrderNumber());
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 5, 10, 5));

        // items
        order.getItems().addListener((ListChangeListener<Item>) orderItemsChange -> {
            while (orderItemsChange.next()) {
                if (orderItemsChange.wasAdded()) {
                    List<? extends Item> addedItems = orderItemsChange.getAddedSubList();
                    for (Item item : addedItems) {
                        vBox.getChildren().add(new OrderItem(item));
                    }
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        this.setContent(scrollPane);
    }

    public Order getOrder() {
        return order;
    }

}
