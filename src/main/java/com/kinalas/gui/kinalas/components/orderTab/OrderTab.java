package com.kinalas.gui.kinalas.components.orderTab;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
                vBox.getChildren().clear();
                for (Item item : orderItemsChange.getList()) {
                    vBox.getChildren().add(new OrderItem(item));
                }
                if (orderItemsChange.wasRemoved()) {
                    Kinalas.getInstance().getSelectedItems().clear();
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent");

        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);

        VBox box = new VBox(scrollPane, space, new TotalBox(order));

        box.setOnMouseClicked(mouseEvent -> {
            Kinalas.getInstance().getSelectedItems().clear();
            mouseEvent.consume();
        });

        this.setContent(box);
    }

    public Order getOrder() {
        return order;
    }

}
