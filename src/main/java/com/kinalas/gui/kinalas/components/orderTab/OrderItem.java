package com.kinalas.gui.kinalas.components.orderTab;

import com.kinalas.core.model.orderable.item.Item;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class OrderItem extends HBox {

    private final Item item;

    public OrderItem(Item item) {
        this.item = item;

        this.setPadding(new Insets(5));

        Text itemName = new Text(item.getName());
        Text itemPrice = new Text(String.format("%.2f", item.getPrice()));

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        this.getChildren().addAll(itemName, space, itemPrice);
    }

    public Item getItem() {
        return item;
    }
}
