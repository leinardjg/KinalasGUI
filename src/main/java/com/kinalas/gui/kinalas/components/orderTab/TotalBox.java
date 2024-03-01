package com.kinalas.gui.kinalas.components.orderTab;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderModifier.OrderModifier;
import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TotalBox extends VBox {

    private final Order order;

    public TotalBox(Order order) {
        this.order = order;

        double subTotalAmount = calculateSubTotal();

        Text subtotalLabel = new Text("Subtotal");
        Text subTotal = new Text(String.format("%.2f", subTotalAmount));
        Region subtotalSpace = new Region();

        Text taxLabel = new Text("Tax");
        Text tax = new Text(String.format("%.2f", calculateTax(subTotalAmount)));
        Region taxSpace = new Region();

        Text totalLabel = new Text("Total");
        Text total = new Text(String.format("%.2f", subTotalAmount + calculateTax(subTotalAmount)));
        Region totalSpace = new Region();

        HBox.setHgrow(subtotalSpace, Priority.ALWAYS);
        HBox.setHgrow(taxSpace, Priority.ALWAYS);
        HBox.setHgrow(totalSpace, Priority.ALWAYS);

        this.setPadding(new Insets(10));
        this.getChildren().addAll(
                new HBox(subtotalLabel, subtotalSpace, subTotal),
                new HBox(taxLabel, taxSpace, tax),
                new HBox(totalLabel, totalSpace, total)
        );

        for (Item item : order.getItems()) {
            item.getModifiers().addListener((ListChangeListener<OrderModifier>) change -> calculateSubTotal());
        }

        order.getItems().addListener((ListChangeListener<Item>) change -> {
            while (change.next()) {
                for (Item item : change.getAddedSubList()) {
                    item.getModifiers().addListener((ListChangeListener<OrderModifier>) modifierChange -> {
                        double newSubTotal = calculateSubTotal();
                        double newTax = calculateTax(newSubTotal);
                        subTotal.setText(String.format("%.2f", newSubTotal));
                        tax.setText(String.format("%.2f", newTax));
                        total.setText(String.format("%.2f", newSubTotal + newTax));
                    });
                }
                double newSubTotal = calculateSubTotal();
                double newTax = calculateTax(newSubTotal);
                subTotal.setText(String.format("%.2f", newSubTotal));
                tax.setText(String.format("%.2f", newTax));
                total.setText(String.format("%.2f", newSubTotal + newTax));
            }
        });

    }

    private double calculateSubTotal() {
        double total = 0d;
        for (Item item : order.getItems()) {
            total += item.getPrice();
            for (OrderModifier modifier : item.getModifiers()) {
                total += modifier.getPrice();
            }
        }
        return total;
    }

    private double calculateTax(double subTotal) {
        return subTotal * Kinalas.getInstance().getTaxMultiplier();
    }
}
